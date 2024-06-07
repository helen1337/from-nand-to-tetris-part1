import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The AssemblerHack class translates Hack assembly language mnemonics into Hack binary code.
 */
public class AssemblerHack {
    private final static AssemblerHack hack = new AssemblerHack();
    private final HackSymbolTable symbolTable = new HackSymbolTable();
    int variableAddress = 16;
    int commandAddress = 0;
    private static ArrayList<String> code = new ArrayList<>();

    /**
     * The main method that runs the assembler.
     *
     * @param args command line arguments, the first argument should be the path to the .asm file
     */
    public static void main(String[] args) {
        String locationAsmFile = args[0];
        System.out.println("kek");
        Parser file = null;
        try {
             file = new Parser(locationAsmFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (Objects.nonNull(file)) {
            hack.fillHackMap(file);
            code = hack.HackCode(file);
            createAndWriteHackFile(code, nameHackFile(locationAsmFile));
        }
    }

    /**
     * Fills the symbol table with labels found in the .asm file.
     *
     * @param file the Parser object that reads the .asm file
     */
    private void fillHackMap(Parser file) {
        while (file.hasMoreLines()) {
            if (file.instructionType().equals(InstructionType.L_INSTRUCTION)) {
                symbolTable.addEntry(file.symbol(), commandAddress--);
            }
            commandAddress++;
            file.advance();
        }
        file.jumpFirst();
    }

    /**
     * Generates Hack machine code from the parsed assembly instructions.
     *
     * @param file the Parser object that reads the .asm file
     * @return a list of Hack machine code instructions
     */
    private ArrayList<String> HackCode(Parser file) {
        while (file.hasMoreLines()) {
            InstructionType type = file.instructionType();
            switch (type) {
                case A_INSTRUCTION -> {
                    try {
                        code.add("0" + Code.bits(Integer.parseInt(file.symbol())));
                    } catch (NumberFormatException e) {
                        if (!symbolTable.contains(file.symbol())) symbolTable.addEntry(file.symbol(), variableAddress++);
                        code.add("0" + Code.bits(symbolTable.getAddress(file.symbol())));
                    }
                }
                case C_INSTRUCTION -> {
                    code.add("111" + Code.comp(file.comp()) + Code.dest(file.dest()) + Code.jump(file.jump()));
                }
            }
            file.advance();
        }
        file.jumpFirst();
        return code;
    }

    /**
     * Generates the .hack file name from the .asm file name.
     *
     * @param locationAsmFile the location of the .asm file
     * @return the .hack file name
     */
    private static String nameHackFile(String locationAsmFile) {
        return locationAsmFile.substring(0, locationAsmFile.lastIndexOf('.') + 1) + "hack";
    }

    /**
     * Creates .hack file and writes the Hack machine code to a .hack file.
     *
     * @param code the list of Hack machine code instructions
     * @param location the location to save the .hack file
     */
    private static void createAndWriteHackFile(ArrayList<String> code, String location) {
        try (BufferedWriter hackFile = new BufferedWriter(new FileWriter(location))) {
            for (String s : code) {
                hackFile.write(s);
                hackFile.newLine();
            }
            System.out.println("Success!");
        } catch (IOException e) {
            System.out.println("Could not create hack file!");
        }
    }
}