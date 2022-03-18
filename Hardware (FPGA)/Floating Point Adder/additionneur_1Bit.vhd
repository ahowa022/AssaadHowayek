LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY additionneur_1Bit IS
	PORT(
	cin, a, b: IN STD_LOGIC;
	sum, cout: OUT STD_LOGIC
	);
END additionneur_1Bit; 

ARCHITECTURE LogicFunc of additionneur_1Bit IS
BEGIN
	sum <= a XOR b XOR cin;
	Cout <= (a AND b) OR (cin AND a) OR (cin AND b);
END LogicFunc;