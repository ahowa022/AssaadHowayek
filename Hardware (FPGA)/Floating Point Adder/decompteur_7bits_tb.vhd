Library ieee;
Use ieee.std_logic_1164.ALL;

entity decompteur_7bits_tb is
end entity decompteur_7bits_tb;

architecture tb of decompteur_7bits_tb is

	signal in_reset, in_decompteur, in_load, clock, out_zero: std_logic;
	signal in_value: std_logic_vector(6 downto 0):= "0001000";
	signal out_value: std_logic_vector(6 downto 0);
	signal sim_end : boolean := false; --signals the end of the simulation
	constant period: time := 50 ns; -- set period of time for clock

	component decompteur_7bits is
		port(
			d, load, reset, clock: in std_logic; 
			in_value: in std_logic_vector(6 downto 0); --Input value
			z: out std_logic;  -- zero value
			out_value: out std_logic_vector(6 downto 0) --Output value
			);
	end component;

begin
	dut: decompteur_7bits
		port map (in_decompteur, in_load, in_reset, clock, in_value, out_zero, out_value);

	-- this is clock process to simulate clock
    -- it will toggle every half period (which we defined earlier)
    clock_process : process
    begin 
        while(not sim_end) loop
            clock <= '1';
            wait for period/2;
            clock <= '0';
            wait for period/2;
        end loop;
        wait;
    end process;

	stimulus: process is
	begin
	    in_reset <= '0';
		in_decompteur <= '0';
		in_load <= '0';
		wait for period/2;

		in_load <= '1';
		in_reset <= '1';
		wait for period*2;

		in_load <= '0';
		in_decompteur <= '1';
		wait for period*10;

		sim_end <= true;
		wait;
	end process stimulus;
end tb;