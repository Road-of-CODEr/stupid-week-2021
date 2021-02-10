import java.io.*;
import java.util.Stack;
import java.util.StringTokenizer;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int N = Integer.parseInt(br.readLine());
        int[] answer = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        Stack<NGE> s = new Stack<>();

        for (int i = 0; i < N; i++) {
            int number = Integer.parseInt(st.nextToken());
            while (!s.isEmpty() && number > s.peek().element) {
                answer[s.pop().position] = number;
            }
            s.push(new NGE(number, i));
        }

        for (int r : answer) {
            if (r == 0) r = -1;
            bw.write(r+" ");
        }
        bw.flush();
    }
}

class NGE {
    int element, position;

    public NGE(int element, int position) {
        this.element = element;
        this.position = position;
    }
}