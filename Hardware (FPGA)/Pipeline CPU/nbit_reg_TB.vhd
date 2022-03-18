LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

entity nbit_reg_TB is 
end nbit_reg_TB;

architecture testbench of nbit_reg_TB is
    signal clk_tb, load_tb : std_logic; -- signal control test bench
    signal asynch_reset_tb: std_logic; -- signal used to reset whole system

    signal input_TB, output_TB : std_logic_vector(6 downto 0); --input and output test bench of eight bit register test

    signal sim_end : boolean := false; --signals the end of the simulation

    component nbit_reg IS --design under test
        generic (n : positive := 7);
        PORT (
            i_clock, i_load, asynch_reset: IN STD_LOGIC;
            i_value: IN STD_LOGIC_VECTOR(n-1 downto 0);
            o_value: OUT STD_LOGIC_VECTOR(n-1 downto 0)
        );
    END component;

    constant period: time := 50 ns; -- set period of time for clock

begin
    DUT: nbit_reg -- connect test bench signals to signals of eight bit register
        generic map(n => 7)
        port map (clk_tb, load_tb, asynch_reset_tb, input_TB, output_TB);

    -- this is clock process to simulate clock
    -- it will toggle every half period (which we defined earlier)
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
        asynch_reset_tb <= '0'; -- so the registers do not reset
        input_TB <= "0000011"; 

        --testing load functionality of the eight bit register
        wait for period/2; 
        asynch_reset_tb <= '1';
        load_TB <='1'; 
        wait for period;
        
        --testing load functionality of eight bit register
        input_TB <= "1010101";
        wait for period;
        
        --testing load functionality of eight bit register
        input_TB <= "1111000";
        wait for period;
        
        -- test no change functionality
        input_TB <= "0111000";
        load_TB <='0';
        wait for period;
        
        -- test reset functionality
        asynch_reset_tb <= '0';
        wait for period;

        sim_end <= true;
        wait;
    end process;
end testbench;
