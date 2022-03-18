LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY sevenBitMux2to1 IS
	PORT(
	a, b: IN STD_LOGIC_VECTOR(6 downto 0); -- inputs
	sel: STD_LOGIC; --bits de selection
	c: OUT STD_LOGIC_VECTOR(6 downto 0)); --output
END sevenBitMux2to1;

ARCHITECTURE rtl OF sevenBitMux2to1 IS
	signal int_z: std_logic_vector(6 downto 0);
	component mux2to1 is
		port (
		a, b: IN STD_LOGIC; -- inputs
		sel: STD_LOGIC; --bits de selection
		c: OUT STD_LOGIC); --output
	end component mux2to1;
BEGIN

	loop1: for i in 0 to 6 generate
		bit_0_mux: mux2to1
			port map (
				a => a(i), b => b(i), sel => sel, 
				c => int_z(i)
			);
	end generate;
	c <= int_z; 
END rtl ;