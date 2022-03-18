LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY fixedPoint_multiplier IS
	PORT(
        i_m, i_q : IN STD_LOGIC_VECTOR(8 downto 0);
        o_result: OUT STD_LOGIC_VECTOR(17 downto 0)
	);
END fixedPoint_multiplier; 

ARCHITECTURE rtl of fixedPoint_multiplier is 
    -- final result variable
    signal int_result : std_logic_vector(17 downto 0);
    --row 1 variables
    signal int_m1_r1, int_m0_r1, int_result_r1 : std_logic_vector(8 downto 0);
    signal int_cin_r1 : std_logic_vector(9 downto 0);
    -- row 2 variables
    signal int_mk_r2, int_ppi_r2, int_result_r2 : std_logic_vector(8 downto 0);
    signal int_cin_r2 : std_logic_vector(9 downto 0);
    -- row 3 variables
    signal int_mk_r3, int_ppi_r3, int_result_r3 : std_logic_vector(8 downto 0);
    signal  int_cin_r3 : std_logic_vector(9 downto 0);
    -- row 4 variables
    signal  int_mk_r4, int_ppi_r4, int_result_r4 :  std_logic_vector(8 downto 0);
    signal int_cin_r4 : std_logic_vector(9 downto 0);
    -- row 5 variables
    signal int_mk_r5, int_ppi_r5, int_result_r5 : std_logic_vector(8 downto 0);
    signal int_cin_r5 : std_logic_vector(9 downto 0);
    -- row 6 variables
    signal int_mk_r6, int_ppi_r6, int_result_r6 : std_logic_vector(8 downto 0);
    signal int_cin_r6 : std_logic_vector(9 downto 0);
    -- row 7 variables
    signal int_mk_r7, int_ppi_r7, int_result_r7 : std_logic_vector(8 downto 0);
    signal int_cin_r7 : std_logic_vector(9 downto 0);
    -- row 8 variables
    signal int_mk_r8, int_ppi_r8, int_result_r8 : std_logic_vector(8 downto 0);
    signal int_cin_r8 : std_logic_vector(9 downto 0);

    COMPONENT topRow_block IS
	PORT(
        q0, q1: IN STD_LOGIC;
        m0, m1: IN STD_LOGIC;
        cin : IN STD_LOGIC;
        sum, cout : OUT STD_LOGIC
	);
    END COMPONENT;

    COMPONENT bottomRows_Block IS
	PORT(
        mk, qj : IN STD_LOGIC;
        ppi: IN STD_LOGIC;
        cin : IN STD_LOGIC;
        sum, cout : OUT STD_LOGIC
	);
    END COMPONENT; 
begin
    -- s'il vous plait se referer au schema utiliser dans le rapport de laboratoire
    -- pour mieux comprendre l'implementation du multiplicateur a point fixe

    -- completion of the first row
    int_cin_r1(0) <= '0';
    int_m1_r1(8) <= '0';
    int_m1_r1(7 downto 0) <= i_m(8 downto 1);
    int_m0_r1 <= i_m;
    loop_1: for i in 0 to 8 generate
        row1_block: topRow_block
            port map(i_q(0), i_q(1), int_m0_r1(i), int_m1_r1(i), int_cin_r1(i), int_result_r1(i), int_cin_r1(i+1));
    end generate;


    -- completion of second row
    int_cin_r2(0) <= '0';
    int_ppi_r2(8) <= int_cin_r1(9);
    int_ppi_r2(7 downto 0) <= int_result_r1(8 downto 1);
    int_mk_r2 <= i_m;
    loop_2: for i in 0 to 8 generate
        row2_block: bottomRows_Block
            port map(int_mk_r2(i), i_q(2), int_ppi_r2(i), int_cin_r2(i), int_result_r2(i), int_cin_r2(i+1));
    end generate;

    -- completion of third row
    int_cin_r3(0) <= '0';
    int_ppi_r3(8) <= int_cin_r2(9);
    int_ppi_r3(7 downto 0) <= int_result_r2(8 downto 1);
    int_mk_r3 <= i_m;
    loop_3: for i in 0 to 8 generate
        row3_block: bottomRows_Block
            port map(int_mk_r3(i), i_q(3), int_ppi_r3(i), int_cin_r3(i), int_result_r3(i), int_cin_r3(i+1));
    end generate;

    -- completion of fourth row
    int_cin_r4(0) <= '0';
    int_ppi_r4(8) <= int_cin_r3(9);
    int_ppi_r4(7 downto 0) <= int_result_r3(8 downto 1);
    int_mk_r4 <= i_m;
    loop_4: for i in 0 to 8 generate
        row4_block: bottomRows_Block
            port map(int_mk_r4(i), i_q(4), int_ppi_r4(i), int_cin_r4(i), int_result_r4(i), int_cin_r4(i+1));
    end generate;

    -- completion of fifth row
    int_cin_r5(0) <= '0';
    int_ppi_r5(8) <= int_cin_r4(9);
    int_ppi_r5(7 downto 0) <= int_result_r4(8 downto 1);
    int_mk_r5 <= i_m;
    loop_5: for i in 0 to 8 generate
        row5_block: bottomRows_Block
            port map(int_mk_r5(i), i_q(5), int_ppi_r5(i), int_cin_r5(i), int_result_r5(i), int_cin_r5(i+1));
    end generate;

    -- completion of sixth row
    int_cin_r6(0) <= '0';
    int_ppi_r6(8) <= int_cin_r5(9);
    int_ppi_r6(7 downto 0) <= int_result_r5(8 downto 1);
    int_mk_r6 <= i_m;
    loop_6: for i in 0 to 8 generate
        row6_block: bottomRows_Block
            port map(int_mk_r6(i), i_q(6), int_ppi_r6(i), int_cin_r6(i), int_result_r6(i), int_cin_r6(i+1));
    end generate;

    -- completion of seventh row
    int_cin_r7(0) <= '0';
    int_ppi_r7(8) <= int_cin_r6(9);
    int_ppi_r7(7 downto 0) <= int_result_r6(8 downto 1);
    int_mk_r7 <= i_m;
    loop_7: for i in 0 to 8 generate
        row7_block: bottomRows_Block
            port map(int_mk_r7(i), i_q(7), int_ppi_r7(i), int_cin_r7(i), int_result_r7(i), int_cin_r7(i+1));
    end generate;

    -- completion of eigth row
    int_cin_r8(0) <= '0';
    int_ppi_r8(8) <= int_cin_r7(9);
    int_ppi_r8(7 downto 0) <= int_result_r7(8 downto 1);
    int_mk_r8 <= i_m;
    loop_8: for i in 0 to 8 generate
        row8_block: bottomRows_Block
            port map(int_mk_r8(i), i_q(8), int_ppi_r8(i), int_cin_r8(i), int_result_r8(i), int_cin_r8(i+1));
    end generate;

    -- output driver du resultat
    int_result(17) <= int_cin_r8(9);
    int_result(16 downto 8) <= int_result_r8(8 downto 0);
    int_result(7) <= int_result_r7(0);
    int_result(6) <= int_result_r6(0);
    int_result(5) <= int_result_r5(0);
    int_result(4) <= int_result_r4(0);
    int_result(3) <= int_result_r3(0);
    int_result(2) <= int_result_r2(0);
    int_result(1) <= int_result_r1(0);
    int_result(0) <= i_q(0) and i_m(0);

    o_result <= int_result;
end rtl;