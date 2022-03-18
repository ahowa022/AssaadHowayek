LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

entity registered_ram_TB is 
end registered_ram_TB;

architecture testbench of registered_ram_TB is
    signal clk_tb, i_write_mem_tb: std_logic;
    signal i_address_tb, i_data_tb, o_ram_tb : std_logic_vector(7 downto 0); -- input component

    signal sim_end : boolean := false; --signals the end of the simulation
    constant period: time := 50 ns; -- set period of time for clock

    component registered_ram IS --design under test
        PORT
        (
            address		: IN STD_LOGIC_VECTOR (7 DOWNTO 0);
            clock		: IN STD_LOGIC  := '1';
            data		: IN STD_LOGIC_VECTOR (7 DOWNTO 0);
            wren		: IN STD_LOGIC ;
            q		: OUT STD_LOGIC_VECTOR (7 DOWNTO 0)
        );
    END component;

begin
    DUT: registered_ram -- connect test bench signals to signals of eight bit register
        port map (i_address_tb, clk_tb, i_data_tb, i_write_mem_tb, o_ram_tb);

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
        i_address_tb <= "00000001";
        i_data_tb <= "00000000";
        i_write_mem_tb <= '0';
        wait for period*5/2; 

        i_data_tb <= "11110000";
        i_write_mem_tb <= '1';
        wait for period*3;
        
        i_data_tb <= "11111111";
        i_address_tb <= "01110111";
        i_write_mem_tb <= '0';
        wait for period*3;
        
        i_write_mem_tb <= '1';
        wait for 3* period;

        i_write_mem_tb <= '0';
        i_address_tb <= "00000010";
        wait for 3* period;
        sim_end <= true;
        wait;
    end process;
end testbench;
