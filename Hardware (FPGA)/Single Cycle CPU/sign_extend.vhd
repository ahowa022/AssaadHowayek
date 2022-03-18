Library ieee;
Use ieee.std_logic_1164.ALL;

entity sign_extend is
	port(
		i_value: in std_logic_vector(15 downto 0);
		o_value: out std_logic_vector(31 downto 0)
	);
end entity sign_extend;

architecture basic of sign_extend is
begin
	o_value(15 downto 0) <= i_value(15 downto 0);
	sign_extension: for i in 16 to 31 generate
		o_value(i) <= i_value(15);
	end generate;
end architecture basic;