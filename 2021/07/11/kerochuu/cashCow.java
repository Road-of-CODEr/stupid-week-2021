package baekjoon;

import java.io.*;
import java.util.*;

//@author : blog.naver.com/kerochuu 
//@date : 2021. 7. 10.   
public class cashCow {
	private static class Node {
		int x, y;

		Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	static final char EMPTY = 0;
	static int N, R = 12, C = 10;
	static int[] dx = { -1, 0, 1, 0 };
	static int[] dy = { 0, 1, 0, -1 };
	static char[][] map = new char[13][11];
	static boolean[][] visit = new boolean[13][11];
	static StringBuilder sb = new StringBuilder();
	static Queue<Node> q = new LinkedList<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		while (isContinue(st = new StringTokenizer(br.readLine()))) {
			for (int i = 1; i <= R; i++) {
				char[] input = br.readLine().toCharArray();
				for (int j = 0; j < C; j++) {
					map[i][j] = input[j];
				}
			}
			print(0);
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				shoot((st.nextToken().charAt(0) - 'a'), 13 - stoi(st.nextToken()));
				downFall();
				pushLeft();
				print(i + 1);
			}
			System.out.println("--------------");
			sb.append(calc() + "\n");
		}
		System.out.println(sb.toString());
	}

	private static void shoot(int y, int x) {
		char type = map[x][y];
		if (type == EMPTY || !upperThree(x, y)) {
			return;
		}

		map[x][y] = EMPTY;
		q.add(new Node(x, y));
		while (!q.isEmpty()) {
			Node n = q.poll();
			for (int i = 0; i < 4; i++) {
				int nx = n.x + dx[i];
				int ny = n.y + dy[i];

				if (nx >= 1 && ny >= 0 && nx <= R && ny < C && map[nx][ny] == type) {
					map[nx][ny] = EMPTY;
					q.add(new Node(nx, ny));
				}
			}
		}
	}

	private static boolean upperThree(int x, int y) {
		init();
		return dfs(x, y, 0, map[x][y]) >= 3;
	}

	private static int dfs(int x, int y, int depth, char type) {
		if (depth == 2) {
			return 0;
		}
		int count = 0;
		for (int i = 0; i < 4; i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];
			if (nx >= 1 && ny >= 0 && nx <= R && ny < C && map[nx][ny] == type && !visit[nx][ny]) {
				visit[nx][ny] = true;
				count += dfs(nx, ny, depth + 1, type) + 1;
			}
		}
		return count;
	}

	private static void downFall() {
		for (int y = 0; y < C; y++) {
			int now = R, pick;
			while (now > 1) {
				pick = now - 1;
				if (map[now][y] == EMPTY) {
					while (pick > 0 && map[pick][y] == EMPTY) {
						pick--;
					}
					swap(now, pick, y);
				}
				now--;
			}
		}
	}

	private static void pushLeft() {
		for (int y = 0; y < C - 1; y++) {
			if (map[R][y] == EMPTY) {
				int pick = y + 1;
				while (pick < C && map[R][pick] == EMPTY) {
					pick++;
				}
				for (int x = 1; x <= R; x++) {
					map[x][y] = map[x][pick];
					map[x][pick] = EMPTY;
				}
			}
		}
	}

	private static int calc() {
		int count = 0;
		for (int i = 1; i <= R; i++) {
			for (int j = 0; j < C; j++) {
				if (map[i][j] >= 'A' && map[i][j] <= 'Z') {
					count++;
				}
			}
		}
		return count;
	}

	private static void swap(int now, int pick, int y) {
		char temp = map[now][y];
		map[now][y] = map[pick][y];
		map[pick][y] = temp;
	}

	private static void init() {
		for (int i = 1; i <= R; i++) {
			for (int j = 0; j < C; j++) {
				visit[i][j] = false;
			}
		}
	}

	private static boolean isContinue(StringTokenizer st) {
		return (N = stoi(st.nextToken())) != 0;
	}

	private static void print(int num) {
		if (num == 0) {
			System.out.println("입력 : ");
		} else {
			System.out.println(num + " : ");
		}
		for (int i = 1; i <= R; i++) {
			for (int j = 0; j < C; j++) {
				if(map[i][j] == EMPTY) {
					System.out.print(".");
				} else {
				System.out.print(map[i][j]);
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
