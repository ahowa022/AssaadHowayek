LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

entity TopLevelEntity_TB is 
end TopLevelEntity_TB;

architecture testbench of TopLevelEntity_TB is
    signal clk_tb, asynch_reset_tb : std_logic; -- signal control test bench
    signal sim_end : boolean := false; --signals the end of the simulation
    constant period: time := 50 ns; -- set period of time for clock

    signal signA_tb, signB_tb : std_logic;
    signal exponentA_tb, exponentB_tb : std_logic_vector(6 downto 0);
    signal mantissaA_tb, mantissaB_tb : std_logic_vector(7 downto 0);
    signal finalSign_tb, done_tb, overflow_tb : std_logic;
    signal resultExp_tb : std_logic_vector(6 downto 0);
    signal resultMantissa_tb : std_logic_vector(7 downto 0);

    component TopLevelEntity
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
    End component;

begin
    DUT: TopLevelEntity -- connect test bench signals to signals of eight bit register
        port map (clk_tb, asynch_reset_tb, signA_tb, signB_tb, exponentA_tb, exponentB_tb, mantissaA_tb, mantissaB_tb, resultExp_tb, resultMantissa_tb, finalSign_tb, done_tb, overflow_tb);
    
    clock_process : process
    begin 
        while(not sim_end) loop
            clk_tb <= '1';
            wait for period/2;
            clk_tb <= '0';
            wait for period/2;
        end loop;
        wait;
    end process;

    testbench_process : process
    begin
        asynch_reset_tb <= '0';
        signA_tb <= '0';
        signB_tb <= '0';
        exponentA_tb <= "0001001";
        exponentB_tb <= "0001000";
        mantissaA_tb <= "00001100";
        mantissaB_tb <= "00100000";
        wait for period/2;

        asynch_reset_tb <= '1';
        wait for period*15;

        sim_end <= true;
        wait;
    end process;
end testbench;