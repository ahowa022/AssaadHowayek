LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY TopLevelEntity IS
	PORT(
        i_clock, i_mem_clock, i_asynch_reset : IN std_logic;
        i_valueSelect : IN std_logic_vector(2 downto 0);
		o_labMux: OUT std_logic_vector(7 downto 0);
        o_instruction : OUT std_logic_vector(31 downto 0);
        o_branch, o_zero, o_memWrite, o_regWrite : OUT std_logic
	);
END TopLevelEntity; 

ARCHITECTURE rtl of TopLevelEntity IS
    component registered_rom IS
        PORT
        (
            address		: IN STD_LOGIC_VECTOR (7 DOWNTO 0);
            clock		: IN STD_LOGIC  := '1';
            q		: OUT STD_LOGIC_VECTOR (31 DOWNTO 0)
        );
    END component;

    component registered_ram IS
        PORT
        (
            address		: IN STD_LOGIC_VECTOR (7 DOWNTO 0);
            clock		: IN STD_LOGIC  := '1';
            data		: IN STD_LOGIC_VECTOR (7 DOWNTO 0);
            wren		: IN STD_LOGIC ;
            q		: OUT STD_LOGIC_VECTOR (7 DOWNTO 0)
        );
    END component;

    component ALU IS
        generic (n : positive := 8);
        PORT(
            i_ALU_OP : in std_logic_vector(2 downto 0);
            i_a, i_b : in std_logic_vector(n-1 downto 0);
            o_ALU : out std_logic_vector(n-1 downto 0);
            o_overflow, o_zero : out std_logic
        );
    END component; 

    component registres IS
        generic (size : positive := 8);
        PORT(
            i_clock, i_asynch_reset : IN std_logic;
            i_regWrite : IN std_logic;
            i_readReg1, i_readReg2, i_writeReg: IN std_logic_vector(2 downto 0);
            i_writeData : IN std_logic_vector(size-1 downto 0);
            o_read_data1, o_read_data2 : OUT std_logic_vector(size-1 downto 0)
        );
    END component; 

    component nbit_mux2to1 IS
        generic (n : positive := 8);
        PORT(
            a, b: IN STD_LOGIC_VECTOR(n-1 downto 0); -- inputs
            sel: IN STD_LOGIC; --bits de selection
            z: OUT STD_LOGIC_VECTOR(n-1 downto 0)
        ); --output
    END component;

    component nbit_mux4to1 IS
        generic (n : positive := 8);
        PORT(
            a, b, c, d: IN STD_LOGIC_VECTOR(n-1 downto 0); -- inputs
            sel: IN STD_LOGIC_Vector(1 downto 0); --bits de selection
            o_mux: OUT STD_LOGIC_VECTOR(n-1 downto 0)
        );
    end component; 

    component nbit_mux8to1 is
        generic (n : positive := 8);
        port(
            reg0, reg1, reg2, reg3, reg4, reg5, reg6, reg7: in std_logic_vector(n-1 downto 0);
            i_sel: in std_logic_vector(2 downto 0);
            o_mux: out std_logic_vector(n-1 downto 0)
        );
    end component;

    component ControlUnit IS
        PORT(
            i_op_code : IN std_logic_vector(5 downto 0);
            o_RegDst, o_ALU_SRC, o_MemToReg, o_RegWrite, o_MemRead, o_MemWrite, 
                o_Branch, o_BNE, o_jump, o_ALU_OP1, o_ALU_OP0 : OUT std_logic
        );
    END component; 

    component ALU_Control IS
        PORT(
            i_ALU_Op0, i_ALU_Op1 : IN std_logic;
            i_Func_Field : IN std_logic_vector(5 downto 0);
            o_operation : OUT std_logic_vector(2 downto 0)
        );
    END component;

    component Additionneur_NBits IS
        generic (n : positive := 3);
        port (
            a, b: IN STD_LOGIC_VECTOR(n DOWNTO 0);
            cin: IN STD_LOGIC; -- cin set to 0 when sum and set to 1 when substraction
            cout, zout, sign_msb, overflow: OUT STD_LOGIC;
            sum: OUT STD_LOGIC_VECTOR(n DOWNTO 0)
        );
    END component;

    component nbit_reg IS
        generic (n : positive := 7);
        PORT (
            i_clock, i_load, asynch_reset: IN STD_LOGIC;
            i_value: IN STD_LOGIC_VECTOR(n-1 downto 0);
            o_value: OUT STD_LOGIC_VECTOR(n-1 downto 0)
        );
    END component;

    component HazardDetectionUnit IS
        PORT(
            ID_EX_RegRt, IF_ID_RegRs, IF_ID_RegRt : IN std_logic_vector(2 downto 0); -- registre pour hasard decrochage donnee
            ID_EX_MemRead : IN std_logic; -- signal de control pour hasard decrochage donnee
            i_jmp, i_branch, i_readData1equalsReadData2 : IN std_logic; -- signal de control pour flush (decrochage controle)
            o_IF_IDWrite : out std_logic; -- output pour decrochage donnee
            o_mux_control : out std_logic; -- output pour decrochage donnee
            o_PCWrite : out std_logic; -- output pour decrochage donnee
            o_IF_ID_Flush : out std_logic -- output pour decrochage controle
        );
    end component; 

    component ForwardingUnit IS
        PORT(
            i_EX_MEM_RegRd, i_MEM_WB_RegRd, i_ID_EX_RegRs, i_ID_EX_RegRt : IN std_logic_vector(2 downto 0); -- registre necessaire for forward unit
            i_EX_MEM_RegWrite, i_MEM_WB_RegWrite : IN std_logic; -- signaux necessaires for forwarding unit
            o_forwardA, o_forwardB : OUT std_logic_vector(1 downto 0) -- entre mux controle pour forwarding unit
        );
    END component; 
    
    -- signal de huits bits
    signal addressPC, readData1, readData2, ALU_Out, int_adder1, int_adder2, ramOut : std_logic_vector(7 downto 0);

    -- signal rom output
    signal currentInstruction : std_logic_vector(31 downto 0);

    -- signal mux 
    signal write_reg_out : std_logic_vector(2 downto 0);
    signal aluSrc_out, writeData_out, muxAForUnit,  muxBForUnit, next_address1_out, next_address2_out: std_logic_vector(7 downto 0);

    -- signal du control unit 
    signal RegDst, ALU_SRC, MemToReg, RegWrite, MemRead, MemWrite, Branch, BNE, jump, ALU_OP1, ALU_OP0 : std_logic;
    signal executeBranch, branchOrJump : std_logic;

    -- signal statut fichier de registre
    signal int_ReadData1_Equals_ReadData2 : std_logic;

    -- signal statut output alu
    signal int_overflow, int_zero : std_logic;
    -- signal control du alu
    signal alu_Control_Vector : std_logic_vector(2 downto 0);

    -- signal output mux finale
    signal signalOut : std_logic_vector(7 downto 0);
    
    --Signaux du hazard detection unit--
	signal int_IfIdWrite, int_PCWrite, int_mux, int_IfIdFlush: std_logic; 
	
	--signaux du forwarding unit--
	signal int_forwardA, int_forwardB: std_logic_vector(1 downto 0); 
	
	--signaux pour les mux4to1--
	signal int_ALUMux1, int_ALUMux2: std_logic_vector(7 downto 0); 

	--signal pour le mux du control path
	signal signal_muxControl, final_muxControl: std_logic_vector(9 downto 0);
	
	--Signaux buffer--
	signal next_if_id, i_if_id, o_if_id: std_logic_vector(39 downto 0);
	-- [0 - 31] instruction (rs <= instruction(23 downto 21), rt <= instruction(18 downto 16))
	-- [32 - 39] pc + 1

	signal i_id_ex, o_id_ex: std_logic_vector(42 downto 0);
	-- [7 - 0] read_data_1
	-- [15 - 8] read_data_2
	-- [23 - 16] instruction[7 - 0]
	-- [26 - 24] rs
	-- [29 - 27] rt
	-- [31 - 30] ALU_OP
	-- [32] ALU source
	-- [33] MEM read
	-- [34] MEM write
	-- [35] Reg write
	-- [36] MEM to Reg
	-- [37] beq
	-- [38] reg Dst
	-- [39] jump
	-- [42 - 40] rd

	signal i_ex_mem, o_ex_mem: std_logic_vector(22 downto 0);
	-- [7 - 0] ALU out
	-- [10 - 8] Reg write Dst
	-- [11] MEM read
	-- [12] MEM write
	-- [13] Reg write
	-- [14] MEM to Reg
	-- [22 - 15] read_data_2

	signal i_mem_wb, o_mem_wb: std_logic_vector(20 downto 0);
	-- [7 - 0] ALU out
	-- [15 - 8] MEM out
	-- [18 - 16] Reg write Dst
	-- [19] Reg write
	-- [20] MEM to Reg
