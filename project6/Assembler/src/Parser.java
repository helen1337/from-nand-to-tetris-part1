import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private List<String> program;
    private int currentInstruction = 0;
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

    public String getInstruction() {
        return program.get(currentInstruction);
    }
    public boolean hasMoreLines() {
        return currentInstruction < program.size();
    }

    public void jumpFirst() {
        currentInstruction = 0;
    }
    public void advance() {
        if (hasMoreLines()) currentInstruction++;
        else throw new RuntimeException("Next string doesn't exist");
    }

    public InstructionType instructionType() {
        char bit = program.get(currentInstruction).charAt(0);
        return switch (bit) {
            case '@' -> InstructionType.A_INSTRUCTION;
            case '(' -> InstructionType.L_INSTRUCTION;
            default -> InstructionType.C_INSTRUCTION;
        };
    }

    public String symbol() {
        String curr = program.get(currentInstruction);
        return switch (instructionType()) {
            case L_INSTRUCTION -> curr.substring(curr.indexOf('(') + 1, curr.indexOf(')'));
            case A_INSTRUCTION -> curr.substring(1);
            default -> null;
        };
    }

    public String dest() {
        String curr = program.get(currentInstruction);
        if (instructionType().equals(InstructionType.C_INSTRUCTION)) {
            if (curr.contains("=")) return curr.substring(0, curr.indexOf('='));
            else return "";
        }
        else return null;
    }

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

    public String jump() {
        String curr = program.get(currentInstruction);
        if (instructionType().equals(InstructionType.C_INSTRUCTION)) {
            if (curr.contains(";")) return curr.substring(curr.indexOf(';') + 1);
            else return "";
        }
        else return null;
    }
}
