LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

entity nbit_complement_TB is 
end nbit_complement_TB;

architecture testbench of nbit_complement_TB is
    signal i_value_tb, o_value_tb : STD_LOGIC_VECTOR(7 downto 0);
    signal i_complement_tb : STD_LOGIC;

    component nbit_complement IS --design under test
        generic (n : positive := 8);
        PORT (
            a : IN STD_LOGIC_VECTOR(n-1 downto 0);
            i_complement: IN STD_LOGIC;
            o_value: OUT STD_LOGIC_VECTOR(n-1 downto 0)
        );
    END component;

begin
    DUT: nbit_complement -- connect test bench signals to signals of eight bit register
        generic map(n => 8)
        port map (i_value_tb, i_complement_tb, o_value_tb);

    testbench_process : process
    begin

        i_value_tb <= "00000000"; -- value to complement
        i_complement_tb <= '0'; -- deactivate complement
        wait for 225 ns;
        i_complement_tb <= '1'; -- activate complement
        wait for 225 ns; 

        i_complement_tb <= '0'; -- deactivate complement
        wait for 225 ns;

        i_value_tb <= "10101010"; -- value to complement
        wait for 225 ns;
        i_complement_tb <= '1'; -- activate complement
        wait for 225 ns;

        wait;
    end process;
end testbench;
