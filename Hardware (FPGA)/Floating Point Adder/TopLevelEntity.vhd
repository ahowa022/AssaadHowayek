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

architecture rtl of TopLevelEntity is
    -- intermediate signals used to communicate between datapath and control path
    -- from data path to control path
    signal int_sign, int_nlt9, int_zero, int_cout_Mz : STD_LOGIC; 
    -- from control path to data path
    signal int_load1, int_load2, int_load3, int_load4, int_load5, int_load6, int_load7, int_complement_1, int_complement_2, 
        int_cin, int_flag0, int_flag1, int_flag, int_OF_UF0, int_OF_UF1, int_shiftR3, int_shiftR4, int_shiftR5, int_countD6, int_countU7, int_clear3, int_clear4, int_clear5, int_done : STD_LOGIC;

    component DataPath 
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
    END component;

    Component ControlPath 
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
    END component; 

begin
    control: ControlPath 
        PORT MAP(
            i_clock => i_clock, 
            i_asynch_reset => i_asynch_reset, 
            i_sign => int_sign, 
            i_nlt9 => int_nlt9, 
            i_zero => int_zero, 
            i_cout_Mz => int_cout_Mz, 
            o_load1 => int_load1, 
            o_load2 => int_load2, 
            o_load3 => int_load3, 
            o_load4 => int_load4, 
            o_load5 => int_load5, 
            o_load6 => int_load6, 
            o_load7 => int_load7,
            o_complement_1 => int_complement_1, 
            o_complement_2 => int_complement_2,
            o_cin => int_cin,
            o_flag0 => int_flag0, 
            o_flag1 => int_flag1, 
            o_OF_UF0 => int_OF_UF0, 
            o_OF_UF1 => int_OF_UF1,
            o_shiftR3 => int_shiftR3, 
            o_shiftR4 => int_shiftR4, 
            o_shiftR5 => int_shiftR5,
            o_countD6 => int_countD6, 
            o_countU7 => int_countU7,
            o_clear3 => int_clear3, 
            o_clear4 => int_clear4, 
            o_clear5 => int_clear5, 
            o_done => int_done
        );

    data: DataPath
        PORT MAP(
            i_clock => i_clock, 
            i_asynch_reset => i_asynch_reset,
            i_exponent_1 => i_exponent_env1, 
            i_exponent_2 => i_exponent_env2,
            i_mantissa_1 => i_mantissa_env1, 
            i_mantissa_2 => i_mantissa_env2, 
            i_load1 => int_load1, 
            i_load2 => int_load2, 
            i_load3 => int_load3, 
            i_load4 => int_load4, 
            i_load5 => int_load5, 
            i_load6 => int_load6, 
            i_load7 => int_load7,
            i_complement_1 => int_complement_1, 
            i_complement_2 => int_complement_2,
            i_cin => int_cin,
            i_flag0 => int_flag0, 
            i_flag1 => int_flag1, 
            i_OF_UF0 => int_OF_UF0, 
            i_OF_UF1 => int_OF_UF1,
            i_shiftR3 => int_shiftR3, 
            i_shiftR4 => int_shiftR4,
            i_shiftR5 => int_shiftR5,
            i_countD6 => int_countD6, 
            i_countU7 => int_countU7,
            i_clear3 => int_clear3, 
            i_clear4 => int_clear4, 
            i_clear5 => int_clear5,
            i_done => int_done,
            o_sign => int_sign, 
            o_nlt9 => int_nlt9, 
            o_zero => int_zero, 
            o_cout_Mz => int_cout_Mz,
            o_OF_UF => o_overflow_underflow,
            o_flag => int_flag,
            o_mantissa => o_final_mantissa,
            o_exponent => o_final_exponent,
            o_done => o_done_computing
        );

    o_final_sign <= (i_sign_env2 and int_flag) or (i_sign_env1 and not(int_flag));
end rtl;