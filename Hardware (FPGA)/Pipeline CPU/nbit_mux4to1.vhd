Library ieee;
Use ieee.std_logic_1164.ALL;

ENTITY nbit_mux4to1 IS
	generic (n : positive := 8);
	PORT(
        a, b, c, d: IN STD_LOGIC_VECTOR(n-1 downto 0); -- inputs
        sel: IN STD_LOGIC_Vector(1 downto 0); --bits de selection
        o_mux: OUT STD_LOGIC_VECTOR(n-1 downto 0)
        ); --output
END nbit_mux4to1;

Architecture rtl of nbit_mux4to1 is 
    component mux4to1 is 
        PORT (
            i_a, i_b, i_c, i_d: IN STD_LOGIC; -- inputs
            i_sel: STD_LOGIC_VECTOR(1 downto 0); --bits de selection
            o_mux: OUT STD_LOGIC --output
        );	 
    end component;
begin 
    loop_n: FOR i in 0 to n-1 GENERATE -- loop to generate nbit 4 to 1 mux
        mux_tmp: mux4to1
            PORT MAP (
                i_a => a(i), i_b => b(i), i_c => c(i), i_d => d(i), i_sel => sel,
                o_mux => o_mux(i)
            );
    END GENERATE;
end rtl;