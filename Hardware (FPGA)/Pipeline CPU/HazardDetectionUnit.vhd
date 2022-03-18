LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY HazardDetectionUnit IS
	PORT(
		ID_EX_RegRt, IF_ID_RegRs, IF_ID_RegRt : IN std_logic_vector(2 downto 0); -- registre pour hasard decrochage donnee
        ID_EX_MemRead : IN std_logic; -- signal de control pour hasard decrochage donnee
        i_jmp, i_branch, i_readData1equalsReadData2 : IN std_logic; -- signal de control pour flush (decrochage controle)
        o_IF_IDWrite : out std_logic; -- output pour decrochage donnee
        o_mux_control : out std_logic; -- output pour decrochage donnee
        o_PCWrite : out std_logic; -- output pour decrochage donnee
        o_IF_ID_Flush : out std_logic -- output pour decrochage controle
	);
END HazardDetectionUnit; 

Architecture rtl of HazardDetectionUnit is
    signal ID_EX_RegRt_equals_IF_ID_RegRs, ID_EX_RegRt_equals_IF_ID_RegRt, int_hazard_donnee: std_logic; -- signaux pour decrochage donnee
    signal int_branchJump : std_logic; -- signaux pour decrochage 
begin
    -- logique pour hasards de donnee -- 
    ID_EX_RegRt_equals_IF_ID_RegRs <= (ID_EX_RegRt(2) xnor IF_ID_RegRs(2)) and (ID_EX_RegRt(1) xnor IF_ID_RegRs(1)) and (ID_EX_RegRt(0) xnor IF_ID_RegRs(0));
    ID_EX_RegRt_equals_IF_ID_RegRt <= (ID_EX_RegRt(2) xnor IF_ID_RegRt(2)) and (ID_EX_RegRt(1) xnor IF_ID_RegRt(1)) and (ID_EX_RegRt(0) xnor IF_ID_RegRt(0));
    int_hazard_donnee <= ID_EX_MemRead and (ID_EX_RegRt_equals_IF_ID_RegRt or ID_EX_RegRt_equals_IF_ID_RegRs);

    -- logique pour hasard de controle --
    int_branchJump <= i_jmp or (i_branch and i_readData1equalsReadData2);
    
    -- output driver --
    o_IF_ID_Flush <= int_branchJump;
    o_IF_IDWrite <= (not int_hazard_donnee);
    o_PCWrite <= (not int_hazard_donnee);
    o_mux_control <= (int_hazard_donnee);
end rtl;