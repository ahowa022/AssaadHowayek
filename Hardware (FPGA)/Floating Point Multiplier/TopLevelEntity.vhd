LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY TopLevelEntity IS
    PORT(
        i_clock, i_asynch_reset : IN STD_LOGIC; -- general clock and reset pour synchroniser tous le systeme 
        -- input de l'environnement
        i_sign_env1, i_sign_env2 : IN STD_LOGIC;
        i_exponent_env1, i_exponent_env2 : IN STD_LOGIC_VECTOR(6 downto 0);
        i_mantissa_env1, i_mantissa_env2 : IN STD_LOGIC_VECTOR(7 downto 0);
        o_final_exponent : OUT STD_LOGIC_VECTOR(6 downto 0);
        o_final_mantissa : OUT STD_LOGIC_VECTOR(7 downto 0);
        o_final_sign : OUT STD_LOGIC;
        o_done_computing : OUT STD_LOGIC;
        o_overflow_underflow : OUT STD_LOGIC
    );
End TopLevelEntity;

ARCHITECTURE rtl of TopLevelEntity Is 
    signal int_load_count, int_load_reg, int_OF_UF_0, int_OF_UF_1, int_complement, int_shiftR, int_countUP, int_exception, int_normalized : STD_LOGIC;

    Component DataPath IS
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
    end component;

    component ControlPath IS
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
    END component; 

begin 
    data_path : DataPath
        Port Map(
            i_clock => i_clock, 
            i_asynch_reset => i_asynch_reset,
            i_signA => i_sign_env1, 
            i_signB => i_sign_env2,  
            i_exponentA => i_exponent_env1, 
            i_exponentB => i_exponent_env2, 
            i_mantissaA => i_mantissa_env1, 
            i_mantissaB => i_mantissa_env2,
            i_load_count => int_load_count, 
            i_load_reg => int_load_reg,  
            i_OF_UF_0 => int_OF_UF_0, 
            i_OF_UF_1 => int_OF_UF_1,
            i_complement => int_complement,
            i_shiftR => int_shiftR,
            i_countUp => int_countUp,
            o_exception => int_exception, 
            o_normalized => int_normalized,
            o_final_sign => o_final_sign, 
            o_final_exp => o_final_exponent,
            o_final_mantissa => o_final_mantissa,
            o_overflow_underflow => o_overflow_underflow
        );
    
    control_path : ControlPath 
        PORT MAP(
            i_clock => i_clock, 
            i_asynch_reset => i_asynch_reset,
            i_exception => int_exception, 
            i_normalized => int_normalized,
            o_load_count => int_load_count, 
            o_load_reg => int_load_reg, 
            o_OF_UF_0 => int_OF_UF_0, 
            o_OF_UF_1 => int_OF_UF_1, 
            o_complement => int_complement, 
            o_shiftR => int_shiftR, 
            o_countUp => int_countUp, 
            o_done => o_done_computing
	    );
end rtl;