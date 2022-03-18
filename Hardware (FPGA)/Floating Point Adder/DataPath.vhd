LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY DataPath IS
	PORT(
        i_clock, i_asynch_reset : IN STD_LOGIC; -- general clock and reset pour synchroniser tous le systeme 
        -- input du control path qui gere tous le datapath
        i_exponent_1, i_exponent_2 : IN STD_LOGIC_VECTOR(6 downto 0);
        i_mantissa_1, i_mantissa_2 : IN STD_LOGIC_VECTOR(7 downto 0);
        i_load1, i_load2, i_load3, i_load4, i_load5, i_load6, i_load7 : IN STD_LOGIC; 
        i_complement_1, i_complement_2 : IN STD_LOGIC;
        i_cin : IN STD_LOGIC;
        i_flag0, i_flag1, i_OF_UF0, i_OF_UF1 : IN STD_LOGIC;
        i_shiftR3, i_shiftR4, i_shiftR5 : IN STD_LOGIC;
        i_countD6, i_countU7 : IN STD_LOGIC;
        i_clear3, i_clear4, i_clear5 : IN STD_LOGIC;
        i_done : STD_LOGIC;
        -- output des signaux envoie au control path
        o_sign, o_nlt9, o_zero, o_cout_Mz : OUT STD_LOGIC;
        -- output du systeme
        o_OF_UF, o_flag : OUT STD_LOGIC;
        o_mantissa : OUT STD_LOGIC_VECTOR(7 downto 0);
        o_exponent : OUT STD_LOGIC_VECTOR(6 downto 0);
        o_done : OUT STD_LOGIC
	);
END DataPath; 

architecture rtl of DataPath is
    -- tous les signaux necessaire pour interconnecter tous les composantes
    signal int_exponent_1, int_exponent_2 : std_logic_vector(6 downto 0);
    signal int_complement_1, int_complement_2 : std_logic_vector(7 downto 0);
    signal int_exponent_sum : std_logic_vector(7 downto 0);
    signal int_difference_exp : std_logic_vector(6 downto 0);
    signal int_output_mux : std_logic_vector(6 downto 0);
    signal int_final_exp : std_logic_vector(6 downto 0);
    signal int_flag, int_flag_bar : std_logic;
    signal int_overflow, int_overflow_bar : std_logic;
    signal int_mantissa_1, int_mantissa_2: std_logic_vector(8 downto 0);
    signal int_mantissa_sum : std_logic_vector(8 downto 0);
    signal int_final_mantissa : std_logic_vector(8 downto 0);
    signal int_sign, int_zero, int_NLT9, int_coutFz, int_done: std_logic; 

    -- tous les components necessaire pour former notre systeme
    component decompteur_7bits is
		port(
			d, load, reset, clock: in std_logic; 
			in_value: in std_logic_vector(6 downto 0); --Input value
			z: out std_logic;  -- zero value
			out_value: out std_logic_vector(6 downto 0) --Output value
			);
	end component;

    component seven_bit_counter
    PORT (
        i_clock, asynch_reset: IN STD_LOGIC;
        i_load, i_increment: IN STD_LOGIC;
        i_value: IN STD_LOGIC_VECTOR(6 downto 0);
        o_value: OUT STD_LOGIC_VECTOR(6 downto 0)
    );
    END Component;

    component sevenBitComparator IS 
        PORT(
            i_Ai, i_Bi	: IN	STD_LOGIC_VECTOR(6 downto 0);
            o_NLT		: OUT	STD_LOGIC);
    END component;

    component nbit_complement IS 
        generic (n : positive := 8);
        PORT (
            a : IN STD_LOGIC_VECTOR(n-1 downto 0);
            i_complement: IN STD_LOGIC;
            o_value: OUT STD_LOGIC_VECTOR(n-1 downto 0)
        );
    END component;

    component nbitShiftRightReg IS 
        generic (n : positive := 9);
        PORT (
            i_clock, asynch_reset: IN STD_LOGIC;
            i_load, i_clear, i_shift : IN STD_LOGIC;
            i_serial_input : IN STD_LOGIC;
            i_value: IN STD_LOGIC_VECTOR(n-1 downto 0);
            o_value: OUT STD_LOGIC_VECTOR(n-1 downto 0)
        );
    END component;

    component nbit_reg IS 
        generic (n : positive := 7);
        PORT (
            i_clock, i_load, asynch_reset: IN STD_LOGIC;
            i_value: IN STD_LOGIC_VECTOR(n-1 downto 0);
            o_value: OUT STD_LOGIC_VECTOR(n-1 downto 0)
        );
    END component;

    component srLatch is
	port(
		i_set, i_reset  : IN	STD_LOGIC;
		o_q, o_qBar		: OUT	STD_LOGIC);
    end component;

    component Additionneur_NBits IS
	generic (n : positive := 3);
	port (
		a, b: IN STD_LOGIC_VECTOR(n DOWNTO 0);
		cin: IN STD_LOGIC; -- cin set to 0 when sum and set to 1 when substraction
		cout, zout, sign_msb: OUT STD_LOGIC;
		sum: OUT STD_LOGIC_VECTOR(n DOWNTO 0));
    END component;

    component sevenBitMux2to1 IS
	PORT(
        a, b: IN STD_LOGIC_VECTOR(6 downto 0); -- input
        sel: STD_LOGIC; -- ctrl
        c: OUT STD_LOGIC_VECTOR(6 downto 0)); --output
    END component;

    COMPONENT enARdFF_2
		PORT(
			i_resetBar	: IN	STD_LOGIC;
			i_d		    : IN	STD_LOGIC;
			i_enable	: IN	STD_LOGIC;
			i_clock		: IN	STD_LOGIC;
			o_q, o_qBar	: OUT	STD_LOGIC
		);
	END COMPONENT;

