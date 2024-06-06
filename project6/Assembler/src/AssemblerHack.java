import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class AssemblerHack {
    private final static AssemblerHack hack = new AssemblerHack();
    private HackSymbolTable symbolTable = new HackSymbolTable();
    int variableAddress = 16;
    int commandAddress = 0;
    private static ArrayList<String> code = new ArrayList<>();

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


    private void fillHackMap(Parser file) {
        while (file.hasMoreLines()) {
            if (file.instructionType().equals(InstructionType.L_INSTRUCTION)) {
                symbolTable.addEntry(file.symbol(), commandAddress--);
            }
            commandAddress++;
            file.advance();
        }
        file.jumpFirst();
        while (file.hasMoreLines()) {
            if (file.instructionType().equals(InstructionType.A_INSTRUCTION)) {
                try {
                    Integer.parseInt(file.symbol());
                }
                catch (NumberFormatException e) {
                    if (!symbolTable.contains(file.symbol())) symbolTable.addEntry(file.symbol(), variableAddress++);
                }
            }
            file.advance();
        }
        file.jumpFirst();
    }

    private ArrayList<String> HackCode(Parser file) {
        while (file.hasMoreLines()) {
            InstructionType type = file.instructionType();
            switch (type) {
                case A_INSTRUCTION -> {
                    if (symbolTable.contains(file.symbol())) {
                        code.add("0" + Code.bits(symbolTable.getAddress(file.symbol())));
                    }
                    else {
                        code.add("0" + Code.bits(Integer.parseInt(file.symbol())));
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

    private static String nameHackFile(String locationAsmFile) {
        return locationAsmFile.substring(0, locationAsmFile.lastIndexOf('.') + 1) + "hack";
    }

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
