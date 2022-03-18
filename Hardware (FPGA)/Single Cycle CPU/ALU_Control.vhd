LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY ALU_Control IS
	PORT(
        i_ALU_Op0, i_ALU_Op1 : IN std_logic;
        i_Func_Field : IN std_logic_vector(5 downto 0);
        o_operation : OUT std_logic_vector(2 downto 0)
	);
END ALU_Control;

ARCHITECTURE rtl of ALU_Control IS 
begin 
    o_operation(2) <= (i_ALU_Op0) or (i_ALU_Op1 and i_Func_Field(1));
    o_operation(1) <= (not i_ALU_Op1) or (not i_Func_Field(2));
    o_operation(0) <= (i_ALU_Op1) and (i_Func_Field(3) or i_Func_Field(0));
end rtl;