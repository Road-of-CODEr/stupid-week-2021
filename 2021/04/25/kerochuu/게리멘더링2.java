package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 4. 22.                   
public class 게리멘더링2 {

	static int N;
	static int[] num;
	static int[][] map;
	static int[][] debugMap;
	static boolean[][] no5;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = stoi(st.nextToken());
		init();

		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= N; j++) {
				map[i][j] = stoi(st.nextToken());
			}
		}

		System.out.println(simulation());
	}

	private static int simulation() {
		int result = Integer.MAX_VALUE;
		for (int x = 1; x <= N; x++) {
			for (int y = 1; y <= N; y++) {
				for (int d1 = 1; d1 < N; d1++) {
					for (int d2 = 1; isPossible(x, y, d1, d2); d2++) {
//						System.out.println(x + " " + y + " " + d1 + " " + d2);
						result = Math.min(result, calc(x, y, d1, d2));
					}
				}
			}
		}
		return result;
	}

	private static boolean isPossible(int x, int y, int d1, int d2) {
		if (1 <= x && x < x + d1 + d2 && x + d1 + d2 <= N && 1 <= y - d1 && y - d1 < y && y < y + d2 && y + d2 <= N) {
			return true;
		}
		return false;
	}

	private static int calc(int x, int y, int d1, int d2) {
		divideArea(x, y, d1, d2, true);
		fillNo5(x + 1, y, true);
		stamp();
		for (int i = 0; i < 5; i++)
			num[i] = 0;

		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				debugMap[i][j] = convert(i, j, x, y, d1, d2);
				num[convert(i, j, x, y, d1, d2)] += map[i][j];
			}
		}
//		System.out.println(Arrays.toString(num));
//		print();

		Arrays.sort(num);
		fillNo5(x + 1, y, false);
		return num[num.length - 1] - num[0];
	}

	private static void stamp() {
		for (int i = 2; i < N; i++) {
			lp: for (int j = 2; j < N; j++) {
				for (int d = 0; d < 4; d++) {
					int nx = i + dx[d];
					int ny = j + dy[d];
					if (!no5[nx][ny]) {
						continue lp;
					}
				}
				no5[i][j] = true;
			}
		}
	}

	private static void fillNo5(int x, int y, boolean flag) {
		for (int i = 0; i < 4; i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];
			if (nx >= 1 && ny >= 1 && nx <= N && ny <= N && no5[nx][ny] == !flag) {
				no5[nx][ny] = flag;
				fillNo5(nx, ny, flag);
			}
		}
	}

	private static void divideArea(int x, int y, int d1, int d2, boolean flag) {
		for (int i = 0; i < d1; i++) {
			no5[x++][y--] = flag;
		}

		for (int i = 0; i < d2; i++) {
			no5[x++][y++] = flag;
		}

		for (int i = 0; i < d1; i++) {
			no5[x--][y++] = flag;
		}

		for (int i = 0; i < d2; i++) {
			no5[x--][y--] = flag;
		}
	}

	private static int convert(int i, int j, int x, int y, int d1, int d2) {
		if (no5[i][j]) return 4;
		if (1 <= i && i < x + d1 && 1 <= j && j <= y) {
			return 0;
		} else if (1 <= i && i <= x + d2 && y < j && j <= N) {
			return 1;
		} else if (x + d1 <= i && i <= N && 1 <= j && j < y - d1 + d2) {
			return 2;
		} else if (x + d2 < i && i <= N && y - d1 + d2 <= j && j <= N) {
			return 3;
		} else {
			return 4;
		}
	}

	private static void init() {
		num = new int[5];
		map = new int[N + 1][N + 1];
		debugMap = new int[N + 1][N + 1];
		no5 = new boolean[N + 1][N + 1];
	}

	private static void print() {
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				System.out.format("%2d ", (debugMap[i][j] + 1));
			}
			System.out.println();
		}
		System.out.println();

		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				System.out.format("%c ", no5[i][j] ? '*' : ' ');
			}
			System.out.println();
		}
		System.out.println();
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}

}
