import java.util.HashMap;

/**
 * The HackSymbolTable class manages a symbol table that associates symbolic labels
 * with numeric addresses. This is used in the context of the Hack computer system.
 */
public class HackSymbolTable {
    private static HashMap<String,Integer> symbolTable;

    /**
     * Constructs a new HackSymbolTable and initializes it with predefined symbols.
     */
    public HackSymbolTable() {
        symbolTable = new HashMap<>();
        symbolTable.put("SP", 0);
        symbolTable.put("LCL", 1);
        symbolTable.put("ARG", 2);
        symbolTable.put("THIS", 3);
        symbolTable.put("THAT", 4);
        symbolTable.put("SCREEN", 16384);
        symbolTable.put("KBD", 24576);
        symbolTable.put("R0", 0);
        symbolTable.put("R1", 1);
        symbolTable.put("R2", 2);
        symbolTable.put("R3", 3);
        symbolTable.put("R4", 4);
        symbolTable.put("R5", 5);
        symbolTable.put("R6", 6);
        symbolTable.put("R7", 7);
        symbolTable.put("R8", 8);
        symbolTable.put("R9", 9);
        symbolTable.put("R10", 10);
        symbolTable.put("R11", 11);
        symbolTable.put("R12", 12);
        symbolTable.put("R13", 13);
        symbolTable.put("R14", 14);
        symbolTable.put("R15", 15);
    }

    /**
     * Adds the pair (symbol, address) to the table
     *
     * @param symbol the symbol to add
     * @param address the address associated with the symbol
     */
    public void addEntry(String symbol, int address) {
        symbolTable.put(symbol, address);
    }

    /**
     * Does the symbol table contain the given symbol?
     *
     * @param symbol the symbol to check
     * @return true if the symbol table contains the symbol, false otherwise
     */
    public boolean contains(String symbol) {
        return symbolTable.containsKey(symbol);
    }

    /**
     * Returns the address associated with the given symbol.
     *
     * @param symbol the symbol whose address is to be returned
     * @return the address associated with the symbol
     */
    public int getAddress(String symbol) {
        return symbolTable.get(symbol);
    }
}
