LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY ControlPath IS
	PORT(
        i_clock, i_asynch_reset : IN STD_LOGIC; -- general clock and reset pour synchroniser tous le systeme
        i_sign, i_nlt9, i_zero, i_cout_Mz : IN STD_LOGIC; -- input des signaux du datapath 
        -- output du control path qui gere tous le datapath
        o_load1, o_load2, o_load3, o_load4, o_load5, o_load6, o_load7 : OUT STD_LOGIC; 
        o_complement_1, o_complement_2 : OUT STD_LOGIC;
        o_cin : OUT STD_LOGIC;
        o_flag0, o_flag1, o_OF_UF0, o_OF_UF1 : OUT STD_LOGIC;
        o_shiftR3, o_shiftR4, o_shiftR5 : OUT STD_LOGIC;
        o_countD6, o_countU7 : OUT STD_LOGIC;
        o_clear3, o_clear4, o_clear5 : OUT STD_LOGIC;
        o_done : OUT STD_LOGIC
	);
END ControlPath; 

ARCHITECTURE rtl of ControlPath IS 
    signal next_input : STD_LOGIC_VECTOR(9 downto 0);
    signal current_state : STD_LOGIC_VECTOR(9 downto 0);

    COMPONENT enARdFF_2
		PORT(
			i_resetBar	: IN	STD_LOGIC;
			i_d		    : IN	STD_LOGIC;
			i_enable	: IN	STD_LOGIC;
			i_clock		: IN	STD_LOGIC;
			o_q, o_qBar	: OUT	STD_LOGIC
		);
	END COMPONENT;

    COMPONENT enASdFF_2
		PORT(
			i_resetBar	: IN	STD_LOGIC;
			i_d		    : IN	STD_LOGIC;
			i_enable	: IN	STD_LOGIC;
			i_clock		: IN	STD_LOGIC;
			o_q, o_qBar	: OUT	STD_LOGIC
		);
	END COMPONENT;

begin 
    -- all these values are based off of control path in theoretical design at beginning of lab document
    -- this controlpath will control the data path to accomplish the objectives we're trying to reach
    next_input(0) <= '0';
    next_input(1) <= current_state(0);
    next_input(2) <= current_state(1) and i_sign;
    next_input(3) <= current_state(2) and i_nlt9;
    next_input(4) <= (current_state(4) and (not i_zero)) or (current_state(2) and (not i_nlt9) and (not i_zero));
    next_input(5) <= current_state(1) and (not i_sign) and i_nlt9;
    next_input(6) <= (current_state(6) and (not i_zero)) or (current_state(1) and (not i_sign) and (not i_zero) and (not i_nlt9));
    next_input(7) <= current_state(3) or current_state(5) or (current_state(4) and i_zero) or (current_state(6) and i_zero) 
        or (current_state(1) and (not i_sign) and (not i_nlt9) and i_zero) or (current_state(2) and (not i_nlt9) and i_zero);
    next_input(8) <= current_state(7) and i_cout_Mz;
    next_input(9) <= current_state(9) or current_state(8) or (current_state(7) and (not i_cout_Mz));

    -- flip flop generation -- 
    loop_control : FOR i IN 1 TO 9 GENERATE
		bitn: enARdFF_2 PORT MAP (
			i_resetBar => i_asynch_reset, i_d => next_input(i), i_enable => '1', i_clock => i_clock,
			o_q => current_state(i)
		);
	END GENERATE;

    first_state : enASdFF_2 PORT MAP (
        i_resetBar => i_asynch_reset, i_d => next_input(0), i_enable => '1', i_clock => i_clock,
        o_q => current_state(0)
    );

    -- output driver --
    o_load1 <= current_state(0);
    o_load2 <= current_state(0);
    o_load3 <= current_state(0);
    o_load4 <= current_state(0);
    o_load5 <= current_state(7);
    o_load6 <= current_state(1) or current_state(2);
    o_load7 <= current_state(7);

    o_complement_1 <= current_state(2);
    o_complement_2 <= current_state(1);

    o_cin <= current_state(1) or current_state(2);

    o_flag0 <= current_state(1);
    o_flag1 <= current_state(2);
    o_OF_UF0 <= current_state(1);
    o_OF_UF1 <= current_state(3) or current_state(5);

    o_shiftR3 <= current_state(4);
    o_shiftR4 <= current_state(6);
    o_shiftR5 <= current_state(8);

    o_clear3 <= current_state(3);
    o_clear4 <= current_state(5);
    o_clear5 <= '0';

    o_countD6 <= current_state(4) or current_state(6);
    o_countU7 <= current_state(8);

    o_done <= current_state(9);
end rtl;