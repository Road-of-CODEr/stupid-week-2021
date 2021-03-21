package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 3. 15.                   
public class gridColoring {

	static int R, C;
	static char[][] map;
	static int[] blueIdx;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = stoi(st.nextToken());
		C = stoi(st.nextToken());
		init();

		for (int i = 0; i < R; i++) {
			map[i] = br.readLine().toCharArray();
		}

		if (isPossible()) {
			findBlueIdx();
			System.out.println(calcDp());
		} else {
			System.out.println(0);
		}
	}

	private static boolean isPossible() {
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (map[i][j] != 'R') continue;
				for (int x = i; x < R; x++) {
					for (int y = j; y < C; y++) {
						if (x == i && y == j) continue;
						if (map[x][y] == 'B') {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private static void findBlueIdx() {
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (map[i][j] == 'B') {
					for (int a = 0; a <= j; a++) {
						blueIdx[a] = Math.max(blueIdx[a], i);
					}
				}
			}
		}
	}

	private static long calcDp() {
		long[] dp = new long[R + 1];
		dp[R] = 1;
		for (int j = 0; j < C; j++) {
			long[] next = new long[R + 1];
			for (int i = blueIdx[j]; i < R; i++) {
				if (i >= 0 && map[i][j] == 'R') break;
				for (int x = i + 1; x <= R; x++) {
					next[i + 1] += dp[x];
				}
			}
			dp = next;
		}

		long result = 0;
		for (int i = 0; i <= R; i++) {
			result += dp[i];
		}
		return result;
	}

	private static void init() {
		map = new char[R][C];
		blueIdx = new int[C];
		for (int i = 0; i < C; i++) {
			blueIdx[i] = -1;
		}
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
