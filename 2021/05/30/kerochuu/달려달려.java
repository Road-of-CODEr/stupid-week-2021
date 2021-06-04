package baekjoon;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 5. 30.                   
import java.io.*;
import java.util.StringTokenizer;

public class 달려달려 {

	static int N, M;
	static int[][] dp;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = stoi(st.nextToken());
		M = stoi(st.nextToken());
		init();

		for (int idx = 1; idx <= N; idx++) {
			calc(idx, stoi(br.readLine()));
//			print();
		}

		System.out.println(dp[N][0]);
	}

	private static void calc(int idx, int move) {
		// 1st step
		dp[idx][0] = dp[idx - 1][0];
		
		// 2nd step
		for (int tired = 1; tired <= M; tired++) {
			dp[idx][tired] = dp[idx - 1][tired - 1] + move;
		}
		
		// 3rd step
		for (int tired = 1; tired <= M && idx > tired; tired++) {
			dp[idx][0] = Math.max(dp[idx][0], dp[idx - tired][tired]);
		}
	}

	private static void print() {
		for (int i = 0; i <= N; i++) {
			System.out.format("%2d : ", i);
			for (int j = 0; j <= M; j++) {
				System.out.format("%2d ", dp[i][j]);
			}
			System.out.println();
		}
		System.out.println("--------------------");
	}

	private static void init() {
		dp = new int[N + 1][M + 1];
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
