import java.io.*;
 
public class Main {
 
    static char[] table;
    static char[] input;
 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 
        table = br.readLine().toCharArray();
        input = br.readLine().toCharArray();
 
        System.out.println(calc());
    }
 
    private static String calc() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length; i++) {
            if (input[i] != ' ') {
                if (input[i] < 97) {
                    input[i] = (char) (table[input[i] - 'A'] - 32);
                } else {
                    input[i] = table[input[i] - 'a'];
                }
            }
            sb.append(input[i]);
        }
        return sb.toString();
    }
}