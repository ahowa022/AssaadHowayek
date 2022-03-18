LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY nbit_mux2to1 IS
	generic (n : positive := 8);
	PORT(
        a, b: IN STD_LOGIC_VECTOR(n-1 downto 0); -- inputs
        sel: IN STD_LOGIC; --bits de selection
        z: OUT STD_LOGIC_VECTOR(n-1 downto 0)
        ); --output
END nbit_mux2to1;

ARCHITECTURE rtl OF nbit_mux2to1 IS
	signal int_z: std_logic_vector(n-1 downto 0);
BEGIN
	loop_n : FOR i IN 0 TO n-1 GENERATE
		int_z(i) <= (not(sel) and a(i)) or (sel and b(i));
		z(i) <= int_z(i);
	end generate;  
END rtl ;