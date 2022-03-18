LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

entity TopLevelEntity_TB is 
end TopLevelEntity_TB;

architecture testbench of TopLevelEntity_TB is
    signal clk_tb, clkMem_tb, asynch_reset_tb : std_logic; -- signal control et synchronisation test bench
    signal i_valueSelect_tb : std_logic_vector(2 downto 0);
    signal o_labMux_tb : std_logic_vector(7 downto 0);
    signal o_instruction_tb : std_logic_vector(31 downto 0);
    signal o_branch_tb, o_zero_tb, o_memWrite_tb, o_regWrite_tb: std_logic;
    

    signal sim_end : boolean := false; --signals the end of the simulation
    constant period: time := 50 ns; -- set period of time for clock
    constant periodMem: time := 0.05 ns; -- set period of time for clock

    component TopLevelEntity IS --design under test
        PORT(
            i_clock, i_mem_clock, i_asynch_reset : IN std_logic;
            i_valueSelect : IN std_logic_vector(2 downto 0);
            o_labMux: OUT std_logic_vector(7 downto 0);
            o_instruction : OUT std_logic_vector(31 downto 0);
            o_branch, o_zero, o_memWrite, o_regWrite : OUT std_logic
        );
    END component;

begin
    DUT: TopLevelEntity 
        port map (clk_tb, clkMem_tb, asynch_reset_tb, i_valueSelect_tb, o_labMux_tb, o_instruction_tb, o_branch_tb, o_zero_tb, o_memWrite_tb, o_regWrite_tb);

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

    clock_process2 : process
    begin 
        while(not sim_end) loop
            clkMem_tb <= '1';
            wait for periodMem/2;
            clkMem_tb <= '0';
            wait for periodMem/2;
        end loop;
        wait;
    end process;

    testbench_process : process
    begin
        -- asynch reset tous le systeme
        asynch_reset_tb <= '0'; 
        i_valueSelect_tb <= "000";
        wait for period/2; 
        asynch_reset_tb <= '1';

        wait for period*8;
        sim_end <= true;
        wait;
    end process;
end testbench;
