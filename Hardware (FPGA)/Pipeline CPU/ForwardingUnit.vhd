LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY ForwardingUnit IS
	PORT(
        i_EX_MEM_RegRd, i_MEM_WB_RegRd, i_ID_EX_RegRs, i_ID_EX_RegRt : IN std_logic_vector(2 downto 0); -- registre necessaire for forward unit
        i_EX_MEM_RegWrite, i_MEM_WB_RegWrite : IN std_logic; -- signaux necessaires for forwarding unit
        o_forwardA, o_forwardB : OUT std_logic_vector(1 downto 0) -- entre mux controle pour forwarding unit
	);
END ForwardingUnit; 

ARCHITECTURE rtl of ForwardingUnit IS	
    -- declare all important signals to obtain output
    signal int_EX_MEM_RegRd_isNotZero, int_MEM_WB_RegRd_isNotZero : std_logic;
    signal int_Ex_MEM_RegRd_equals_ID_EX_RegRs, int_Ex_MEM_RegRd_equals_ID_EX_RegRt : std_logic;
    signal int_MEM_WB_RegRd_equals_ID_EX_RegRs, int_MEM_WB_RegRd_equals_ID_EX_RegRt : std_logic;
    signal int_forwardA_msb, int_forwardA_lsb, int_forwardB_msb, int_forwardB_lsb : std_logic;

begin 
    -- determiner si RegRd est 0 pour Ex/mem et mem/wb
    int_EX_MEM_RegRd_isNotZero <= i_EX_MEM_RegRd(2) or i_EX_MEM_RegRd(1) or i_EX_MEM_RegRd(0);
    int_MEM_WB_RegRd_isNotZero <= i_MEM_WB_RegRd(2) or i_MEM_WB_RegRd(1) or i_MEM_WB_RegRd(0);

    -- Ex/mem rd = id/ex rs 
    int_Ex_MEM_RegRd_equals_ID_EX_RegRs <=  ( (i_EX_MEM_RegRd(2) xnor i_ID_EX_RegRs(2)) and (i_EX_MEM_RegRd(1) xnor i_ID_EX_RegRs(1)) and (i_EX_MEM_RegRd(0) xnor i_ID_EX_RegRs(0)) );
    -- Ex/mem rd = id/ex rt
    int_Ex_MEM_RegRd_equals_ID_EX_RegRt <=  ( (i_EX_MEM_RegRd(2) xnor i_ID_EX_RegRt(2)) and (i_EX_MEM_RegRd(1) xnor i_ID_EX_RegRt(1)) and (i_EX_MEM_RegRd(0) xnor i_ID_EX_RegRt(0)) );

    -- Mem/wb rd = id/ex rs 
    int_MEM_WB_RegRd_equals_ID_EX_RegRs <=  ( (i_MEM_WB_RegRd(2) xnor i_ID_EX_RegRs(2)) and (i_MEM_WB_RegRd(1) xnor i_ID_EX_RegRs(1)) and (i_MEM_WB_RegRd(0) xnor i_ID_EX_RegRs(0)) );
    -- Mem/wb rd = id/ex rt
    int_MEM_WB_RegRd_equals_ID_EX_RegRt <=  ( (i_MEM_WB_RegRd(2) xnor i_ID_EX_RegRt(2)) and (i_MEM_WB_RegRd(1) xnor i_ID_EX_RegRt(1)) and (i_MEM_WB_RegRd(0) xnor i_ID_EX_RegRt(0)) );

    -- output driver --
    int_forwardA_msb <= i_EX_MEM_RegWrite and int_EX_MEM_RegRd_isNotZero and int_Ex_MEM_RegRd_equals_ID_EX_RegRs;
    int_forwardA_lsb <= i_MEM_WB_RegWrite and int_MEM_WB_RegRd_isNotZero and int_MEM_WB_RegRd_equals_ID_EX_RegRs and (not int_forwardA_msb); -- ajoute terme a la fin comme EX/MEM a priority sur mem/WB

    int_forwardB_msb <= i_EX_MEM_RegWrite and int_EX_MEM_RegRd_isNotZero and int_Ex_MEM_RegRd_equals_ID_EX_RegRt;
    int_forwardB_lsb <= i_MEM_WB_RegWrite and int_MEM_WB_RegRd_isNotZero and int_MEM_WB_RegRd_equals_ID_EX_RegRt and (not int_forwardB_msb); -- ajoute terme a la fin comme EX/MEM a priority sur mem/WB

    o_forwardA(1) <= int_forwardA_msb;
    o_forwardA(0) <= int_forwardA_lsb;

    o_forwardB(1) <= int_forwardB_msb;
    o_forwardB(0) <= int_forwardB_lsb;

end rtl;
    