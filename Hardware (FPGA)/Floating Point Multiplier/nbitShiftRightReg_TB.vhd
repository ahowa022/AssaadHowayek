LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

entity nbitShiftRightReg_TB is 
end nbitShiftRightReg_TB;

architecture testbench of nbitShiftRightReg_TB is
    signal clk_tb, load_tb, clear_tb, shift_tb, serial_input_tb : std_logic; -- signal control test bench
    signal asynch_reset_tb: std_logic; -- signal used to reset whole system
    signal input_tb, output_tb : std_logic_vector(8 downto 0);
    constant period: time := 50 ns; -- set period of time for clock
    signal sim_end : boolean := false; --signals the end of the simulation

    component nbitShiftRightReg IS --design under test
        generic (n : positive := 9);
        PORT (
            i_clock, asynch_reset: IN STD_LOGIC;
            i_load, i_clear, i_shift : IN STD_LOGIC;
            i_serial_input : IN STD_LOGIC;
            i_value: IN STD_LOGIC_VECTOR(n-1 downto 0);
            o_value: OUT STD_LOGIC_VECTOR(n-1 downto 0)
        );
    END component;

begin
    DUT: nbitShiftRightReg
        generic map(n => 9)
        port map (clk_tb, asynch_reset_tb, load_tb, clear_tb, shift_tb, serial_input_tb, input_tb, output_tb);

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
        load_tb <= '0';
        clear_tb <= '0';
        asynch_reset_tb <= '0';
        shift_tb <= '0';
        serial_input_tb <= '0';
        input_tb <= "101010101";

        wait for period/2;
        asynch_reset_tb <= '1';
        load_tb <= '1';

        wait for period;
        load_tb <= '0'; -- deactivate load activate shift

        shift_tb <= '1';
        wait for period*10; -- test shift for shifting right every bit

        shift_tb <= '0';
        load_tb <= '1';
        wait for period;

        clear_tb <= '1';
        load_tb <= '0';
        wait for period*2;

        sim_end <= true;
        wait;
    end process;
end testbench;