Library ieee;
Use ieee.std_logic_1164.ALL;

entity demux8to1 is
	port(
		i_regWrite: in std_logic;
		i_select: in std_logic_vector(2 downto 0);
		o_regWriteVector: out std_logic_vector(7 downto 0)
	);
end entity demux8to1;

architecture rtl of demux8to1 is 
begin
	o_regWriteVector(0) <= i_regWrite and not(i_select(0)) and not(i_select(1)) and not(i_select(2));
	o_regWriteVector(1) <= i_regWrite and i_select(0) and not(i_select(1)) and not(i_select(2));
	o_regWriteVector(2) <= i_regWrite and not(i_select(0)) and i_select(1) and not(i_select(2));
	o_regWriteVector(3) <= i_regWrite and i_select(0) and i_select(1) and not(i_select(2));
	o_regWriteVector(4) <= i_regWrite and not(i_select(0)) and not(i_select(1)) and i_select(2);
	o_regWriteVector(5) <= i_regWrite and i_select(0) and not(i_select(1)) and i_select(2);
	o_regWriteVector(6) <= i_regWrite and not(i_select(0)) and i_select(1) and i_select(2);
	o_regWriteVector(7) <= i_regWrite and i_select(0) and i_select(1) and i_select(2);
end architecture rtl;