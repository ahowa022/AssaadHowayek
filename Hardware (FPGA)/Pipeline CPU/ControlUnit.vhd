LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY ControlUnit IS
	PORT(
        i_op_code : IN std_logic_vector(5 downto 0);
        o_RegDst, o_ALU_SRC, o_MemToReg, o_RegWrite, o_MemRead, o_MemWrite, 
            o_Branch, o_BNE, o_jump, o_ALU_OP1, o_ALU_OP0 : OUT std_logic
	);
END ControlUnit; 

ARCHITECTURE rtl of ControlUnit IS 
    -- signaux intermediaires pour control unit 
    signal RType_cmd, LW_cmd, SW_cmd, BEQ_cmd, BNE_cmd, JMP_cmd : std_logic;
begin 
    RType_cmd <= (not i_op_code(5)) and (not i_op_code(4)) and (not i_op_code(3)) and (not i_op_code(2)) and (not i_op_code(1)) and (not i_op_code(0)); -- 000000
    LW_cmd <= (i_op_code(5)) and (not i_op_code(4)) and (not i_op_code(3)) and (not i_op_code(2)) and (i_op_code(1)) and (i_op_code(0)); -- 100011
    SW_cmd <= (i_op_code(5)) and (not i_op_code(4)) and (i_op_code(3)) and (not i_op_code(2)) and (i_op_code(1)) and (i_op_code(0)); -- 101011
    BEQ_cmd <= (not i_op_code(5)) and (not i_op_code(4)) and (not i_op_code(3)) and (i_op_code(2)) and (not i_op_code(1)) and (not i_op_code(0)); -- 000100
    BNE_cmd <= (not i_op_code(5)) and (not i_op_code(4)) and (not i_op_code(3)) and (i_op_code(2)) and (not i_op_code(1)) and (i_op_code(0)); -- 000101
    JMP_cmd <= (not i_op_code(5)) and (not i_op_code(4)) and (not i_op_code(3)) and (not i_op_code(2)) and (i_op_code(1)) and (not i_op_code(0)); -- 000010


    -- output driver --
    o_RegDst <= RType_cmd;
    o_ALU_SRC <= LW_cmd or SW_cmd;
    o_MemToReg <= LW_cmd;
    o_RegWrite <= LW_cmd or RType_cmd;
    o_MemRead <= LW_cmd;
    o_MemWrite <= SW_cmd;
    o_Branch <= BEQ_cmd;
    O_BNE <= BNE_cmd;
    o_jump <= JMP_cmd;
    o_ALU_OP1 <= RType_cmd;
    o_ALU_OP0 <= BEQ_cmd or BNE_cmd;
end rtl;