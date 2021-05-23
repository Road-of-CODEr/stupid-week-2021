import java.io.*;
import java.util.StringTokenizer;
 
public class Main{
 
    static int N, M;
    static int[] data = new int[31];
 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = stoi(st.nextToken());
        M = stoi(st.nextToken());
 
        data[N] = M;
 
        System.out.println(calc());
    }
 
    private static String calc() {
        for (int i = M >> 1; i <= M; i++) {
            if (check(i)) {
                return data[1] + "\n" + data[2];
            }
        }
        return "";
    }
 
    private static boolean check(int num) {
        data[N - 1] = num;
        for (int i = N - 2; i > 0; i--) {
            data[i] = data[i + 2] - data[i + 1];
            if (data[i] > data[i + 1]) {
                return false;
            }
        }
        return true;
    }
 
    private static int stoi(String input) {
        return Integer.parseInt(input);
    }
}