LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY DataPath IS
    PORT(
        i_clock, i_asynch_reset : IN STD_LOGIC; -- general clock and reset pour synchroniser tous le systeme
        -- inputs de lenvironnement
        i_signA, i_signB : IN STD_LOGIC;
        i_exponentA, i_exponentB : IN STD_LOGIC_VECTOR(6 downto 0);
        i_mantissaA, i_mantissaB : IN STD_LOGIC_VECTOR(7 downto 0);
        -- output du control path qui gere tous le datapath
        i_load_count, i_load_reg : IN STD_LOGIC;
        i_OF_UF_0, i_OF_UF_1 : IN STD_LOGIC;
        i_complement : IN STD_LOGIC;
        i_shiftR, i_countUp : IN STD_LOGIC;
        -- output des signaux du datapath vont vers controlpath
        o_exception, o_normalized : OUT STD_LOGIC;
        -- output qui va vers l'environnement
        o_final_sign : OUT STD_LOGIC;
        o_final_exp : OUT STD_LOGIC_VECTOR(6 downto 0);
        o_final_mantissa : OUT STD_LOGIC_VECTOR(7 downto 0);
        o_overflow_underflow : OUT STD_LOGIC
	);
end DataPath;

ARCHITECTURE rtl of DataPath is
    signal int_complement : STD_LOGIC_VECTOR(7 downto 0);
    signal int_differenceA, int_differenceB : STD_LOGIC_VECTOR(7 downto 0);
    signal int_sum_A_B, int_sum_with_excess : STD_LOGIC_VECTOR(7 downto 0);
    signal int_overflow_1, int_overflow_2 : STD_LOGIC;
    signal int_final_exponent : STD_LOGIC_VECTOR(6 downto 0);

    signal int_multiplier_output : STD_LOGIC_VECTOR(17 downto 0);
    signal int_final_mantissa : STD_LOGIC_VECTOR(17 downto 0);

    signal int_OF_UF, int_OF_UF_BAR : STD_LOGIC;

    component seven_bit_counter
    PORT (
        i_clock, asynch_reset: IN STD_LOGIC;
        i_load, i_increment: IN STD_LOGIC;
        i_value: IN STD_LOGIC_VECTOR(6 downto 0);
        o_value: OUT STD_LOGIC_VECTOR(6 downto 0)
    );
    END Component;

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

    component Additionneur_NBits IS
	generic (n : positive := 3);
	port (
		a, b: IN STD_LOGIC_VECTOR(n DOWNTO 0);
		cin: IN STD_LOGIC; -- cin set to 0 when sum and set to 1 when substraction
		cout, zout, sign_msb, overflow: OUT STD_LOGIC;
		sum: OUT STD_LOGIC_VECTOR(n DOWNTO 0));
    END component;

    component srLatch is
        port(
            i_set, i_reset  : IN	STD_LOGIC;
            o_q, o_qBar		: OUT	STD_LOGIC);
    end component;

    component fixedPoint_multiplier IS
	PORT(
        i_m, i_q : IN STD_LOGIC_VECTOR(8 downto 0);
        o_result: OUT STD_LOGIC_VECTOR(17 downto 0)
	);
    END component; 

begin
    -- it is important to note this whole datapath is just the coded version of the 
    -- drawing of our data path in the theoretical part of the report 

    -- exponent calculation components
    -- 00111111 est 63 en decimale ce qui represente lexces de la representation point mobile dans le lab
    complement_excess : nbit_complement  
        generic map(n => 8)
        PORT MAP(a => "00111111", i_complement => i_complement, o_value => int_complement);
    
    ALU_Diff_A : Additionneur_NBits
        generic map(n => 7)
        PORT MAP(a(7) =>'0', a(6 downto 0) => i_exponentA, b => int_complement, cin => '1', sum => int_differenceA);
    
    ALU_Diff_B : Additionneur_NBits
        generic map(n => 7)
        PORT MAP(a(7) =>'0', a(6 downto 0) => i_exponentB, b => int_complement, cin => '1', sum => int_differenceB);
    
    ALU_SUM_A_B : Additionneur_NBits
        generic map(n => 7)
        PORT MAP(a => int_differenceA, b => int_differenceB, cin => '0', overflow => int_overflow_1, sum => int_sum_A_B);
    
    ALU_SUM_EXCESS : Additionneur_NBits
        generic map(n => 7)
        PORT MAP(a => int_sum_A_B, b => "00111111", cin => '0', overflow => int_overflow_2, sum => int_sum_with_excess);

    counter_1 : seven_bit_counter
        PORT MAP(i_clock, i_asynch_reset, i_load_count, i_countUp, int_sum_with_excess(6 downto 0), int_final_exponent);

    overflow_gen : srlatch
        PORT MAP(i_OF_UF_1, i_OF_UF_0, int_OF_UF, int_OF_UF_BAR);

    -- mantissa calculation components
    fixedPoint_mult : fixedPoint_multiplier
        PORT MAP(i_m(8) => '1', i_m(7 downto 0) => i_mantissaA, i_q(8) => '1', i_q(7 downto 0) => i_mantissaB, o_result => int_multiplier_output);

    finalMantissa : nbitShiftRightReg
        generic map(n => 18)
        PORT MAP (i_clock => i_clock, asynch_reset => i_asynch_reset, i_load=> i_load_reg, i_clear => '0', i_shift => i_shiftR, i_serial_input => '0', 
            i_value => int_multiplier_output, o_value => int_final_mantissa);

    
    -- output driver 
    o_exception <= int_overflow_1 or int_overflow_2 or int_sum_with_excess(7);
    o_normalized <= (not int_multiplier_output(17));
    -- output qui va vers l'environnement
    o_final_sign <= i_signA xor i_signB;
    o_final_exp <= int_final_exponent;
    o_final_mantissa <= int_final_mantissa(15 downto 8);
    o_overflow_underflow <= int_OF_UF;

end rtl;