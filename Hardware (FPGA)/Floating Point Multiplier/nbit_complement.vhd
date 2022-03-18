Library ieee;
Use ieee.std_logic_1164.ALL;

Entity nbit_complement IS
	generic (n : positive := 8);
	PORT (
        a : IN STD_LOGIC_VECTOR(n-1 downto 0);
		i_complement: IN STD_LOGIC;
		o_value: OUT STD_LOGIC_VECTOR(n-1 downto 0)
	);
END nbit_complement;

ARCHITECTURE rtl OF nbit_complement IS
    signal o_xor_gate_gate_output : STD_LOGIC_VECTOR(n-1 downto 0); 
    component xorGate
        PORT(
        a, b: IN STD_LOGIC; -- inputs
        c : OUT STD_LOGIC); --output
	end component; 

begin   
    loop_n : FOR i IN 0 TO n-1 GENERATE
        xor_output : xorGate PORT MAP(
                a => a(i), b => i_complement, c => o_xor_gate_gate_output(i)
            );
    END GENERATE;

    o_value <= o_xor_gate_gate_output;
END rtl;
