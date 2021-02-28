package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 2. 25.                   
public class crossCountrySkiing {
	private static class Node {
		int x, y;

		Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	static int R, C;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static boolean[][] visit;
	static int[][] map;
	static ArrayList<Node> list = new ArrayList<>();
	static Queue<Node> q = new LinkedList<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = stoi(st.nextToken());
		C = stoi(st.nextToken());

		init();

		for (int i = 0; i < R; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < C; j++) {
				map[i][j] = stoi(st.nextToken());
			}
		}

		for (int i = 0; i < R; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < C; j++) {
				if (stoi(st.nextToken()) == 1) {
					list.add(new Node(i, j));
				}
			}
		}

		System.out.println(parametricSearch());
	}

	private static long parametricSearch() {
		long left = 0, right = Integer.MAX_VALUE, mid, result = -1;

		while (left <= right) {
			mid = (left + right) >> 1;
			if (isPossible(mid)) {
				right = mid - 1;
				result = mid;
			} else {
				left = mid + 1;
			}
		}
		return result;
	}

	private static boolean isPossible(long target) {
		q.clear();
		q.add(list.get(0));
		initVisit();
		visit[list.get(0).x][list.get(0).y] = true;

		while (!q.isEmpty()) {
			Node n = q.poll();
			for (int i = 0; i < 4; i++) {
				int nx = n.x + dx[i];
				int ny = n.y + dy[i];
				if (nx >= 0 && ny >= 0 && nx < R && ny < C && !visit[nx][ny]) {
					if (Math.abs(map[n.x][n.y] - map[nx][ny]) <= target) {
						visit[nx][ny] = true;
						q.add(new Node(nx, ny));
					}
				}
			}
		}

		for (Node n : list) {
			if (!visit[n.x][n.y]) {
				return false;
			}
		}
		return true;
	}

	private static void init() {
		map = new int[R][C];
		visit = new boolean[R][C];
	}

	private static void initVisit() {
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				visit[i][j] = false;
			}
		}
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
