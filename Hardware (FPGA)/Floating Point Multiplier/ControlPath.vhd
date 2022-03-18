LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY ControlPath IS
	PORT(
        i_clock, i_asynch_reset : IN STD_LOGIC; -- general clock and reset pour synchroniser tous le systeme
        i_exception, i_normalized : IN STD_LOGIC; -- input des signaux du datapath 
        -- output du control path qui gere tous le datapath
        o_load_count, o_load_reg : OUT STD_LOGIC;
        o_OF_UF_0, o_OF_UF_1 : OUT STD_LOGIC;
        o_complement : OUT STD_LOGIC;
        o_shiftR, o_countUp : OUT STD_LOGIC;
        o_done : OUT STD_LOGIC
	);
END ControlPath; 

ARCHITECTURE rtl of ControlPath IS 
    signal next_input : STD_LOGIC_VECTOR(3 downto 0);
    signal current_state : STD_LOGIC_VECTOR(3 downto 0);

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
    next_input(1) <= current_state(0) and i_exception;
    next_input(2) <= current_state(0) and (not i_exception) and (not i_normalized);
    next_input(3) <= (current_state(1)) or (current_state(2)) or (current_state(3)) or (current_state(0) and (not i_exception) and i_normalized);

    -- flip flop generation -- 
    loop_control : FOR i IN 1 TO 3 GENERATE
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
    o_load_count <= current_state(0);
    o_load_reg <= current_state(0);

    o_OF_UF_0 <= current_state(0);
    o_OF_UF_1 <= current_state(1);

    o_complement <= current_state(0);

    o_shiftR <= current_state(2);
    o_countUp <= current_state(2);

    o_done <= current_state(3);
end rtl;