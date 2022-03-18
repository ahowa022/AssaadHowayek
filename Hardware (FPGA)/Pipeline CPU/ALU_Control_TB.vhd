Library ieee;
Use ieee.std_logic_1164.ALL;

entity ALU_Control_TB is
end entity ALU_Control_TB;

architecture tb of ALU_Control_TB is
    signal  i_ALU_Op0_tb, i_ALU_Op1_tb: std_logic;
    signal i_Func_Field_tb : std_logic_vector(5 downto 0);
	signal o_operation_tb: std_logic_vector(2 downto 0);
	constant period: time := 50 ns; -- set period of time for clock

	component ALU_Control IS
        PORT(
            i_ALU_Op0, i_ALU_Op1 : IN std_logic;
            i_Func_Field : IN std_logic_vector(5 downto 0);
            o_operation : OUT std_logic_vector(2 downto 0)
        );
    END component;

begin
	dut: ALU_Control
		port map (i_ALU_Op0_tb, i_ALU_Op1_tb, i_Func_Field_tb, o_operation_tb);

	stimulus: process is
	begin
        -- verifie tous les sorties de la table de verite 
	    i_Func_Field_tb <= "000000"; 
        i_ALU_Op1_tb <= '0';
        i_ALU_Op0_tb <= '0';
        wait for period; -- sortie doit etre 010

        i_ALU_Op1_tb <= '0';
        i_ALU_Op0_tb <= '1';
        wait for period; -- sortie doit etre 110

        i_ALU_Op1_tb <= '1';
        i_ALU_Op0_tb <= '0';
        wait for period; -- sortie doit etre 010

        i_Func_Field_tb <= "000010";
        wait for period; -- sortie doit etre 110

        i_Func_Field_tb <= "000100";
        wait for period; -- sortie doit etre 000

        i_Func_Field_tb <= "000101";
        wait for period; -- sortie doit etre 001

        i_Func_Field_tb <= "001010";
        wait for period; -- sortie doit etre 111
		wait;
	end process stimulus;
end tb;