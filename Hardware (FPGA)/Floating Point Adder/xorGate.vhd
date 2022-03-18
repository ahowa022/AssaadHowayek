LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY xorGate IS
	PORT(
	a, b: IN STD_LOGIC; -- inputs
	c : OUT STD_LOGIC); --output
END xorGate;

ARCHITECTURE rtl OF xorGate IS

BEGIN
	c <= a xor b; 
END rtl; 
