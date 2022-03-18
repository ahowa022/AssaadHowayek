Library ieee;
Use ieee.std_logic_1164.ALL;

entity fixedPoint_multiplier_tb is
end fixedPoint_multiplier_tb;

ARCHITECTURE testbench of fixedPoint_multiplier_tb is
    signal i_a_tb, i_b_tb : std_logic_vector(8 downto 0);
    signal o_result_tb : std_logic_vector(17 downto 0);

    component fixedPoint_multiplier IS
	PORT(
        i_m, i_q : IN STD_LOGIC_VECTOR(8 downto 0);
        o_result: OUT STD_LOGIC_VECTOR(17 downto 0)
	);
    END component; 

begin
    dut: fixedPoint_multiplier
		port map (i_a_tb, i_b_tb, o_result_tb);

    testbench_process: process is
	begin
		i_a_tb <= "000001000"; i_b_tb <= "000001000";  wait for 10 ns;
		i_a_tb <= "000000100"; i_b_tb <= "000000100";  wait for 10 ns;
        i_a_tb <= "111111111"; i_b_tb <= "111111111";  wait for 10 ns;
		wait;
	end process testbench_process;

end testbench;