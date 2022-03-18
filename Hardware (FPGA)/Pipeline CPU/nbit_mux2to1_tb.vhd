Library ieee;
Use ieee.std_logic_1164.ALL;

entity nbit_mux2to1_tb is
end entity nbit_mux2to1_tb;

architecture tb of nbit_mux2to1_tb is
    signal reg0_tb, reg1_tb: std_logic_vector(2 downto 0);
    signal i_sel_tb : std_logic;
    signal o_mux_tb : std_logic_vector(2 downto 0);
	
	constant period: time := 50 ns; -- set period of time for clock

	component nbit_mux2to1 IS
        generic (n : positive := 8);
        PORT(
            a, b: IN STD_LOGIC_VECTOR(n-1 downto 0); -- inputs
            sel: IN STD_LOGIC; --bits de selection
            z: OUT STD_LOGIC_VECTOR(n-1 downto 0)
        );
    end component; 

begin
	dut: nbit_mux2to1
        generic map(n => 3)
		port map (reg0_tb, reg1_tb, i_sel_tb, o_mux_tb);

	stimulus: process is
	begin
	    reg0_tb <= "000"; 
        reg1_tb <= "111"; 

        i_sel_tb <= '0';
        wait for period;

        i_sel_tb <= '1';
        wait for period;
        
		wait;
	end process stimulus;
end tb;