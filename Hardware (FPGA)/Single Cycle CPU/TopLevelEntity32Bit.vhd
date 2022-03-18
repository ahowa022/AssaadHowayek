LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY TopLevelEntity32Bit IS
	PORT(
        i_clock, i_clock_ram, i_asynch_reset : IN std_logic;
        i_valueSelect : IN std_logic_vector(2 downto 0);
        o_instruction : OUT std_logic_vector(31 downto 0);
        o_branch, o_zero, o_memWrite, o_regWrite : OUT std_logic
	);
END TopLevelEntity32Bit; 

ARCHITECTURE rtl of TopLevelEntity32Bit IS
    component registered_rom IS
        PORT
        (
            address		: IN STD_LOGIC_VECTOR (7 DOWNTO 0);
            clock		: IN STD_LOGIC  := '1';
            q		: OUT STD_LOGIC_VECTOR (31 DOWNTO 0)
        );
    END component;

    component registered_ram32Bit IS
        PORT
        (
            address		: IN STD_LOGIC_VECTOR (7 DOWNTO 0);
            clock		: IN STD_LOGIC  := '1';
            data		: IN STD_LOGIC_VECTOR (31 DOWNTO 0);
            wren		: IN STD_LOGIC ;
            q		    : OUT STD_LOGIC_VECTOR (31 DOWNTO 0)
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

    component sign_extend is
        port(
            i_value: in std_logic_vector(15 downto 0);
            o_value: out std_logic_vector(31 downto 0)
        );
    end component;

    component nbit_mux2to1 IS
        generic (n : positive := 8);
        PORT(
            a, b: IN STD_LOGIC_VECTOR(n-1 downto 0); -- inputs
            sel: IN STD_LOGIC; --bits de selection
            z: OUT STD_LOGIC_VECTOR(n-1 downto 0)
        ); --output
    END component;

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
    
    -- signal de 32 bits
    signal addressPC, readData1, readData2, ALU_Out, int_adder1, int_adder2, ramOut, signExtendedOffset: std_logic_vector(31 downto 0);

    -- signal rom output
    signal currentInstruction : std_logic_vector(31 downto 0);

    -- signal mux 
    signal write_reg_out : std_logic_vector(4 downto 0);
    signal aluSrc_out, writeData_out, next_address1_out, next_address2_out: std_logic_vector(31 downto 0);

    -- signal du control unit 
    signal RegDst, ALU_SRC, MemToReg, RegWrite, MemRead, MemWrite, Branch, BNE, jump, ALU_OP1, ALU_OP0 : std_logic;
    signal executeBranch : std_logic;

    -- signal statut output alu
    signal int_overflow, int_zero : std_logic;
    -- signal control du alu
    signal alu_Control_Vector : std_logic_vector(2 downto 0);

begin
    -- CPU
    -- PC inclue addresse de la prochaine instruction
    PC : nbit_reg
        generic MAP(n =>32)
        PORT MAP(
            i_clock => i_clock, i_load => '1', asynch_reset => i_asynch_reset,
            i_value => next_address2_out,
            o_value => addressPC
        );

    -- prend rom input et sort linstruction a executer
    ROM : registered_rom
        PORT MAP
        (
            address	=> addressPC(7 downto 0),
            clock => i_clock, 
            q => currentInstruction
        );
    
    -- additionneur qui accomplit PC+1
    PcPlusOne : Additionneur_NBits
        generic map (n => 31)
        port map (
            a => addressPC, b => "00000000000000000000000000000001", 
            cin => '0',
            sum => int_adder1
        );
    
    -- extend le signe de l'offset de l,instruction
    SignExtension: sign_extend
        port MAP(
                i_value => currentInstruction(15 downto 0),
                o_value => signExtendedOffset
        );
    
    -- additionneur qui calculs address de branchement
    BranchAddress : Additionneur_NBits
        generic map (n => 31)
        port map (
            a => int_adder1, b => signExtendedOffset, 
            cin => '0',
            sum => int_adder2
        );
    
    -- mux qui va dans le write data de fichier de registre
    writeRegMux : nbit_mux2to1
        generic map(n => 5)
        PORT map(
            a => currentInstruction(20 downto 16), b => currentInstruction(15 downto 11),
            sel => RegDst,
            z => write_reg_out
        );
    
    
    -- registre du labo
    fichier_registre : registres
        generic map (size => 32)
        PORT map (
            i_clock => i_clock, i_asynch_reset => i_asynch_reset, 
            i_regWrite => RegWrite, 
            i_readReg1 => currentInstruction(23 downto 21), i_readReg2 => currentInstruction(18 downto 16), i_writeReg => write_reg_out(2 downto 0),
            i_writeData => writeData_out, 
            o_read_data1 => readData1, o_read_data2 => readData2
        );

    -- gros ALU du datapath
    ALU_CPU : ALU
        generic map (n => 32)
        PORT MAP(
            i_ALU_OP => alu_Control_Vector,
            i_a => readData1, i_b => aluSrc_out, 
            o_ALU => ALU_Out,
            o_overflow => int_overflow, o_zero => int_zero
        );
    
    -- control unit de lalu
    controlAlu:  ALU_Control 
        PORT MAP(
            i_ALU_Op0 => ALU_OP0, i_ALU_Op1 => ALU_OP1,
            i_Func_Field => currentInstruction(5 downto 0), 
            o_operation => alu_Control_Vector
        );
    
    -- mux qui determine deuxieme operande de lalu
    AluSrcMux : nbit_mux2to1
        generic map(n => 32)
        PORT map(
            a => readData2, b => signExtendedOffset,
            sel => ALU_SRC,
            z => aluSrc_out
        );
    
    -- memoire du labo
    RAM : registered_ram32Bit
        PORT Map   (
            address	 => ALU_Out(7 downto 0),
            clock => i_clock_ram,
            data => readData2,
            wren => MemWrite,
            q => ramOut
        );

    -- data qui va dans input write data des registres
    writeData_mux : nbit_mux2to1
        generic map(n => 32)
        PORT map(
            a => ALU_Out, b => ramOut,
            sel => MemToReg,
            z => writeData_out
        );
    
    -- control de tous le labo
    controlCPU : ControlUnit
        PORT MAP(
            i_op_code => currentInstruction(31 downto 26),
            o_RegDst => RegDst, o_ALU_SRC => ALU_SRC, o_MemToReg => MemToReg, o_RegWrite => RegWrite, o_MemRead => MemRead, o_MemWrite => MemWrite, 
                o_Branch => Branch, o_BNE => BNE, o_jump => jump, o_ALU_OP1 => ALU_OP1, o_ALU_OP0 => ALU_OP0
        );
    
    executeBranch <= (Branch and int_zero) or (BNE and (not int_zero));

    -- mux 1 pour prochain addresse - facile a visualiser dans datapath du rapport
    next_address_1 : nbit_mux2to1
        generic map(n => 32)
        PORT map(
            a => int_adder1, b => int_adder2,
            sel => executeBranch,
            z => next_address1_out
        );

    -- mux 2 address prochain addresse - facile a visualiser dans datapath du rapport
    next_address_2 : nbit_mux2to1
        generic map(n => 32)
        PORT map(
            a => next_address1_out, b(31 downto 28) => addressPC(31 downto 28), b(27 downto 2) => currentInstruction(25 downto 0), b(1 downto 0) =>"00",
            sel => jump,
            z => next_address2_out
        );
    
    -- output driver
    o_instruction <= currentInstruction;
    o_branch <= Branch;
    o_zero <= int_zero;
    o_memWrite <= MemWrite;
    o_regWrite <= RegWrite;

end rtl;