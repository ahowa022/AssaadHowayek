Library ieee;
Use ieee.std_logic_1164.ALL;

entity ControlUnit_TB is
end entity ControlUnit_TB;

architecture tb of ControlUnit_TB is
    signal i_OpCode_tb: std_logic_vector(5 downto 0);
	signal o_RegDst_tb, o_ALU_SRC_tb, o_MemToReg_tb, o_RegWrite_tb, o_MemRead_tb, o_MemWrite_tb, 
        o_Branch_tb, O_BNE_tb, o_jmp_tb, o_ALU_OP1_tb, o_ALU_OP0_tb: std_logic;
	constant period: time := 50 ns; -- set period of time for clock

	component ControlUnit IS
        PORT(
            i_op_code : IN std_logic_vector(5 downto 0);
            o_RegDst, o_ALU_SRC, o_MemToReg, o_RegWrite, o_MemRead, o_MemWrite, 
                o_Branch, o_BNE, o_jump, o_ALU_OP1, o_ALU_OP0 : OUT std_logic
        );
    end component; 

begin
	dut: ControlUnit
		port map (i_OpCode_tb, o_RegDst_tb, o_ALU_SRC_tb, o_MemToReg_tb, o_RegWrite_tb, o_MemRead_tb, o_MemWrite_tb, 
            o_Branch_tb, O_BNE_tb, o_jmp_tb,o_ALU_OP1_tb, o_ALU_OP0_tb);

	stimulus: process is
	begin
	    i_OpCode_tb <= "000000"; -- type R instruction -> verify output
        wait for period;

        i_OpCode_tb <= "100011"; -- lw instruction -> verify output
        wait for period;

        i_OpCode_tb <= "101011"; -- sw instruction -> verify output
        wait for period;

        i_OpCode_tb <= "000100"; -- beq instruction -> verify output
        wait for period;

        i_OpCode_tb <= "000101"; -- bne instruction -> verify output
        wait for period;

        i_OpCode_tb <= "000010"; -- jmp instruction -> verify output
        wait for period;
		wait;
	end process stimulus;
end tb;