begin 
    exp1 : nbit_reg
        generic map(n => 7)
        port map(i_clock, i_load1, i_asynch_reset, i_exponent_1, int_exponent_1);
    
    exp2 : nbit_reg
        generic map(n => 7)
        port map(i_clock, i_load2, i_asynch_reset, i_exponent_2, int_exponent_2);
    
    complement1 : nbit_complement  
        generic map(n => 8)
        PORT MAP(a(7) => '0', a(6 downto 0) => int_exponent_1, i_complement => i_complement_1, o_value => int_complement_1);
    
    complement2 : nbit_complement  
        generic map(n => 8)
        PORT MAP(a(7) => '0', a(6 downto 0) => int_exponent_2, i_complement => i_complement_2, o_value => int_complement_2);
    
    ALU1 : Additionneur_NBits
        generic map(n => 7)
        PORT MAP(a => int_complement_1, b => int_complement_2, cin => i_cin, sign_msb => int_sign, sum => int_exponent_sum);
    
    decounter : decompteur_7bits
        PORT MAP(i_countD6, i_load6, i_asynch_reset, i_clock, int_exponent_sum(6 downto 0), int_zero, int_difference_exp);

    comparator : sevenBitComparator
        PORT MAP(int_difference_exp, "0001001", int_NLT9); -- "0001001" est 9 en decimal and generates our signal not less than nine
    
    flag_gen : srlatch
        PORT MAP(i_flag1, i_flag0, int_flag, int_flag_bar);
    
    mux_1 : sevenBitMux2to1
        PORT MAP(int_exponent_1, int_exponent_2, int_flag, int_output_mux);
    
    counter_1 : seven_bit_counter
        PORT MAP(i_clock, i_asynch_reset, i_load7, i_countU7, int_output_mux, int_final_exp);

    mantissa1 : nbitShiftRightReg
        generic map(n => 9)
        PORT MAP (i_clock => i_clock, asynch_reset => i_asynch_reset, i_load=> i_load3, i_clear => i_clear3, i_shift => i_shiftR3, i_serial_input => '0', 
            i_value(8) => '1', i_value(7 downto 0) => i_mantissa_1, o_value => int_mantissa_1);
    
    mantissa2 : nbitShiftRightReg
        generic map(n => 9)
        PORT MAP (i_clock => i_clock, asynch_reset => i_asynch_reset, i_load=> i_load4, i_clear => i_clear4, i_shift => i_shiftR4, i_serial_input => '0', 
            i_value(8) => '1', i_value(7 downto 0) => i_mantissa_2, o_value => int_mantissa_2);
    
    ALU2 : Additionneur_NBits
        generic map(n => 8)
        PORT MAP(a => int_mantissa_1, b => int_mantissa_2, cin => '0', cout => int_coutFz, sum => int_mantissa_sum);
    
    mantissa3 : nbitShiftRightReg
        generic map(n => 9)
        PORT MAP (i_clock => i_clock, asynch_reset => i_asynch_reset, i_load=> i_load5, i_clear => i_clear5, i_shift => i_shiftR5, i_serial_input => '0', 
            i_value => int_mantissa_sum, o_value => int_final_mantissa);
    
    overflow_gen : srlatch
        PORT MAP(i_OF_UF1, i_OF_UF0, int_overflow, int_overflow_bar);
    
    done: enardFF_2
        port map(
            i_resetBar => i_asynch_reset, i_d => i_done, i_enable => '1', i_clock => i_clock,
			o_q => int_done); 

    -- output du systeme
    o_sign <= int_sign;
    o_nlt9 <= int_NLT9;
    o_zero <= int_zero;
    o_cout_Mz <= int_coutFz;
    o_OF_UF <= int_overflow;
    o_flag <= int_flag;
    o_done <= int_done;
    o_mantissa <= int_final_mantissa(7 downto 0);
    o_exponent <= int_final_exp;

end rtl;