begin
    -- IF SECTION -- 

    -- PC inclue addresse de l'instruction
    PC : nbit_reg
    generic MAP(n =>8)
        PORT MAP(
            i_clock => i_clock, i_load => int_PCWrite, asynch_reset => i_asynch_reset,
            i_value => next_address2_out,
            o_value => addressPC
        );

    -- prend rom input et sort l'instruction a executer
    ROM : registered_rom
        PORT MAP
        (
            address	=> addressPC,
            clock => i_mem_clock, 
            q => currentInstruction
        );
    
    -- additionneur qui accomplit PC+1
    PcPlusOne : Additionneur_NBits
        generic map (n => 7)
        port map (
            a => addressPC, b => "00000001", 
            cin => '0',
            sum => int_adder1
        );

    -- END SECTION IF -- 

    -- [31 - 0] instruction (rs <= instruction(23 downto 21), rt <= instruction(18 downto 16))
	-- [39 - 32] pc + 1
    next_if_id(31 downto 0) <= currentInstruction;
    next_if_id(39 downto 32) <= int_adder1;

    if_id_input : nbit_mux2to1
        generic map(n => 40)
        PORT map(
            a => next_if_id, b => "0000000000000000000000000000000000000000",
            sel => int_IfIdFlush,
            z => i_if_id
        );

    IF_IDReg : nbit_reg
        generic MAP(n =>40)
        PORT MAP(
            i_clock => i_clock, i_load => int_IfIdWrite, asynch_reset => i_asynch_reset,
            i_value => i_if_id,
            o_value => o_if_id
        );

    -- ID SECTION -- 
    
    -- fichiers de registres du labo
    fichier_registre : registres
        generic map (size => 8)
        PORT map (
            i_clock => i_clock, i_asynch_reset => i_asynch_reset, 
            i_regWrite => o_mem_wb(19), 
            i_readReg1 => o_if_id(23 downto 21), i_readReg2 => o_if_id(18 downto 16), i_writeReg => o_mem_wb(18 downto 16),
            i_writeData => writeData_out, 
            o_read_data1 => readData1, o_read_data2 => readData2
        );

    -- unite de controle du laboratoire
    controlCPU : ControlUnit
        PORT MAP(
            i_op_code => o_if_id(31 downto 26),
            o_RegDst => RegDst, o_ALU_SRC => ALU_SRC, o_MemToReg => MemToReg, o_RegWrite => RegWrite, o_MemRead => MemRead, o_MemWrite => MemWrite, 
                o_Branch => Branch, o_BNE => BNE, o_jump => jump, o_ALU_OP1 => ALU_OP1, o_ALU_OP0 => ALU_OP0
        );

    -- additionneur qui calculs address de branchement
    BranchAddress : Additionneur_NBits
        generic map (n => 7)
        port map (
            a => o_if_id(39 downto 32), b => o_if_id(7 downto 0), 
            cin => '0',
            sum => int_adder2
        );

    -- signal qui compare read data 1 et read data 2 et determine sils sont egaux
    int_zero <= (readData1(0) xnor readData2(0)) and (readData1(1) xnor readData2(1)) and (readData1(2) xnor readData2(2)) and (readData1(3) xnor readData2(3)) and 
        (readData1(4) xnor readData2(4)) and (readData1(5) xnor readData2(5)) and (readData1(6) xnor readData2(6)) and (readData1(7) xnor readData2(7));

    executeBranch <= (Branch and int_zero);
    branchOrJump <= executeBranch or jump;
    -- mux sortie si on veut branch ou jump
    branch_Or_Jump : nbit_mux2to1
        generic map(n => 8)
        PORT map(
            a => o_if_id(7 downto 0), b => int_adder2,
            sel => executeBranch,
            z => next_address1_out
        );
    
    -- mux qui determine input qui va dans PC (PC +1 ou adresse Branchement ou jump)
    pc_input : nbit_mux2to1
        generic map(n => 8)
        PORT map(
            a => int_adder1, b => next_address1_out,
            sel => branchOrJump,
            z => next_address2_out
        );
    
    -- mux qui determine signaux de controles qui vont dans le ID/EX
    signal_muxControl <= (9 => jump, 8 => RegDst, 7 => Branch, 6 => MemToReg, 5 => RegWrite, 4 => MemWrite, 3 => MemRead, 2 => ALU_SRC, 1 => ALU_OP1, 0 => ALU_OP0);
    input_mux_control : nbit_mux2to1
        generic map(n => 10)
        PORT map(
            a => signal_muxControl, b => "0000000000",
            sel => int_mux,
            z => final_muxControl
        );
    
    -- unite de detection d'hazard
    hazard_unit : HazardDetectionUnit 
        PORT MAP(
            ID_EX_RegRt => o_id_ex(29 downto 27), IF_ID_RegRs => o_if_id(23 downto 21), IF_ID_RegRt => o_if_id(18 downto 16),
            ID_EX_MemRead => o_id_ex(33), 
            i_jmp => jump, i_branch => Branch, i_readData1equalsReadData2 => int_zero, 
            o_IF_IDWrite => int_IfIdWrite,
            o_mux_control => int_mux,
            o_PCWrite => int_PCWrite,
            o_IF_ID_Flush => int_IfIdFlush
        );

    -- END SECTION ID --

    -- [7 - 0] read_data_1
	-- [15 - 8] read_data_2
	-- [23 - 16] instruction[7 - 0]
	-- [26 - 24] rs
	-- [29 - 27] rt
	-- [31 - 30] ALU_OP
	-- [32] ALU source
	-- [33] MEM read
	-- [34] MEM write
	-- [35] Reg write
	-- [36] MEM to Reg
	-- [37] beq
	-- [38] reg Dst
	-- [39] jump
	-- [42 - 40] rd
    i_id_ex(42 downto 40) <= o_if_id(13 downto 11); -- rd
	i_id_ex(39 downto 30) <= final_muxControl; 
	i_id_ex(29 downto 27) <= o_if_id(18 downto 16); -- rt
	i_id_ex(26 downto 24) <= o_if_id(23 downto 21); -- rs
	i_id_ex(23 downto 16) <= o_if_id(7  downto 0); -- instruction(7 downto 0)
	i_id_ex(15 downto 8) <= readData2; 
	i_id_ex(7 downto 0) <= readData1; 

    ID_EX : nbit_reg
        generic MAP(n => 43)
        PORT MAP(
            i_clock => i_clock, i_load => '1', asynch_reset => i_asynch_reset,
            i_value => i_id_ex,
            o_value => o_id_ex
        );
    
    -- start Section EX --

    -- deux mux du forwarding unit
    muxAForwardingUnit : nbit_mux4to1 
        generic map (n => 8)
        PORT MAP(
            a => o_id_ex(7 downto 0), b => writeData_out, c => o_ex_mem(7 downto 0), d => "00000000",
            sel => int_forwardA, 
            o_mux => muxAForUnit
        );
    muxBForwardingUnit : nbit_mux4to1 
        generic map (n => 8)
        PORT MAP(
            a => o_id_ex(15 downto 8), b => writeData_out, c => o_ex_mem(7 downto 0), d => "00000000",
            sel => int_forwardB, 
            o_mux => muxBForUnit
        );

    -- mux qui determine deuxieme operande de lalu
    AluSrcMux : nbit_mux2to1
        generic map(n => 8)
        PORT map(
            a => muxBForUnit, b => o_id_ex(23 downto 16),
            sel => o_id_ex(32),
            z => aluSrc_out
        );

    -- mux qui determine reg dest
    writeRegMux : nbit_mux2to1
        generic map(n => 3)
        PORT map(
            a => o_id_ex(29 downto 27), b => o_id_ex(42 downto 40),
            sel => o_id_ex(38),
            z => write_reg_out
        );
    
    -- gros ALU du datapath
    ALU_CPU : ALU
        generic map (n => 8)
        PORT MAP(
            i_ALU_OP => alu_Control_Vector,
            i_a => muxAForUnit, i_b => aluSrc_out, 
            o_ALU => ALU_Out,
            o_overflow => int_overflow
        );

    -- control unit de lalu
    controlAlu:  ALU_Control 
        PORT MAP(
            i_ALU_Op0 => o_id_ex(30), i_ALU_Op1 => o_id_ex(31),
            i_Func_Field => o_id_ex(21 downto 16), 
            o_operation => alu_Control_Vector
        );
    
    forward: ForwardingUnit 
        PORT MAP(
            i_EX_MEM_RegRd => o_ex_mem(10 downto 8), i_MEM_WB_RegRd => o_mem_wb(18 downto 16), i_ID_EX_RegRs => o_id_ex(26 downto 24), i_ID_EX_RegRt => o_id_ex(29 downto 27),
            i_EX_MEM_RegWrite => o_ex_mem(13), i_MEM_WB_RegWrite => o_mem_wb(19),
            o_forwardA => int_forwardA, o_forwardB => int_forwardB
        );

    -- END Section EX --

    -- [7 - 0] ALU out
	-- [10 - 8] Reg write Dst
	-- [11] MEM read
	-- [12] MEM write
	-- [13] Reg write
	-- [14] MEM to Reg
	-- [22 - 15] read_data_2

    i_ex_mem(7 downto 0) <= ALU_Out;
    i_ex_mem(10 downto 8) <= write_reg_out;
    i_ex_mem(11) <= o_id_ex(33);
    i_ex_mem(12) <= o_id_ex(34);
    i_ex_mem(13) <= o_id_ex(35);
    i_ex_mem(14) <= o_id_ex(36);
    i_ex_mem(22 downto 15) <= muxBForUnit;
    
    EX_MEM : nbit_reg
        generic MAP(n => 23)
        PORT MAP(
            i_clock => i_clock, i_load => '1', asynch_reset => i_asynch_reset,
            i_value => i_ex_mem,
            o_value => o_ex_mem
        );
    
    -- Start Section WB --

    -- memoire donnee labo
    RAM : registered_ram
        PORT Map   (
            address	 => o_ex_mem(7 downto 0),
            clock => i_mem_clock,
            data => o_ex_mem(22 downto 15),
            wren => o_ex_mem(12),
            q => ramOut
        );


    -- END SECTION WB -- 

	-- [7 - 0] ALU out
	-- [15 - 8] MEM out
	-- [18 - 16] Reg write Dst
	-- [19] Reg write
	-- [20] MEM to Reg
    i_mem_wb(7 downto 0) <= o_ex_mem(7 downto 0);
    i_mem_wb(15 downto 8) <= ramOut;
    i_mem_wb(18 downto 16) <= o_ex_mem(10 downto 8);
    i_mem_wb(19) <= o_ex_mem(13);
    i_mem_wb(20) <= o_ex_mem(14);

    MEM_WB : nbit_reg
        generic MAP(n => 21)
        PORT MAP(
            i_clock => i_clock, i_load => '1', asynch_reset => i_asynch_reset,
            i_value => i_mem_wb,
            o_value => o_mem_wb
        );

    -- Start section WriteBack
    -- data qui va dans input write data des registres
    writeData_mux : nbit_mux2to1
        generic map(n => 8)
        PORT map(
            a => o_mem_wb(7 downto 0), b => o_mem_wb(15 downto 8),
            sel => o_mem_wb(20),
            z => writeData_out
        );


     -- output driver -- 
     o_instruction <= currentInstruction;
     o_branch <= Branch;
     o_zero <= int_zero;
     o_memWrite <= MemWrite;
     o_regWrite <= RegWrite;
 
     -- signal indique dans document du laboratoire
     signalOut <= (7 => '0', 6 => RegDst, 5=>jump, 4=> MemRead, 3=>MemToReg, 2=> ALU_OP1, 1=>ALU_OP0, 0=> ALU_SRC);
 
     -- output du laboratoire dependant de input value select
     outputmux: nbit_mux8to1
         generic map(n => 8)
         Port map (reg0 => addressPC, reg1 => ALU_Out, reg2=> readData1,
             reg3=> readData2, reg4 => writeData_out, reg5 => signalOut, 
             reg6 => signalOut, reg7 => signalOut, i_sel =>i_valueSelect,
             o_mux => o_labMux);
end rtl;