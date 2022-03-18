Library ieee;
Use ieee.std_logic_1164.ALL;

Entity nbit_reg IS
	generic (n : positive := 7);
	PORT (
		i_clock, i_load, asynch_reset: IN STD_LOGIC;
		i_value: IN STD_LOGIC_VECTOR(n-1 downto 0);
		o_value: OUT STD_LOGIC_VECTOR(n-1 downto 0)
	);
END nbit_reg;

ARCHITECTURE rtl OF nbit_reg IS
	COMPONENT enARdFF_2
		PORT(
			i_resetBar	: IN	STD_LOGIC;
			i_d		: IN	STD_LOGIC;
			i_enable	: IN	STD_LOGIC;
			i_clock		: IN	STD_LOGIC;
			o_q, o_qBar	: OUT	STD_LOGIC
		);
	END COMPONENT;

BEGIN
	loop_n : FOR i IN 0 TO n-1 GENERATE
		bitn: enARdFF_2 PORT MAP (
			i_resetBar => asynch_reset, i_d => i_value(i), i_enable => i_load, i_clock => i_clock,
			o_q => o_value(i)
		);
	END GENERATE;
END rtl;
