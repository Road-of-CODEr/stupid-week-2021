import java.io.*;
import java.util.StringTokenizer;
 
public class Main {
 
    static int N;
    static boolean[][] map = new boolean[101][101];
 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = stoi(st.nextToken());
 
        for (int n = 0; n < N; n++) {
            st = new StringTokenizer(br.readLine());
            int R = stoi(st.nextToken());
            int C = stoi(st.nextToken());
 
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    map[R + i][C + j] = true;
                }
            }
        }
        System.out.println(calc());
    }
 
    private static int calc() {
        int count = 0;
        for (int i = 1; i <= 100; i++) {
            for (int j = 1; j <= 100; j++) {
                if (map[i][j]) {
                    if (!map[i - 1][j]) count++;
                    if (!map[i + 1][j]) count++;
                    if (!map[i][j - 1]) count++;
                    if (!map[i][j + 1]) count++;
                }
            }
        }
        return count;
    }
 
    private static int stoi(String input) {
        return Integer.parseInt(input);
    }
}