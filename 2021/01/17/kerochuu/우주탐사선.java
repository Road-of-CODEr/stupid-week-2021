package baekjoon;

import java.io.*;
import java.util.StringTokenizer;

public class 우주탐사선 {

	static int N, start, result = Integer.MAX_VALUE, count;
	static int[][] cost;
	static boolean[] visit;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = stoi(st.nextToken());
		init();

		visit[start = stoi(st.nextToken())-1] = true;

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				cost[i][j] = stoi(st.nextToken());
			}
		}

		floyd();
		backTracking(1, start, 0);

		System.out.println(result + " " + count);
	}

	private static void floyd() {
		for (int k = 0; k < N; k++) {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (k == i || i == j || j == k) continue;
					if (cost[i][k] + cost[k][j] < cost[i][j]) {
						cost[i][j] = cost[i][k] + cost[k][j];
					}
				}
			}
		}
	}

	private static void backTracking(int depth, int now, int allSum) {
		if (depth == N) {
			if (result > allSum) {
				result = allSum;
				count = 1;
			} else if (result == allSum) {
				count++;
			}
			return;
		}

		for (int i = 0; i < N; i++) {
			if (!visit[i] && cost[now][i] < 987654321) {
				visit[i] = true;
				backTracking(depth + 1, i, allSum + cost[now][i]);
				visit[i] = false;
			}
		}
	}

	private static void init() {
		visit = new boolean[N];
		cost = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				cost[i][j] = 987654321;
			}
		}
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
