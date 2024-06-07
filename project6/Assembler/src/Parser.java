import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * The Parser class is used to read and parse assembly language files.
 * It provides methods to read instructions line by line and categorize them.
 */
public class Parser {
    private List<String> program;
    private int currentInstruction = 0;

    /**
     * Creates a Parser object and initializes it with a List program,
     * which will be filled in as the file is read, located at location
     *
     * @param location the path to the assembly file to be read
     * @throws RuntimeException if the file cannot be read
     */
    public Parser(String location) {
        program = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader((new FileReader(location)))){
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty() && !line.trim().startsWith("//")) {
                    if (line.contains("/")) program.add(line.substring(0,line.indexOf(' ')).trim());
                    else program.add(line.trim());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read the file. Check the location and try again.");
        }
    }

    /**
     * Returns the current instruction
     * (this method was created for debugging).
     * @return the current instruction as a String
     */
    public String getInstruction() {
        return program.get(currentInstruction);
    }

    /**
     * Are there more commands in the input?
     *
     * @return true if there are more lines, false otherwise
     */
    public boolean hasMoreLines() {
        return currentInstruction < program.size();
    }

    /**
     * Resets the instruction pointer to the first instruction.
     */
    public void jumpFirst() {
        currentInstruction = 0;
    }

    /**
     * Read the next command from the input and makes it hte current command.
     * Should be called only is hasMoreCommands() is true.
     * Initially there is the 0th command.
     *
     * @throws RuntimeException if there are no more lines to read
     */
    public void advance() {
        if (hasMoreLines()) currentInstruction++;
        else throw new RuntimeException("Next string doesn't exist");
    }

    /**
     * Returns the type of the current instruction:
     * <ul>
     *     <li>A_INSTRUCTION for @xxx where xxx is either a symbol or a decimal number</li>
     *     <li>C_INSTRUCTION for dest=comp;jump</li>
     *     <li>L_INSTRUCTION (actually, pseudo-command) for (xxx) where xxx is a symbol</li>
     * </ul>
     *
     * @return the type of the current instruction as an InstructionType enum
     */
    public InstructionType instructionType() {
        char bit = program.get(currentInstruction).charAt(0);
        return switch (bit) {
            case '@' -> InstructionType.A_INSTRUCTION;
            case '(' -> InstructionType.L_INSTRUCTION;
            default -> InstructionType.C_INSTRUCTION;
        };
    }

    /**
     * Returns the symbol or decimal xxx of the current command @xxx or (xxx).
     * Should be called only when instructionType() is A_INSTRUCTION or L_INSTRUCTION.
     *
     * @return the symbol as a String, or null if not applicable
     */
    public String symbol() {
        String curr = program.get(currentInstruction);
        return switch (instructionType()) {
            case L_INSTRUCTION -> curr.substring(curr.indexOf('(') + 1, curr.indexOf(')'));
            case A_INSTRUCTION -> curr.substring(1);
            default -> null;
        };
    }

    /**
     * Returns the destination mnemonic in the current C-instruction.
     * Should be called only when instructionType() is C_INSTRUCTION.
     *
     * @return the destination mnemonic as a String, or null if not applicable
     */
    public String dest() {
        String curr = program.get(currentInstruction);
        if (instructionType().equals(InstructionType.C_INSTRUCTION)) {
            if (curr.contains("=")) return curr.substring(0, curr.indexOf('='));
            else return "";
        }
        else return null;
    }

    /**
     * Returns the computation mnemonic in the current C-instruction.
     * Should be called only when instructionType() is C_INSTRUCTION.
     *
     * @return the computation mnemonic as a String, or null if not applicable
     */
    public String comp() {
        String curr = program.get(currentInstruction);
        if (instructionType().equals(InstructionType.C_INSTRUCTION)) {
            int start;
            if (dest().equals("")) start = 0;
            else start=curr.indexOf('=') + 1;
            int end;
            if (curr.contains(";")) end = curr.indexOf(';');
            else end = curr.length();
            return curr.substring(start, end);
        }
        else return null;
    }

    /**
     * Returns the jump mnemonic in the current C-instruction.
     *
     * @return the jump mnemonic as a String, or null if not applicable
     */
    public String jump() {
        String curr = program.get(currentInstruction);
        if (instructionType().equals(InstructionType.C_INSTRUCTION)) {
            if (curr.contains(";")) return curr.substring(curr.indexOf(';') + 1);
            else return "";
        }
        else return null;
    }
}
