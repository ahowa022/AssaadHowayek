LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

entity sevenBitComparator_TB is 
end sevenBitComparator_TB;

architecture testbench of sevenBitComparator_TB is
    signal i_Ai_tb, i_Bi_tb: std_logic_vector(6 downto 0);
	signal o_NLT_tb: std_logic;

    component sevenBitComparator IS --design under test
        PORT(
            i_Ai, i_Bi			: IN	STD_LOGIC_VECTOR(6 downto 0);
            o_NLT		: OUT	STD_LOGIC);
    END component;

begin
    dut: sevenBitComparator 
        Port Map(i_Ai_tb, i_Bi_tb, o_NLT_tb);
    
    testbench_process : process
    begin
        -- output signal should be 0
        i_Bi_tb <= "0001001"; -- will always be output used in lab (generates signal NLT9 in ASM Design)
        i_Ai_tb <= "0000000"; 

        wait for 225 ns;
        -- output should be 1
        i_Ai_tb <= "0001010";

        wait for 225 ns;
        -- output should be 1
        i_Ai_tb <= "1000000";

        wait for 225 ns;
        -- output should be 0
        i_Ai_tb <= "0001000";

        wait for 225 ns;
        -- output should be 0
        i_Ai_tb <= "0000001";

        wait for 225 ns;
        -- output should be 1
        i_Ai_tb <= "0010000";
        wait for 225 ns;

        wait;
    end process;
end testbench;