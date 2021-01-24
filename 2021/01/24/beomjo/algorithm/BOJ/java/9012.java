import java.io.*;
import java.util.Stack;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int total = Integer.parseInt(bufferedReader.readLine());
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        while (total-- > 0) {
            Stack<Character> stack = new Stack<>();
            String input = bufferedReader.readLine();
            boolean isVPS = true;
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (c == '(') {
                    stack.push(c);
                } else {
                    if (!stack.isEmpty()) {
                        stack.pop();
                    } else {
                        isVPS = false;
                        break;
                    }
                }
            }
            if (!stack.isEmpty()) {
                isVPS = false;
            }

            bufferedWriter.write(isVPS ? "YES\n" : "NO\n");
        }
        bufferedWriter.flush();
        return;
    }
}