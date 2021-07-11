package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 7. 11.                   
public class 견우와직녀 {
	private static class Node {
		int x, y, step;

		Node(int x, int y) {
			this.x = x;
			this.y = y;
		}

		Node(int x, int y, int step) {
			this.x = x;
			this.y = y;
			this.step = step;
		}
	}

	static int N, M;
	static int[][] map;
	static int[][] visit;
	static int[] dx = { -1, 0, 1, 0 };
	static int[] dy = { 0, -1, 0, 1 };
	static Queue<Node> q = new LinkedList<>();
	static Queue<Node> bridge = new LinkedList<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = stoi(st.nextToken());
		M = stoi(st.nextToken());
		map = new int[N][N];
		visit = new int[N][N];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = stoi(st.nextToken());
			}
		}

		preProcess();

		System.out.println(simulation());
	}

	private static int simulation() {
		int result = Integer.MAX_VALUE;
		while (!bridge.isEmpty()) {
			Node n = bridge.poll();
			map[n.x][n.y] = M;
			result = Math.min(result, bfs());
			map[n.x][n.y] = 0;
//			print();
		}
		return result;
	}

	private static int bfs() {
		init();
		while (!q.isEmpty()) {
			Node n = q.poll();

			for (int i = 0; i < 4; i++) {
				int nx = n.x + dx[i];
				int ny = n.y + dy[i];
				int next = n.step + 1;

				if (isInside(nx, ny) && map[nx][ny] != 0) {
					if (map[nx][ny] == 1) {
						if (visit[nx][ny] > next) {
							visit[nx][ny] = next;
							q.add(new Node(nx, ny, next));
						}
					} else if (map[n.x][n.y] == 1) {
						while ((next % map[nx][ny]) != 0) {
							next++;
						}
						if (visit[nx][ny] > next) {
							visit[nx][ny] = next;
							q.add(new Node(nx, ny, next));
						}
					}
				}
			}
		}
		return visit[N - 1][N - 1];
	}

	private static void init() {
		q.clear();
		q.add(new Node(0, 0, 0));
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				visit[i][j] = Integer.MAX_VALUE;
			}
		}
		visit[0][0] = 0;
	}

	private static void preProcess() {
		for (int x = 0; x < N; x++) {
			for (int y = 0; y < N; y++) {
				if (map[x][y] == 0) {
					isBuild(x, y);
				}
			}
		}
	}

	private static void isBuild(int x, int y) {
		for (int i = 0; i < 4; i++) {
			int ax = x + dx[i];
			int ay = y + dy[i];

			int bx = x + dx[(i + 1) % 4];
			int by = y + dy[(i + 1) % 4];

			if (isInside(ax, ay) && isInside(bx, by)) {
				if (map[ax][ay] == 0 && map[bx][by] == 0) {
					return;
				}
			}
		}
		bridge.add(new Node(x, y));
	}

	private static boolean isInside(int nx, int ny) {
		return nx >= 0 && ny >= 0 && ny < N && nx < N;
	}

	private static void print() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (visit[i][j] == Integer.MAX_VALUE) {
					System.out.format("%2d ", -1);
				} else {
					System.out.format("%2d ", visit[i][j]);
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
