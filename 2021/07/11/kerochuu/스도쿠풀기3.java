package baekjoon;

import java.io.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 7. 9.                   
public class 스도쿠풀기3 {

	static final int N = 9;
	static int[][] map = new int[N][N];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		for (int i = 0; i < N; i++) {
			char[] input = br.readLine().toCharArray();
			for (int j = 0; j < N; j++) {
				if (input[j] != '.') {
					map[i][j] = input[j] - '0';
				}
			}
		}

		while (isContinue()) {}
		System.out.println(print());
	}

	private static boolean isContinue() {
		for (int num = 1; num <= N; num++) {
			if (isPossible(num)) return true;
		}
		return false;
	}

	private static boolean isPossible(int num) {
		boolean[] row = new boolean[N], col = new boolean[N];
		boolean[][] around = new boolean[3][3];

		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (map[x][y] == num) {
					if (row[x] || col[y] || around[x / 3][y / 3]) {
						callError();
					}
					row[x] = col[y] = around[x / 3][y / 3] = true;
				}
			}
		}

		boolean flag = false;
		for (int sx = 0; sx < 3; ++sx) {
			for (int sy = 0; sy < 3; ++sy) {
				if (!around[sx][sy] && find(sx, sy, row, col, num)) flag = true;
			}
		}
		return flag;
	}

	private static boolean find(int sx, int sy, boolean[] row, boolean[] col, int num) {
		int x = -1, y = -1;

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				if (map[3 * sx + i][3 * sy + j] == 0 && !row[3 * sx + i] && !col[3 * sy + j]) {
					if (x == -1 && y == -1) {
						x = 3 * sx + i;
						y = 3 * sy + j;
					} else {
						return false;
					}
				}
			}
		}

		if (x == -1 && y == -1) callError();

		map[x][y] = num;
		row[x] = col[y] = true;
		return true;
	}

	private static String print() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] == 0) sb.append('.');
				else sb.append(map[i][j]);
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	private static void callError() {
		System.out.println("ERROR");
		System.exit(0);
	}
}
