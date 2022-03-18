Library ieee;
Use ieee.std_logic_1164.ALL;

entity demux8to1_tb is
end entity demux8to1_tb;

architecture tb of demux8to1_tb is
    signal i_regwrite_tb: std_logic;
    signal i_sel_tb : std_logic_vector(2 downto 0);
    signal o_regWriteVector_tb : std_logic_vector(7 downto 0);
	
	constant period: time := 50 ns; -- set period of time for clock

	component demux8to1 IS
        port(
            i_regWrite: in std_logic;
            i_select: in std_logic_vector(2 downto 0);
            o_regWriteVector: out std_logic_vector(7 downto 0)
        );
    end component; 

begin
	dut: demux8to1
		port map (i_regwrite_tb, i_sel_tb, o_regWriteVector_tb);

	stimulus: process is
	begin
	    i_regwrite_tb <= '1';
        -- play around with select vectors
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