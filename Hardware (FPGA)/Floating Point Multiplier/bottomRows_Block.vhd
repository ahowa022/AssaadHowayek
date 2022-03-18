LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY bottomRows_Block IS
	PORT(
        mk, qj : IN STD_LOGIC;
        ppi: IN STD_LOGIC;
        cin : IN STD_LOGIC;
        sum, cout : OUT STD_LOGIC
	);
END bottomRows_Block; 

ARCHITECTURE rtl of bottomRows_Block is 
    signal int_and : std_logic;

    COMPONENT Additionneur_1Bit 
		PORT (
			a, b, cin: IN STD_LOGIC;
			sum, cout: OUT STD_LOGIC
		);
	END COMPONENT;
begin 
    -- conception de base -> inclue dans le rapport pour explication
    int_and <= mk and qj;
    
    adder : Additionneur_1Bit
        port map(a => ppi, b=> int_and, cin => cin, sum => sum, cout => cout);

end rtl;