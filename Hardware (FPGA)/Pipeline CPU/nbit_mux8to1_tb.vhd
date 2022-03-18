Library ieee;
Use ieee.std_logic_1164.ALL;

entity nbit_mux8to1_tb is
end entity nbit_mux8to1_tb;

architecture tb of nbit_mux8to1_tb is
    signal reg0_tb, reg1_tb, reg2_tb, reg3_tb, reg4_tb, 
        reg5_tb, reg6_tb, reg7_tb: std_logic_vector(2 downto 0);
    signal i_sel_tb : std_logic_vector(2 downto 0);
    signal o_mux_tb : std_logic_vector(2 downto 0);
	
	constant period: time := 50 ns; -- set period of time for clock

	component nbit_mux8to1 IS
        generic (n : positive := 8);
        port(
            reg0, reg1, reg2, reg3, reg4, reg5, reg6, reg7: in std_logic_vector(n-1 downto 0);
            i_sel: in std_logic_vector(2 downto 0);
            o_mux: out std_logic_vector(n-1 downto 0)
        );
    end component; 

begin
	dut: nbit_mux8to1
        generic map(n => 3)
		port map (reg0_tb, reg1_tb, reg2_tb, reg3_tb, reg4_tb, reg5_tb, reg6_tb, reg7_tb,
            i_sel_tb, o_mux_tb);

	stimulus: process is
	begin
	    reg0_tb <= "000"; 
        reg1_tb <= "001"; 
        reg2_tb <= "010"; 
        reg3_tb <= "011";
        reg4_tb <= "100";
        reg5_tb <= "101";
        reg6_tb <= "110";
        reg7_tb <= "111";
        i_sel_tb <= "000";
        wait for period;

        i_sel_tb <= "001";
        wait for period;

        i_sel_tb <= "010";
        wait for period;

        i_sel_tb <= "011";
        wait for period;

        i_sel_tb <= "100";
        wait for period;

        i_sel_tb <= "101";
        wait for period;

        i_sel_tb <= "110";
        wait for period;

        i_sel_tb <= "111";
        wait for period;
		wait;
	end process stimulus;
end tb;