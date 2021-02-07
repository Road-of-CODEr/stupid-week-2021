
import java.io.*;
import java.util.Stack;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        Stack<Character> stack = new Stack<>();
        int t = 0;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '(') {
                stack.push(c);
            } else {
                stack.pop();
                if (input.charAt(i - 1) == '(') {
                    t += stack.size();
                } else {
                    t++;
                }
            }
        }

        System.out.print(t);
    }
}
