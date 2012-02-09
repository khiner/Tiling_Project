import java.lang.String;
import java.lang.StringBuilder;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

/*
 *  takes a pair of integers, converts them to binary strings, appends
 *  0's to make them the same length, and then feeds these strings to
 *  the following 2-automaton described by Jean-Paul Allouche and
 *  Olivier Salon:
 *  alphabet S = {a, b, c, d}, initial state = a
 *  4 maps from S to S: {(0, 0), (0, 1), (1, 0), (1, 1)}
 *  output function tao, defined by: tao(a) = tao(d) = 0,
 *                                   tao(b) = tao(c) = 1
 */
public class Automaton {
    // aliases for the four states, so we can use them as array indices
    public static final int a = 0, b = 1, c = 2, d = 3;
    // used like: int nextState = nextState[currState][symbol]
    public static final int[][] nextState = {{0,1,1,2},
                                             {1,0,0,3},
                                             {1,2,2,3},
                                             {0,3,3,2}};
    public static final int[] tao = {0,0,1,1};
    private static final Map<String, Integer> transitions;
    static {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("00", 0);
        map.put("01", 1);
        map.put("10", 2);
        map.put("11", 3);
        transitions = Collections.unmodifiableMap(map);
    }
    
    public static void main(String[] args) {
        // expects two integers as input
        if (args.length != 2) {
            System.out.println("Need one int arg for length.");
            System.exit(1);
        }
        Integer.valueOf(args[0]);
        String i = convertToBinaryString(Integer.valueOf(args[0]));
        String j = convertToBinaryString(Integer.valueOf(args[1]));
        // make strings the same length
        while (i.length() < j.length()) {
            i = "0" + i;
        }
        while (j.length() < i.length()) {
            j = "0" + j;
        }
        System.out.println(runAutomaton(i, j));
    }

    /*
     * @return a binary string representing the decimal number provided
     * @param n - the number to convert
     */
    public static String convertToBinaryString(int n) {
        StringBuilder binary = new StringBuilder();
        while (n > 0) {
            binary.append(n%2);
            n = n/2;
        }
        return binary.reverse().toString();        
    }

    /*
     * Make two binary strings the same length by appending zeroes
     * to the shorter of the two
     * @param i, j - the two strings
     */
    public static void makeSameLength(String i, String j) {
        if (i.length() == j.length())
            return;        
    }

    /*
     * Run the 2-automaton on a pair of strings
     * Input strings are assumed to be binary, with equal length
     * @param i, j - the input strings for the automaton.
     * @return 1 or 0, the output of the automaton
     */
    public static int runAutomaton(String i, String j) {
        int currState = a;
        for (int count = i.length() - 1; count >= 0; count--) {
            String transition = Character.toString(i.charAt(count)) +
                                Character.toString(j.charAt(count));
            System.out.println("Currstate = " + currState);
            System.out.println("Transition = " + transition);
            currState = nextState[currState][transitions.get(transition)];
        }
        return tao[currState];
    }
}