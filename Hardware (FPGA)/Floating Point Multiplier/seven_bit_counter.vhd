LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

entity seven_bit_counter IS
    PORT (
        i_clock, asynch_reset: IN STD_LOGIC;
        i_load, i_increment: IN STD_LOGIC;
        i_value: IN STD_LOGIC_VECTOR(6 downto 0);
        o_value: OUT STD_LOGIC_VECTOR(6 downto 0)
    );
END seven_bit_counter;

architecture rtl of seven_bit_counter is
    signal next_input_inc, next_input, int_value : std_logic_vector(6 downto 0);
    signal enable : std_logic;

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
	-- enable les bascules slmt quand on veut increment ou bien load
    enable <= i_increment or i_load;
    -- use concept of carry look ahead adder to determine next input if there is an increment
    next_input_inc(0) <= int_value(0) xor i_increment;
	next_input_inc(1) <= int_value(1) xor (int_value(0) and i_increment);
	next_input_inc(2) <= int_value(2) xor (int_value(1) and int_value(0) and i_increment);
	next_input_inc(3) <= int_value(3) xor (int_value(2) and int_value(1) and int_value(0) and i_increment);
	next_input_inc(4) <= int_value(4) xor (int_value(3) and int_value(2) and int_value(1) and int_value(0) and i_increment);
	next_input_inc(5) <= int_value(5) xor (int_value(4) and int_value(3) and int_value(2) and int_value(1) and int_value(0) and i_increment);
	next_input_inc(6) <= int_value(6) xor (int_value(5) and int_value(4) and int_value(3) and int_value(2) and int_value(1) and int_value(0) and i_increment);

	loop1: for i in 0 to 6 generate
        next_input(i) <= (next_input_inc(i) and i_increment) or (i_value (i) and i_load);
		bit_n: enARdFF_2
			port map (
				i_resetBar => asynch_reset, i_d => next_input(i), i_enable => enable, i_clock => i_clock, 
				o_q => int_value(i)
			);
	end generate;
	
	o_value <= int_value;
end rtl;