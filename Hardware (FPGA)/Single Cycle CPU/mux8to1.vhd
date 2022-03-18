Library ieee;
Use ieee.std_logic_1164.ALL;

entity mux8to1 is
	port(
		i_values: in std_logic_vector(7 downto 0);
		i_sel: in std_logic_vector(2 downto 0);
		o_mux: out std_logic
	);
end mux8to1;

architecture rtl of mux8to1 is 
begin
	o_mux <= 
		(i_values(0) and not(i_sel(0)) and not(i_sel(1)) and not(i_sel(2))) or
		(i_values(1) and i_sel(0) and not(i_sel(1)) and not(i_sel(2))) or
		(i_values(2) and not(i_sel(0)) and i_sel(1) and not(i_sel(2))) or
		(i_values(3) and i_sel(0) and i_sel(1) and not(i_sel(2))) or
		(i_values(4) and not(i_sel(0)) and not(i_sel(1)) and i_sel(2)) or
		(i_values(5) and i_sel(0) and not(i_sel(1)) and i_sel(2)) or
		(i_values(6) and not(i_sel(0)) and i_sel(1) and i_sel(2)) or
		(i_values(7) and i_sel(0) and i_sel(1) and i_sel(2));
end architecture rtl;