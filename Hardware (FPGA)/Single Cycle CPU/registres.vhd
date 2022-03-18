LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY registres IS
    generic (size : positive := 8);
	PORT(
        i_clock, i_asynch_reset : IN std_logic;
        i_regWrite : IN std_logic;
		i_readReg1, i_readReg2, i_writeReg: IN std_logic_vector(2 downto 0);
        i_writeData : IN std_logic_vector(size-1 downto 0);
        o_read_data1, o_read_data2 : OUT std_logic_vector(size-1 downto 0)
	);
END registres; 

ARCHITECTURE rtl of registres IS
    type int_registers is array(7 downto 0) of std_logic_vector(7 downto 0);
    signal int_regWriteVector : std_logic_vector(7 downto 0);
    signal int_regOutput : int_registers;
    
    
    component nbit_reg IS
        generic (n : positive := 7);
        PORT (
            i_clock, i_load, asynch_reset: IN STD_LOGIC;
            i_value: IN STD_LOGIC_VECTOR(n-1 downto 0);
            o_value: OUT STD_LOGIC_VECTOR(n-1 downto 0)
        );
    END component;

    component demux8to1 IS
        port(
            i_regWrite: in std_logic;
            i_select: in std_logic_vector(2 downto 0);
            o_regWriteVector: out std_logic_vector(7 downto 0)
        );
    end component; 

    component nbit_mux8to1 is
        generic (n : positive := 8);
        port(
            reg0, reg1, reg2, reg3, reg4, reg5, reg6, reg7: in std_logic_vector(n-1 downto 0);
            i_sel: in std_logic_vector(2 downto 0);
            o_mux: out std_logic_vector(n-1 downto 0)
        );
    end component;

BEGIN
    -- genere signal reg write pour chaque registres
    demux : demux8to1
        port map(i_regWrite => i_regWrite, i_select => i_writeReg, o_regWriteVector => int_regWriteVector);
    
    -- loop qui genere tous les registres dans le fichier de registre
    loop_reg : for i in 0 to 7 generate
        regn : nbit_reg
            generic map (n => size)
            PORT Map(
                i_clock => i_clock, i_load => int_regWriteVector(i), asynch_reset => i_asynch_reset, i_value => i_writeData,
                    o_value => int_regOutput(i));
    end generate;

    -- genere sortie de read data 1 and 2 de[emdamt des select input
    mux1: nbit_mux8to1
        generic map(n => size)
        Port map (reg0 => int_regOutput(0), reg1 => int_regOutput(1), reg2=> int_regOutput(2),
            reg3=> int_regOutput(3), reg4 => int_regOutput(4), reg5 => int_regOutput(5), 
            reg6 => int_regOutput(6), reg7 => int_regOutput(7), i_sel => i_readReg1,
            o_mux => o_read_data1);
    
    mux2: nbit_mux8to1
        generic map(n => size)
        Port map (reg0 => int_regOutput(0), reg1 => int_regOutput(1), reg2=> int_regOutput(2),
            reg3=> int_regOutput(3), reg4 => int_regOutput(4), reg5 => int_regOutput(5), 
            reg6 => int_regOutput(6), reg7 => int_regOutput(7), i_sel => i_readReg2,
            o_mux => o_read_data2);
END rtl;