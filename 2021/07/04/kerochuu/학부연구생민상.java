package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 6. 30.                   
public class 학부연구생민상 {
	private static class Node {
		int x, y, dir;

		Node(int x, int y, int dir) {
			this.x = x;
			this.y = y;
			this.dir = dir;
		}
	}

	static int R, C;
	static int[][] map;
	static boolean[][][] visit;
	static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;

	static int[] dx = { -1, 0, 1, 0 };
	static int[] dy = { 0, 1, 0, -1 };

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
				if ((map[i][j] = stoi(st.nextToken())) == 9) {
					for (int dir = 0; dir < 4; dir++) {
						visit[i][j][dir] = true;
						q.add(new Node(i, j, dir));
					}
				}
			}
		}

		bfs();
		System.out.println(find());
	}

	private static void bfs() {
		while (!q.isEmpty()) {
			Node n = q.poll();

			int nx = n.x + dx[n.dir];
			int ny = n.y + dy[n.dir];

			if (nx >= 0 && ny >= 0 && nx < R && ny < C && !visit[nx][ny][n.dir]) {
				visit[nx][ny][n.dir] = true;

				switch (map[nx][ny]) {
				case 0:
					q.add(new Node(nx, ny, n.dir));
					break;
				case 1:
					q.add(new Node(nx, ny, n.dir % 2 == 0 ? n.dir : ((n.dir + 2) % 4)));
					break;
				case 2:
					q.add(new Node(nx, ny, n.dir % 2 == 1 ? n.dir : ((n.dir + 2) % 4)));
					break;
				case 3:
					q.add(new Node(nx, ny, convert(n.dir, true)));
					break;
				case 4:
					q.add(new Node(nx, ny, convert(n.dir, false)));
					break;
				}
			}
		}
	}

	private static int convert(int now, boolean isThree) {
		switch (now) {
		case UP:
			return isThree ? RIGHT : LEFT;
		case RIGHT:
			return isThree ? UP : DOWN;
		case DOWN:
			return isThree ? LEFT : RIGHT;
		case LEFT:
			return isThree ? DOWN : UP;
		}
		return -1;
	}

	private static int find() {
		int count = 0;
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (isPossible(i, j)) count++;
			}
		}
		return count;
	}

	private static boolean isPossible(int x, int y) {
		for (int dir = 0; dir < 4; dir++) {
			if (visit[x][y][dir]) return true;
		}
		return false;
	}

	private static void init() {
		map = new int[R][C];
		visit = new boolean[R][C][4];
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
