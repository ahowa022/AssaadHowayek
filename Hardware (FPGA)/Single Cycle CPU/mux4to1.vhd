Library ieee;
Use ieee.std_logic_1164.ALL;

Entity mux4to1 IS
	PORT (
        i_a, i_b, i_c, i_d: IN STD_LOGIC; -- inputs
        i_sel: STD_LOGIC_VECTOR(1 downto 0); --bits de selection
        o_mux: OUT STD_LOGIC --output
	);	
END mux4to1;

ARCHITECTURE rtl OF mux4to1 IS

BEGIN
    o_mux <= (not(i_sel(1)) and not(i_sel(0)) and i_a) or (i_sel(0) and not(i_sel(1)) and i_b) or (not(i_sel(0)) and i_sel(1) and i_c) or (i_sel(1) and i_sel(0) and i_d);
END rtl;