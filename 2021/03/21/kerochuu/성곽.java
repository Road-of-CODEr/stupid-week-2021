package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 3. 14.                   
public class 성곽 {

	static int R, C, maxSize = 0, rooms = 0;
	static int[] dx = { 0, -1, 0, 1 };
	static int[] dy = { -1, 0, 1, 0 };
	static boolean[][][] walls;
	static boolean[][] visit;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		C = stoi(st.nextToken());
		R = stoi(st.nextToken());
		init();

		for (int i = 0; i < R; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < C; j++) {
				input(i, j, stoi(st.nextToken()));
			}
		}

		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (!visit[i][j]) {
					rooms++;
					maxSize = Math.max(maxSize, dfs(i, j));
				}
			}
		}
		sb.append(rooms + "\n" + maxSize + "\n");
		sb.append(calc() + "\n");

		System.out.println(sb.toString());
	}

	private static String calc() {
		maxSize = 0;
		int temp, x = 0, y = 0, dir = 0;
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				for (int k = 0; k < 4; k++) {
					if (walls[i][j][k]) {
						initVisit();
						walls[i][j][k] = false;
						temp = dfs(i, j);
						if (temp > maxSize) {
							maxSize = temp;
							x = i;
							y = j;
							dir = k;
						}
						walls[i][j][k] = true;
					}
				}
			}
		}
		return maxSize + "";
//		return (x + 1) + " " + (y + 1) + " " + (dir == 0 ? 'N' : (dir == 1 ? 'S' : (dir == 2 ? 'W' : 'E')));
	}

	private static int dfs(int x, int y) {
//		System.out.println("dfs: " + x + " " + y);
		visit[x][y] = true;
		int allSum = 1;
		for (int i = 0; i < 4; i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];
			if (nx >= 0 && ny >= 0 && nx < R && ny < C && !walls[x][y][i] && !visit[nx][ny]) {
				allSum += dfs(nx, ny);
			}
		}
		return allSum;
	}

	private static void input(int i, int j, int num) {
		for (int k = 3; k >= 0; k--) {
			if (num >= (1 << k)) {
				num -= 1 << k;
				walls[i][j][k] = true;
			}
		}
	}

	private static void initVisit() {
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				visit[i][j] = false;
			}
		}
	}

	private static void init() {
		walls = new boolean[R][C][4];
		visit = new boolean[R][C];
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
