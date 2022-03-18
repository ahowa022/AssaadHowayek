Library ieee;
Use ieee.std_logic_1164.ALL;

entity ForwardingUnit_TB is
end entity ForwardingUnit_TB;

architecture tb of ForwardingUnit_TB is
    signal i_EX_MEM_RegRd, i_MEM_WB_RegRd, i_ID_EX_RegRs, i_ID_EX_RegRt : std_logic_vector(2 downto 0);
    signal i_EX_MEM_RegWrite, i_MEM_WB_RegWrite : std_logic;
    signal o_forwardA, o_forwardB : std_logic_vector(1 downto 0);

	constant period: time := 50 ns; -- set period of time for clock

	component ForwardingUnit IS
        PORT(
            i_EX_MEM_RegRd, i_MEM_WB_RegRd, i_ID_EX_RegRs, i_ID_EX_RegRt : IN std_logic_vector(2 downto 0); -- registre necessaire for forward unit
            i_EX_MEM_RegWrite, i_MEM_WB_RegWrite : IN std_logic; -- signaux necessaires for forwarding unit
            o_forwardA, o_forwardB : OUT std_logic_vector(1 downto 0) -- entre mux controle pour forwarding unit
        );
    END component; 

begin
	dut: ForwardingUnit
		port map (i_EX_MEM_RegRd, i_MEM_WB_RegRd, i_ID_EX_RegRs, i_ID_EX_RegRt, i_EX_MEM_RegWrite, i_MEM_WB_RegWrite, o_forwardA, o_forwardB);

	stimulus: process is
	begin
	    i_EX_MEM_RegRd <= "111";
        i_MEM_WB_RegRd <= "011";
        i_ID_EX_RegRs <= "111";
        i_ID_EX_RegRt <= "111";
        i_EX_MEM_RegWrite <= '1';
        i_MEM_WB_RegWrite <= '1'; 
        wait for period; -- output forwardA 10 forwardB 10

        i_ID_EX_RegRs <= "011";
        i_ID_EX_RegRt <= "011";
        wait for period; -- output forwardA 01 forwardB 01

        i_ID_EX_RegRs <= "100";
        i_ID_EX_RegRt <= "100";
        wait for period; -- output forwardA 00 forwardB 00

        i_ID_EX_RegRs <= "111";
        i_ID_EX_RegRt <= "111";
        i_MEM_WB_RegRd <= "111";
        wait for period; -- verifie priority -> forwardA 10 forwardB 10

		wait;
	end process stimulus;
end tb;