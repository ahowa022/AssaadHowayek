Library ieee;
Use ieee.std_logic_1164.ALL;

entity HazardDetectionUnit_TB is
end entity HazardDetectionUnit_TB;

architecture tb of HazardDetectionUnit_TB is
	signal ID_EX_RegRt, IF_ID_RegRs, IF_ID_RegRt : std_logic_vector(2 downto 0);
    signal ID_EX_MemRead : std_logic;
    signal i_jmp, i_branch, i_readData1equalsReadData2 : std_logic;
    signal o_IF_IDWrite, o_mux_control, o_PCWrite, o_IF_ID_Flush : std_logic;
	constant period: time := 50 ns; -- set period of time for clock

	component HazardDetectionUnit IS
        PORT(
            ID_EX_RegRt, IF_ID_RegRs, IF_ID_RegRt : IN std_logic_vector(2 downto 0); -- registre pour hasard decrochage donnee
            ID_EX_MemRead : IN std_logic; -- signal de control pour hasard decrochage donnee
            i_jmp, i_branch, i_readData1equalsReadData2 : IN std_logic; -- signal de control pour flush (decrochage controle)
            o_IF_IDWrite : out std_logic; -- output pour decrochage donnee
            o_mux_control : out std_logic; -- output pour decrochage donnee
            o_PCWrite : out std_logic; -- output pour decrochage donnee
            o_IF_ID_Flush : out std_logic -- output pour decrochage controle
        );
    end component; 

begin
	dut: HazardDetectionUnit
		port map (ID_EX_RegRt, IF_ID_RegRs, IF_ID_RegRt, ID_EX_MemRead, i_jmp, i_branch, i_readData1equalsReadData2, o_IF_IDWrite, o_mux_control, o_PCWrite, o_IF_ID_Flush);

	stimulus: process is
	begin
        -- verifie output pour branchement (hasard controle)
        -- PC Write = 1, o_mux_control = 0, o_IF_IDWrite = 1, o_IF_ID_Flush = 1
	    ID_EX_RegRt <= "000"; 
        IF_ID_RegRs <= "000"; 
        IF_ID_RegRt <= "000";
        ID_EX_MemRead <= '0';
        i_jmp <= '0';
        i_branch <= '1';
        i_readData1equalsReadData2 <= '1';
        wait for period;

        -- verifie output pour jump (hasard controle)
        -- PC Write = 1, o_mux_control = 0, o_IF_IDWrite = 1, o_IF_ID_Flush = 1
        i_jmp <= '1';
        i_branch <= '0';
        i_readData1equalsReadData2 <= '0';
        wait for period;

        -- verifie output pour premier hasard donnee (idex rt = ifid rs)
        -- PC Write = 0, o_mux_control = 1, o_IF_IDWrite = 0, o_IF_ID_Flush = 0
        ID_EX_RegRt <= "001"; 
        IF_ID_RegRs <= "001"; 
        ID_EX_MemRead <= '1';
        i_jmp <= '0';
        wait for period;

        -- verifie output pour premier hasard donnee (idex rt = ifid rt)
        -- PC Write = 0, o_mux_control = 1, o_IF_IDWrite = 0, o_IF_ID_Flush = 0
        ID_EX_RegRt <= "000";
        wait for period;

        -- verifie output fonction normal -> aucun hasard ni de controle ni donee
        -- PC Write = 1, o_mux_control = 0, o_IF_IDWrite = 1, o_IF_ID_Flush = 0
        ID_EX_RegRt <= "111";
        wait for period;
        
		wait;
	end process stimulus;
end tb;