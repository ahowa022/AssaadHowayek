Library ieee;
Use ieee.std_logic_1164.ALL;

entity Additionneur_Nbit_tb is
end Additionneur_Nbit_tb;

architecture tb of Additionneur_Nbit_tb is
	signal a, b, sum: STD_LOGIC_VECTOR(3 downto 0);
	signal cin, cout, z_out, sign_tb: STD_LOGIC;

begin
	
	dut: entity work.Additionneur_Nbits(basic)
		port map (a, b, cin, cout, z_out, sign_tb, sum);
	stimulus: process is
	begin
		a <= "0000"; b <= "0000"; cin <= '0'; wait for 10 ns;
		cin <= '1'; wait for 10 ns;
		a <= "1111"; b <= "0000"; cin <= '0'; wait for 10 ns;
		cin <= '1'; wait for 10 ns;
		a <= "1111"; b <= "1111"; cin <= '0'; wait for 10 ns;
		cin <= '1'; wait for 10 ns;
		a <= "0100"; b <= "0001"; cin <= '0'; wait for 10 ns;
		cin <= '1'; wait for 10 ns;
		wait;
	end process stimulus;
end architecture;