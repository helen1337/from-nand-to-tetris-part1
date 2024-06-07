import java.util.HashMap;

/**
 * The Code class contains static methods to convert assembly language mnemonics into
 * their corresponding binary codes for the Hack computer. This class uses value maps for
 * comp and jump mnemonics - compMap and jumpMap.
 */
public class Code {

    //
    private static final HashMap<String,String> compMap = compMap();

    private static final HashMap<String,String> jumpMap = jumpMap();

    /**
     * Returns the binary code of the dest mnemonic.
     * @param dest the dest mnemonic
     * @return the binary code of the dest mnemonic (ddd - 3 bits)
     */
    public static String dest(String dest) {
        return (dest.contains("A") ? "1" : "0") + (dest.contains("D") ? "1" : "0") + (dest.contains("M") ? "1" : "0");
    }

    /**
     * Returns the binary code of the comp mnemonic.
     * @param comp the comp mnemonic
     * @return the binary code of the comp mnemonic (acccccc - 7 bits)
     */
    public static String comp(String comp) {
        StringBuilder sb = new StringBuilder();
        if (comp.contains("M")) {
            sb.append("1");
            char[] compChar = comp.toCharArray();
            compChar[comp.indexOf('M')] = 'A';
            comp = String.valueOf(compChar);
        }
        else sb.append("0");
        sb.append(compMap().get(comp));
        return sb.toString();
    }

    /**
     * Returns the binary code of the jump mnemonic.
     * @param jump the jump mnemonic
     * @return the binary code of the jump mnemonic (jjj - 3 bits)
     */
    public static String jump(String jump) {
        return jumpMap.get(jump);
    }

    /**
     * Initializes and returns the comp mnemonic to binary code map.
     *
     * @return the comp mnemonic to binary code map
     */
    private static HashMap<String, String> compMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("0","101010");
        map.put("1","111111");
        map.put("-1","111010");
        map.put("D","001100");
        map.put("A","110000");
        map.put("!D","001101");
        map.put("!A","110001");
        map.put("-D","001111");
        map.put("-A","110011");
        map.put("D+1","011111");
        map.put("A+1","110111");
        map.put("D-1","001110");
        map.put("A-1","110010");
        map.put("D+A","000010");
        map.put("D-A","010011");
        map.put("A-D","000111");
        map.put("D&A","000000");
        map.put("D|A","010101");
        return map;
    }

    /**
     * Initializes and returns the jump mnemonic to binary code map.
     *
     * @return the jump mnemonic to binary code map
     */
    private static HashMap<String,String> jumpMap() {
        HashMap<String,String> map = new HashMap<>();
        map.put("","000");
        map.put(null,"000");
        map.put("JGT","001");
        map.put("JEQ","010");
        map.put("JGE","011");
        map.put("JLT","100");
        map.put("JNE","101");
        map.put("JLE","110");
        map.put("JMP","111");
        return map;
    }

    /**
     * Converts an integer to its 15-bit binary representation.
     *
     * @param n the integer to convert
     * @return the 15-bit binary representation of the integer
     */
    public static String bits(int n) {
        StringBuilder bits = new StringBuilder();
        while (n > 0) {
            if (n % 2 == 0) {
                bits.append("0");
            }
            else {
                bits.append("1");
            }
            n /= 2;
        }
        while (bits.length() < 15) {
            bits.append("0");
        }
        return bits.reverse().toString();
    }
}
