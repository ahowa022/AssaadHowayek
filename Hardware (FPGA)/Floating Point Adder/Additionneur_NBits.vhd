Library ieee;
Use ieee.std_logic_1164.ALL;

Entity Additionneur_NBits IS
	generic (n : positive := 3);
	
	port (
		a, b: IN STD_LOGIC_VECTOR(n DOWNTO 0);
		cin: IN STD_LOGIC; -- cin set to 0 when sum and set to 1 when substraction
		cout, zout, sign_msb: OUT STD_LOGIC;
		sum: OUT STD_LOGIC_VECTOR(n DOWNTO 0)
		);
		
END Additionneur_NBits;

ARCHITECTURE basic OF Additionneur_NBits IS

	SIGNAL values: STD_LOGIC_VECTOR(n DOWNTO 0); 
	SIGNAL carry, z: STD_LOGIC_VECTOR(n+1 DOWNTO 0); 
	
	COMPONENT Additionneur_1Bit 
		PORT (
			a, b, cin: IN STD_LOGIC;
			sum, cout: OUT STD_LOGIC
		);
	END COMPONENT;
	
BEGIN
	carry(0) <= cin;
	
	loop_n : FOR i IN 0 TO n GENERATE --Loop to generate N bit adder with N Additionneurs_1Bit (additionneurs en serie)
		additionneur: additionneur_1Bit
			PORT MAP (
				a => a(i), b => b(i), cin => carry(i),
				sum => values(i), cout => carry(i+1)
			);
	END GENERATE;
	
	--output driver
	cout <= carry(n+1);
	z(0) <= '0';
	loop_z : FOR i IN 0 TO n GENERATE --Loop to find zero
		z(i+1) <= z(i) or values(i);
	END GENERATE;

	sum <= values;
	sign_msb <= values(n); --0 => positive and 1 is negative
	zout <= not(z(n+1)); -- Represents the zero. Set to 1 if final value is 0
	
END basic;
