import java.lang.String;
import java.lang.StringBuilder;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.awt.Color;
import java.util.Random;

/*
 *  Takes an integer argument and prints an nXn grid of automata outputs
 *
 *  This machine converts pairs of ints to binary strings,
 *  appends 0's to make them the same length,
 *  and then feeds these strings to the following 2-automaton
 *  (described by Jean-Paul Allouche and Olivier Salon):
 *  4 states:  S = {a, b, c, d}, initial state = a
 *  ( in this code, a=0, b=1, c=2, d=3 )
 *  4 transitions from S to S: {(0, 0), (0, 1), (1, 0), (1, 1)}
 *  output function tao, defined by: tao(a) = tao(d) = 0,
 *                                   tao(b) = tao(c) = 1
 */
public class Automaton {
    // used like: int nextState = nextState[currState][symbol]
    private final int[][] nextState = {{0,1,1,2},
                                       {1,0,0,3},
                                       {1,2,2,3},
                                       {0,3,3,2}};
    private final int[] tao = {0,1,1,0};
    private Map<String, Integer> transitions;
    private Map<String, Color> colors;
    private int[][] binaryGrid;
    private Color[][] colorGrid;
    private Random random = new Random();
                
    public static void main(String[] args) {
        int n = 30;
        if (args.length >= 1)
            n = Integer.valueOf(args[0]);
        Automaton automaton= new Automaton(n);
        Renderer renderer = new Renderer(automaton);
    }

    public Automaton(int n) {
        // map string to integer to use as a symbol for next state
        transitions = new HashMap<String, Integer>();
        colors = new HashMap<String, Color>();        
        transitions.put("00", 0);
        transitions.put("01", 1);
        transitions.put("10", 2);
        transitions.put("11", 3);
        for (int i = 0; i < 2; ++i)
            for (int j = 0; j < 2; ++j)
                for (int k = 0; k < 2; ++k)
                    for (int l = 0; l < 2; ++l) {
                        colors.put(new String(String.valueOf(i) +
                                              String.valueOf(j) +
                                              String.valueOf(k) +
                                              String.valueOf(l)),
                                   new Color(random.nextInt(16777216)));
                    }
        binaryGrid = new int[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                String s1 = convertToBinaryString(i);
                String s2 = convertToBinaryString(j);
                // make strings the same length
                while (s1.length() < s2.length()) {
                    s1 = "0" + s1;
                }
                while (s2.length() < s1.length()) {
                    s2 = "0" + s2;
                }
                binaryGrid[i][j] = runAutomaton(s1, s2);
            }
        }        
        createColorGrid();        
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
    public int runAutomaton(String i, String j) {
        int currState = 0;
        for (int count = i.length() - 1; count >= 0; count--) {
            String transition = Character.toString(i.charAt(count)) +
                                Character.toString(j.charAt(count));
            currState = nextState[currState][transitions.get(transition)];
        }
        return tao[currState];
    }

    public void createColorGrid() {
        colorGrid = new Color[binaryGrid.length/2][binaryGrid.length/2];
        for (int i = 0; i < colorGrid.length; ++i) {
            for (int j = 0; j < colorGrid.length; ++j) {
                String key = new String(String.valueOf(binaryGrid[i*2][j*2]) +
                                        String.valueOf(binaryGrid[i*2 + 1][j*2]) +
                                        String.valueOf(binaryGrid[i*2 + 1][j*2]) +
                                        String.valueOf(binaryGrid[i*2 + 1][j*2 + 1]));
                colorGrid[i][j] = colors.get(key);
            }
        }
    }

    public Color[][] getColorGrid() {
        return colorGrid;
    }
}