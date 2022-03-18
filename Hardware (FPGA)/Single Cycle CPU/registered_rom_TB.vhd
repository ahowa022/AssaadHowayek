LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

entity registered_rom_TB is 
end registered_rom_TB;

architecture testbench of registered_rom_TB is
    signal clk_tb: std_logic;
    signal i_address_tb : std_logic_vector(7 downto 0); -- input component
    signal o_rom_tb : std_logic_vector(31 downto 0);

    signal sim_end : boolean := false; --signals the end of the simulation
    constant period: time := 50 ns; -- set period of time for clock

    component registered_rom IS --design under test
        PORT
        (
            address		: IN STD_LOGIC_VECTOR (7 DOWNTO 0);
            clock		: IN STD_LOGIC  := '1';
            q		: OUT STD_LOGIC_VECTOR (31 DOWNTO 0)
        );
    END component;

begin
    DUT: registered_rom -- connect test bench signals to signals of eight bit register
        port map (i_address_tb, clk_tb, o_rom_tb);

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
        wait for period*3; 

        wait for period*3;
        
        i_address_tb <= "00000010";
        wait for period*3;
        
        wait for 3* period;

        i_address_tb <= "00000011";
        wait for 3* period;
        sim_end <= true;
        wait;
    end process;
end testbench;
