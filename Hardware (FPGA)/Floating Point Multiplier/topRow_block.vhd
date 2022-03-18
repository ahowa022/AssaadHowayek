LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY topRow_block IS
	PORT(
        q0, q1: IN STD_LOGIC;
        m0, m1: IN STD_LOGIC;
        cin : IN STD_LOGIC;
        sum, cout : OUT STD_LOGIC
	);
END topRow_block; 

ARCHITECTURE rtl of topRow_block is 
    signal int_a, int_b : std_logic; -- inputs to the full adder 

    COMPONENT Additionneur_1Bit 
		PORT (
			a, b, cin: IN STD_LOGIC;
			sum, cout: OUT STD_LOGIC
		);
	END COMPONENT;
begin 
    -- conception de base -> inclue dans le rapport pour explication
    int_a <= m0 and q1;
    int_b <= m1 and q0;

    adder : Additionneur_1Bit
        port map(a => int_a, b=> int_b, cin => cin, sum => sum, cout => cout);
end rtl;
    
