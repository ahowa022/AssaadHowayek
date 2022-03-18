LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

entity seven_bit_counter_TB is 
end seven_bit_counter_TB;

architecture testbench of seven_bit_counter_TB is
    signal clk_tb, load_tb, increment_tb : std_logic; -- signal control test bench
    signal asynch_reset_tb: std_logic; -- signal used to reset whole system
    signal i_value_tb, o_value_tb : std_logic_vector(6 downto 0);
    signal sim_end : boolean := false; --signals the end of the simulation
    constant period: time := 50 ns; -- set period of time for clock

    component seven_bit_counter
    PORT (
        i_clock, asynch_reset: IN STD_LOGIC;
        i_load, i_increment: IN STD_LOGIC;
        i_value: IN STD_LOGIC_VECTOR(6 downto 0);
        o_value: OUT STD_LOGIC_VECTOR(6 downto 0)
    );
    END Component;

begin 
    DUT: seven_bit_counter -- connect test bench signals to signals of eight bit register
        port map (clk_tb, asynch_reset_tb, load_tb, increment_tb, i_value_tb, o_value_tb);
    
    clock_process : process
    begin 
        while(not sim_end) loop
            clk_tb <= '1';
            wait for period/2;
            clk_tb <= '0';
            wait for period/2;
        end loop;
        wait;
    end process;
    
    testbench_process : process
    begin
        load_tb <= '0';
        increment_tb <= '0';
        asynch_reset_tb <= '0';
        i_value_tb <= "0001000";
        wait for period/2;

        asynch_reset_tb <= '1';
        load_tb <= '1';
        wait for 2*period;

        load_tb <= '0';
        increment_tb <= '1';

        wait for period*20;

        increment_tb <= '0';
        wait for period;

        sim_end <= true;
        wait;
    end process;
end testbench;