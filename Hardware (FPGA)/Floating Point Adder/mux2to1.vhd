LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY mux2to1 IS
	PORT(
	a, b: IN STD_LOGIC; -- inputs
	sel: STD_LOGIC; --bits de selection
	c: OUT STD_LOGIC); --output
END mux2to1;

ARCHITECTURE rtl OF mux2to1 IS
	signal temp_output: std_logic;
BEGIN
	temp_output <= (not(sel) and a) or (sel and b);
	c <= temp_output; 
END rtl ;