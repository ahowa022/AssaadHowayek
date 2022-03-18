Library ieee;
Use ieee.std_logic_1164.ALL;

entity nbit_mux8to1 is
    generic (n : positive := 8);
	port(
		reg0, reg1, reg2, reg3, reg4, reg5, reg6, reg7: in std_logic_vector(n-1 downto 0);
		i_sel: in std_logic_vector(2 downto 0);
		o_mux: out std_logic_vector(n-1 downto 0)
	);
end nbit_mux8to1;

architecture rtl of nbit_mux8to1 is 
    component mux8to1 is
        port(
            i_values: in std_logic_vector(7 downto 0);
            i_sel: in std_logic_vector(2 downto 0);
            o_mux: out std_logic
        );
    end component;
begin
    loop_n : FOR i IN 0 TO n-1 GENERATE --Loop to generate nbit 8 to 1 mux
		mux_tmp: mux8to1
			PORT MAP (
				i_values(0) => reg0(i), i_values(1) => reg1(i), i_values(2) => reg2(i),
                i_values(3) => reg3(i), i_values(4) => reg4(i), i_values(5) => reg5(i),
                i_values(6) => reg6(i), i_values(7) => reg7(i), i_sel => i_sel, o_mux => o_mux(i)
			);
	END GENERATE;
end architecture rtl;