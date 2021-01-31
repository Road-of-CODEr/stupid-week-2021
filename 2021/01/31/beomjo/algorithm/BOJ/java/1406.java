
import java.io.*;
import java.util.Stack;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        int t = Integer.parseInt(br.readLine());
        Stack<Character> leftStack = new Stack<>();
        Stack<Character> rightStack = new Stack<>();
        
        for (int i = 0; i < str.length(); i++) {
            leftStack.push(str.charAt(i));
        }

        while (t-- > 0) {
            String input = br.readLine();
            String[] inputs = input.split(" ");
            if ("L".equals(inputs[0])) {
                if (leftStack.size() > 0) {
                    Character c = leftStack.pop();
                    rightStack.push(c);
                }
            } else if ("D".equals(inputs[0])) {
                if (rightStack.size() > 0) {
                    Character c = rightStack.pop();
                    leftStack.push(c);
                }
            } else if ("B".equals(inputs[0])) {
                if (leftStack.size() > 0) {
                    leftStack.pop();
                }
            } else {
                leftStack.push(inputs[1].charAt(0));
            }
        }

        StringBuilder sb = new StringBuilder();
        while (!leftStack.isEmpty()) {
            rightStack.push(leftStack.pop());
        }

        while (!rightStack.isEmpty()) {
            sb.append(rightStack.pop());
        }

        System.out.print(sb);
    }
}