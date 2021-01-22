package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 1. 16.                   
public class 소풍 {

	static int K, N, F;
	static boolean[] visit;
	static boolean[][] map;
	static int[] count;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		K = stoi(st.nextToken());
		N = stoi(st.nextToken());
		F = stoi(st.nextToken());

		init();

		for (int i = 0; i < F; i++) {
			st = new StringTokenizer(br.readLine());
			addFriends(stoi(st.nextToken()), stoi(st.nextToken()));
		}

		System.out.println(findGroup());
	}

	private static String findGroup() {
		for (int i = 1; i <= N - K + 1; i++) {
			visit[i] = true;
			if (backTracking(1, i)) return print();
			visit[i] = false;
		}
		return "-1\n";
	}

	private static boolean backTracking(int depth, int now) {
		if (depth == K) return true;
		if (count[now] + depth < K) return false;

		for (int next = 1; next <= N; next++) {
			if (map[now][next] && !visit[next] && isAllFriend(next)) {
				visit[next] = true;
				if (backTracking(depth + 1, next)) return true;
				visit[next] = false;
			}
		}
		return false;
	}

	private static boolean isAllFriend(int now) {
		for (int i = 1; i <= N; i++) {
			if (visit[i] && !map[now][i]) {
				return false;
			}
		}
		return true;
	}

	private static void addFriends(int a, int b) {
		map[a][b] = true;
		map[b][a] = true;
		count[a]++;
		count[b]++;
	}

	private static String print() {
		StringBuilder sb = new StringBuilder();
		for (int j = 1; j <= N; j++) {
			if (visit[j]) sb.append(j + "\n");
		}
		return sb.toString();
	}

	private static void init() {
		visit = new boolean[N + 1];
		map = new boolean[N + 1][N + 1];
		count = new int[N + 1];
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
