Library ieee;
Use ieee.std_logic_1164.ALL;

entity ALU_TB is
end entity ALU_TB;

architecture tb of ALU_TB is
    signal i_ALUOp_tb: std_logic_vector(2 downto 0);
	signal o_overflow_tb, o_zero_tb: std_logic;
    signal i_a_tb, i_b_tb, o_ALU_tb : std_logic_vector(3 downto 0);

	constant period: time := 50 ns; -- set period of time for clock

	component ALU IS
        generic (n : positive := 8);
        PORT(
            i_ALU_OP : in std_logic_vector(2 downto 0);
            i_a, i_b : in std_logic_vector(n-1 downto 0);
            o_ALU : out std_logic_vector(n-1 downto 0);
            o_overflow, o_zero : out std_logic
        );
    END component; 

begin
	dut: ALU
        generic map(n => 4)
		port map (i_ALUOp_tb, i_a_tb, i_b_tb, o_ALU_tb, o_overflow_tb, o_zero_tb);

	stimulus: process is
	begin
	    i_ALUOp_tb <= "000"; -- AND Operation
        i_a_tb <= "1010";
        i_b_tb <= "1100";
        wait for period;

        i_ALUOp_tb <= "001"; -- OR Operation
        wait for period;

        i_ALUOp_tb <= "010"; -- ADD (should generate overflow)
        wait for period;

        i_ALUOp_tb <= "110"; -- Sub Operation
        wait for period;

        i_a_tb <= "0011";
        i_b_tb <= "0011";
        wait for period; -- sub operation

        wait for period;
		wait;
	end process stimulus;
end tb;