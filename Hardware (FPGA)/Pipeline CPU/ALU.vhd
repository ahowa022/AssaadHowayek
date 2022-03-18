LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY ALU IS
	generic (n : positive := 8);
	PORT(
        i_ALU_OP : in std_logic_vector(2 downto 0);
		i_a, i_b : in std_logic_vector(n-1 downto 0);
		o_ALU : out std_logic_vector(n-1 downto 0);
		o_overflow, o_zero : out std_logic
	);
END ALU; 

ARCHITECTURE rtl of ALU IS	
	-- signaux intermediaires pour conception de l'ALU
	signal int_mux_out : std_logic_vector(n-1 downto 0); -- output de b invert
	signal int_and : std_logic_vector(n-1 downto 0); -- output a and b
	signal int_or : std_logic_vector(n-1 downto 0); -- output a or b
	signal int_not_b: std_logic_vector(n-1 downto 0); -- output not b

	signal int_sum_adders : std_logic_vector(n-1 downto 0); -- output du full adder de chaque ALU
	signal int_carry_adders : std_logic_vector(n downto 0); -- carry pour chaque adder

	signal int_overflow_adders : std_logic; -- overflow dans les adder
	signal int_add_operation : std_logic; -- signal indique que alu op represente addition
	signal int_sub_operation : std_logic; -- signal indique que alu op represente soustraction
	
	signal int_zero_gen : std_logic_vector(n downto 0);

	component mux2to1 IS
		PORT(
			a, b: IN STD_LOGIC;
			sel: STD_LOGIC;
			c: OUT STD_LOGIC
		);
	END component;

	component mux4to1 IS
		PORT (
			i_a, i_b, i_c, i_d: IN STD_LOGIC; 
			i_sel: STD_LOGIC_VECTOR(1 downto 0); 
			o_mux: OUT STD_LOGIC
		);	
	END component;	

	component additionneur_1Bit IS
		PORT(
			a, b, cin : IN STD_LOGIC;
			sum, cout : OUT STD_LOGIC
		);
	END component; 

BEGIN
	int_carry_adders(0) <= i_ALU_OP(2);
	-- generate n-1 one bit ALUs
	one_bit_alu_loop : for i in 0 to n-1 generate
		-- output de binvert
		muxout : mux2to1 
			PORT MAP (a => i_b(i), b => int_not_b(i), sel => i_ALU_OP(2),  c => int_mux_out(i));
		-- one bit full adder
		adder : additionneur_1Bit
			PORT MAP(a => i_a(i), b => int_mux_out(i), cin => int_carry_adders(i), sum => int_sum_adders(i), 
				cout => int_carry_adders(i+1));
		-- resultat de ALU/sortie du mux
		result_alu : mux4to1
			PORT MAP(i_a => (int_and(i)), i_b => (int_or(i)), i_c => int_sum_adders(i),
				i_d => '0', i_sel => i_ALU_OP(1 downto 0), o_mux => o_ALU(i));
		
		-- signaux intermediairs pour le and or et not de b
		int_and(i) <= (i_a(i) and i_b(i));
		int_or(i) <= (i_a(i) or i_b(i));
		int_not_b(i) <= (not i_b(i));
		
	end generate;

	-- logique pour output driver --

	-- logique pour obtenir output overflow
	int_overflow_adders <= int_carry_adders(n) xor int_carry_adders(n-1); -- overflow dans adder
	int_add_operation <= (not i_ALU_OP(2)) and (i_ALU_OP(1)) and (not i_ALU_OP(0)); -- 010 indique addition
	int_sub_operation <= (i_ALU_OP(2)) and (i_ALU_OP(1)) and (not i_ALU_OP(0)); -- 110 indique soustraction

	-- logique pour generer signal zero
	int_zero_gen(0) <= '0';
	zero_loop : FOR i in 0 to n-1 generate
		int_zero_gen(i+1) <= int_zero_gen(i) or int_sum_adders(i);
	end generate;

	-- output driver --
	o_zero <= (not int_zero_gen(n));
	o_overflow <= (int_overflow_adders) and (int_add_operation or int_sub_operation);

END rtl;