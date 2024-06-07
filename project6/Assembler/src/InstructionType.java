/**
 * The InstructionType enum represents the different types of instructions
 * in the Hack assembly language.
 */
public enum InstructionType {

    /**
     * A-instruction, used for addressing memory locations.
     * Syntax: @value
     * Example: @2, @loop
     */
    A_INSTRUCTION,

    /**
     * C-instruction, used for computation.
     * Syntax: dest=comp;jump
     * Example: D;JGT, AD=M!
     */
    C_INSTRUCTION,

    /**
     * L-instruction, used for labels (symbols).
     * Syntax: (LABEL)
     * Example: (LOOP)
     */
    L_INSTRUCTION
}
