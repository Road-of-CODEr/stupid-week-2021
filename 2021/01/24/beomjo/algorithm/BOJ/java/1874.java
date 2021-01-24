
import java.io.*;
import java.util.Stack;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        Stack<Integer> stack = new Stack<>();
        int total = Integer.parseInt(br.readLine());
        int i = 1;
        while (total-- > 0) {
            int input = Integer.parseInt(br.readLine());
            while (i <= input) {
                stack.push(i);
                sb.append("+\n");
                i++;
            }

            if (stack.peek() == input) {
                stack.pop();
                sb.append("-\n");
            } else {
                System.out.print("NO");
                return;
            }
        }
        System.out.println(sb);
        br.close();
        return;
    }
}