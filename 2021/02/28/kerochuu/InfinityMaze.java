package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 2. 25.                   
public class InfinityMaze {
	private static class Node {
		int x, y, dir;

		Node(int x, int y, int dir) {
			this.x = x;
			this.y = y;
			this.dir = dir;
		}

		boolean move() {
			int nx = x + dx[dir];
			int ny = y + dy[dir];
			if (nx >= 0 && ny >= 0 && nx < R && ny < C && map[nx][ny]) {
				x = nx;
				y = ny;
				return true;
			} else {
				dir = (dir + 1) % 4;
				return false;
			}
		}
	}

	static Node robot;
	static int R = -1, C = -1;
	static long L = -1;
	static boolean[][] map = new boolean[100][100];
	static boolean[][][] visit = new boolean[100][100][4];
	static long[][][] count = new long[100][100][4];
	static int[][] last = new int[100][100];
	static int[] dx = { 0, -1, 0, 1 };
	static int[] dy = { -1, 0, 1, 0 };
	static Queue<Node> q = new LinkedList<>();
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		String s;
		while (!input(stoi(st.nextToken()), stoi(st.nextToken()), stol(st.nextToken()))) {
			for (int i = 0; i < R; i++) {
				s = br.readLine();
				for (int j = 0; j < C; j++) {
					for (int k = 0; k < 4; k++) visit[i][j][k] = false;
					
					switch (s.charAt(j)) {
					case '#':
						map[i][j] = false;
						continue;
					case '.':
						break;
					default:
						robot = new Node(i, j, ctod(s.charAt(j)));
						break;
					}
					map[i][j] = true;
				}
			}
			bfs();
			st = new StringTokenizer(br.readLine());
		}
		System.out.print(sb.toString());
	}

	private static void bfs() {
		q.add(robot);
		visit[robot.x][robot.y][robot.dir] = true;
		count[robot.x][robot.y][robot.dir] = 0;
		long step = 0;
		while (true) {
			if (robot.move()) {
				step++;
			}
			if (visit[robot.x][robot.y][robot.dir]) {
				break;
			}
			visit[robot.x][robot.y][robot.dir] = true;
			count[robot.x][robot.y][robot.dir] = step;
			if (step == L) break;
		}
		if (step != L) { // cycle 찾으면 %연산으로 최소진행
			L = (L - count[robot.x][robot.y][robot.dir]) % (step - count[robot.x][robot.y][robot.dir]);
			for (int i = 0; i < L;) {
				if (robot.move()) {
					step++;
					i++;
				}
			}
		}

		while (!robot.move()); // 도착점에서 회전가능하면 회전
		
		sb.append((robot.x + 1) + " " + (robot.y + 1) + " " + dtoc(robot.dir) + "\n");
	}

	private static int ctod(char c) {
		switch (c) {
			case 'W': return 0;
			case 'N': return 1;
			case 'E': return 2;
			case 'S': return 3;
		}
		return -1;
	}

	private static char dtoc(int dir) {
		switch (dir) {
			case 0:	return 'W';
			case 1:	return 'N';
			case 2:	return 'E';
			case 3:	return 'S';
		}
		return 0;
	}

	private static boolean input(int r, int c, long l) {
		R = r; C = c; L = l;
		return L-- == 0 && R == 0 && C == 0;
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}

	private static long stol(String input) {
		return Long.parseLong(input);
	}
}