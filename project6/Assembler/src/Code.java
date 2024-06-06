import java.util.HashMap;

public class Code {

    private static final HashMap<String,String> compMap = compMap();

    private static final HashMap<String,String> jumpMap = jumpMap();

    public static String dest(String dest) {
        return (dest.contains("A") ? "1" : "0") + (dest.contains("D") ? "1" : "0") + (dest.contains("M") ? "1" : "0");
    }

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

    public static String jump(String jump) {
        return jumpMap.get(jump);
    }

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
