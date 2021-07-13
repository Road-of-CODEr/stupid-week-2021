package baekjoon;

import java.io.*;
import java.util.*;

//@author : blog.naver.com/kerochuu 
//@date : 2021. 7. 7.   
public class 소코반 {
	private static class Node {
		int x, y;

		Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	static int R, C, count = 1, TARGET;
	static Node player;
	static char[][] map = new char[15][15];
	static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		while (!isStop(st = new StringTokenizer(br.readLine()))) {
			TARGET = 0;
			for (int i = 0; i < R; i++) {
				char[] temp = br.readLine().toCharArray();
				for (int j = 0; j < C; j++) {
					switch (map[i][j] = temp[j]) {
					case 'W':
						TARGET++;
					case 'w':
						player = new Node(i, j);
						break;
					case '+':
						TARGET++;
						break;
					}
				}
			}
			char[] order = br.readLine().toCharArray();
			simulation(order);
			sb.append(print());
		}
		System.out.println(sb.toString());
	}

	private static void simulation(char[] order) {
		for (int i = 0; i < order.length && TARGET != 0; i++) {
			switch (order[i]) {
			case 'U':
				move(UP);
				break;
			case 'D':
				move(DOWN);
				break;
			case 'L':
				move(LEFT);
				break;
			case 'R':
				move(RIGHT);
				break;
			}
			// System.out.println(order[i] + " 일 때...");
			// System.out.println(print());
		}
	}

	private static void move(int dir) {
		int nx = player.x + dx[dir];
		int ny = player.y + dy[dir];

		switch (map[nx][ny]) {
		case 'b':
		case 'B':
			if (!isPossible(nx, ny, dir)) return;
		case '.':
		case '+':
			map[player.x][player.y] = (map[player.x][player.y] == 'W' ? '+' : '.');
			map[nx][ny] = (map[nx][ny] == '.' ? 'w' : 'W');
			player.x = nx;
			player.y = ny;
		}
	}

	private static boolean isPossible(int x, int y, int dir) {
		int nx = x + dx[dir];
		int ny = y + dy[dir];
		
		if(map[x][y] == 'B' && map[nx][ny] == '.') TARGET++;
		if(map[x][y] == 'b' && map[nx][ny] == '+') TARGET--;
		
		switch (map[nx][ny]) {
		case '.':
		case '+':
			map[x][y] = (map[x][y] == 'B' ? '+' : '.');
			map[nx][ny] = (map[nx][ny] == '.' ? 'b' : 'B');
			return true;
		}
		return false;
	}

	private static String print() {
		StringBuilder sb = new StringBuilder();
		boolean isComplete = true;

		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				sb.append(map[i][j]);
				if (map[i][j] == '+') {
					isComplete = false;
				}
			}
			sb.append("\n");
		}
		return "Game " + (count++) + ": " + (isComplete ? "complete" : "incomplete") + "\n" + sb.toString();
	}

	private static boolean isStop(StringTokenizer st) {
		R = stoi(st.nextToken());
		C = stoi(st.nextToken());
		return R == 0 && C == 0;
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
