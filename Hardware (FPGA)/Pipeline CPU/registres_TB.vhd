LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

entity registres_TB is 
end registres_TB;

architecture testbench of registres_TB is
    signal clk_tb, asynch_reset_tb, i_regWrite_tb : std_logic; -- signal control et synchronisation test bench
    signal i_readReg1_tb, i_readReg2_tb, i_writeReg_tb : std_logic_vector(2 downto 0); -- input component
    signal i_writeData_tb, o_read_data1_tb, o_read_data2_tb : std_logic_vector(7 downto 0); -- input/output component

    signal sim_end : boolean := false; --signals the end of the simulation
    constant period: time := 50 ns; -- set period of time for clock

    component registres IS --design under test
        generic (size : positive := 8);
        PORT(
            i_clock, i_asynch_reset : IN std_logic;
            i_regWrite : IN std_logic;
            i_readReg1, i_readReg2, i_writeReg: IN std_logic_vector(2 downto 0);
            i_writeData : IN std_logic_vector(size-1 downto 0);
            o_read_data1, o_read_data2 : OUT std_logic_vector(size-1 downto 0)
        );
    END component;

begin
    DUT: registres -- connect test bench signals to signals of eight bit register
        generic map(size => 8)
        port map (clk_tb, asynch_reset_tb, i_regWrite_tb, i_readReg1_tb, i_readReg2_tb, i_writeReg_tb, i_writeData_tb, o_read_data1_tb, o_read_data2_tb);

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
        -- debut simulation -> set tous les valuers
        asynch_reset_tb <= '0'; 
        i_regWrite_tb <= '0'; 
        i_readReg1_tb <= "000";
        i_readReg2_tb <= "001";
        i_writeReg_tb <= "001";
        i_writeData_tb <= "00001111";

        wait for period/2; 
        asynch_reset_tb <= '1';
        i_regWrite_tb <='1'; 
        wait for period;
        
        i_regWrite_tb <= '1';
        i_writeReg_tb <= "000";
        i_writeData_tb <= "11110000";
        wait for period;
        
        i_regWrite_tb <= '1';
        i_writeReg_tb <= "010";
        i_writeData_tb <= "11111111";
        wait for period;
        
        i_regWrite_tb <= '0';
        i_readReg1_tb <= "010";
        wait for period;

        sim_end <= true;
        wait;
    end process;
end testbench;
