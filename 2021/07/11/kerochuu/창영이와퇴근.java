package baekjoon;

import java.io.*;
import java.util.*;

//@author : blog.naver.com/kerochuu 
//@date : 2021. 7. 11. 
public class 창영이와퇴근 {
	private static class Node {
		int x, y;

		Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	static final int MAX = 1000000001;
	static int N;
	static int[][] map;
	static boolean[][] visit;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static Queue<Node> q = new LinkedList<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = stoi(st.nextToken());
		map = new int[N][N];
		visit = new boolean[N][N];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = stoi(st.nextToken());
			}
		}
		System.out.println(parametricSearch());
	}
	
	private static int parametricSearch() {
		int left = 0, right = MAX, mid;
		while (left < right) {
			mid = (left + right) >> 1;
			if (isPossible(mid)) {
				right = mid;
			} else {
				left = mid + 1;
			}
		}
		return left;
	}
	
	private static boolean isPossible(int gap) {
		init();
		while (!q.isEmpty()) {
			Node n = q.poll();
			if (n.x == N - 1 && n.y == N - 1) {
				return true;
			}

			for (int i = 0; i < 4; i++) {
				int nx = n.x + dx[i];
				int ny = n.y + dy[i];
				if (nx >= 0 && ny >= 0 && nx < N && ny < N && !visit[nx][ny]) {
					if (Math.abs(map[nx][ny] - map[n.x][n.y]) <= gap) {
						visit[nx][ny] = true;
						q.add(new Node(nx, ny));
					}
				}
			}
		}
		return false;
	}

	private static void init() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				visit[i][j] = false;
			}
		}
		q.clear();
		q.add(new Node(0, 0));
		visit[0][0] = true;
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
