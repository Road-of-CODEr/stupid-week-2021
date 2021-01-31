
import java.io.*;
import java.util.Stack;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String input = br.readLine() + "\n";

        Stack<Character> stack = new Stack<>();

        boolean isTag = false;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c == '<') isTag = true;
            if (isTag || c == ' ' || c == '\n') {
                while (!stack.isEmpty()) {
                    bw.write(stack.pop());
                }
                if (c != '\n') bw.write(c);
            } else {
                stack.push(c);
            }
            if (c == '>') isTag = false;
        }
        bw.flush();
        return;
    }
}
