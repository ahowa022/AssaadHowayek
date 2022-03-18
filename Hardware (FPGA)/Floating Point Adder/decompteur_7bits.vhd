LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

entity decompteur_7bits IS
	port(
		d, load, reset, clock: in std_logic; 
		in_value: in std_logic_vector(6 downto 0); --Input value
		z: out std_logic;  -- zero value
		out_value: out std_logic_vector(6 downto 0) --Output value
		);
end decompteur_7bits;

architecture basic of decompteur_7bits is

	signal partial_zero: std_logic; --Intermitiate zero. Value will be given to final zero (z)
	signal next_value, partial_d, current_value :std_logic_vector(6 downto 0); 
	signal enable : std_logic;

	--map out Flip flop
	component enARdFF_2 is
		port (
			i_resetBar: in std_logic;
			i_d: in std_logic;
			i_enable: in std_logic;
			i_clock: in std_logic;
			o_q, o_qBar: out std_logic
		);
	end component enARdFF_2;

	--map out Multiplexor
	component mux2to1 is
		port (
		a, b: IN STD_LOGIC; -- inputs
		sel: STD_LOGIC; --bits de selection
		c: OUT STD_LOGIC); --output
	end component mux2to1;


--Least signficant to most significant
begin
	-- equation next_value represente si le next state si on decremente par 1
	-- ces equations du decompteurs ont ete trouve avec un outil dont on a set les next states pour decrementer par 1
	next_value(0) <=(not(current_value(0)) and d) or (current_value(0) and not(d));
	next_value(1) <=(current_value(1) and not(d)) or (current_value(1) and current_value(0)) or (not(current_value(1)) and not(current_value(0)) and d) ;
	next_value(2) <=(current_value(2) and not(d)) or (current_value(2) and current_value(1)) or (current_value(2) and current_value(0)) or (not(current_value(2)) and not(current_value(1)) and not(current_value(0)) and d);
	next_value(3) <=(current_value(3) and not(d)) or (current_value(3) and current_value(2)) or (current_value(3) and current_value(1)) or (current_value(3) and current_value(0)) or (not(current_value(3)) and not(current_value(2)) and not(current_value(1)) and not(current_value(0)) and d);
	next_value(4) <=(current_value(4) and not(d)) or (current_value(4) and current_value(3)) or (current_value(4) and current_value(2)) or (current_value(4) and current_value(1)) or (current_value(4) and current_value(0)) or (not(current_value(4)) and not(current_value(3)) and not(current_value(2)) and not(current_value(1)) and not(current_value(0)) and d);
	next_value(5) <=(current_value(5) and not(d)) or (current_value(5) and current_value(4)) or (current_value(5) and current_value(3)) or (current_value(5) and current_value(2)) or (current_value(5) and current_value(1)) or (current_value(5) and current_value(0)) or (not(current_value(5)) and not(current_value(4)) and not(current_value(3)) and not(current_value(2)) and not(current_value(1)) and not(current_value(0)) and d);
	next_value(6) <=(current_value(6) and not(d)) or (current_value(6) and current_value(5)) or (current_value(6) and current_value(4)) or (current_value(6) and current_value(3)) or (current_value(6) and current_value(2)) or (current_value(6) and current_value(1)) or (current_value(6) and current_value(0)) or (not(current_value(6)) and not(current_value(5)) and not(current_value(4)) and not(current_value(3)) and not(current_value(2)) and not(current_value(1)) and not(current_value(0)) and d);

	-- enable les bascules si on soit load ou decremente (input du systeme)
	enable <= load or d;

	-- loop qui genere les bascules du decompteurs
	loop1: for i in 0 to 6 generate
		-- prochain input du flip-flop est si on soit load ou on decremente le compteur
		-- il est important de noter que seulement une de ces valeurs peut etre actifs a la fois
		partial_d(i) <= (load and in_value(i)) or (d and next_value(i));
		bit_0: enARdFF_2
			port map (
				i_resetBar => reset, i_d => partial_d(i), i_enable => enable, i_clock => clock,
			    o_q => current_value(i)
			);
	end generate;

    -- Final Output values
	partial_zero <= not(partial_d(0)) and not(partial_d(1)) and not(partial_d(2)) and not(partial_d(3)) and
		not(partial_d(4)) and not(partial_d(5)) and not(partial_d(6));
	z <= partial_zero; 

	out_value <= partial_d;

end basic;