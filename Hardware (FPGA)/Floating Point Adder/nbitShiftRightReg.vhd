LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

Entity nbitShiftRightReg IS
	generic (n : positive := 9);
	PORT (
		i_clock, asynch_reset: IN STD_LOGIC;
        i_load, i_clear, i_shift : IN STD_LOGIC;
        i_serial_input : IN STD_LOGIC;
		i_value: IN STD_LOGIC_VECTOR(n-1 downto 0);
		o_value: OUT STD_LOGIC_VECTOR(n-1 downto 0)
	);
END nbitShiftRightReg;

architecture rtl of nbitShiftRightReg is
    signal next_shift_input : std_logic_vector(n downto 0);
    signal next_input : std_logic_vector(n-1 downto 0);
    signal enable     : std_logic;

    COMPONENT enARdFF_2
		PORT(
			i_resetBar	: IN	STD_LOGIC;
			i_d		: IN	STD_LOGIC;
			i_enable	: IN	STD_LOGIC;
			i_clock		: IN	STD_LOGIC;
			o_q, o_qBar	: OUT	STD_LOGIC
		);
	END COMPONENT;

begin 
	-- enable slmt quand on veut load, clear ou shift
    enable <= i_load or i_clear or i_shift;
	-- next shift input du msb va etre le serial input donnee comme input
    next_shift_input(n) <= i_serial_input;

	-- loop qui genere les bascules du registre, prochain input depend du signal dentree
    loop_n : FOR i IN 0 TO n-1 GENERATE
        next_input(i) <= ((i_load and i_value(i)) or (i_shift and next_shift_input(i+1))) and (not i_clear);
		bitn: enARdFF_2 PORT MAP (
			i_resetBar => asynch_reset, i_d => next_input(i), i_enable => enable, i_clock => i_clock,
			o_q => next_shift_input(i)
		);
	END GENERATE;

	-- output cest le current state des bascules
	o_value <= next_shift_input(n-1 downto 0);
end;