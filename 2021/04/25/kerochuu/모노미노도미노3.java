package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 4. 23.                   
public class 모노미노도미노3 {

	static int N;
	static boolean[][] green, blue;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = stoi(st.nextToken());
		init();

		int result = 0;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			result += input(stoi(st.nextToken()), stoi(st.nextToken()), stoi(st.nextToken()));
//			print();
		}
		System.out.println(result);
		System.out.println(findNum());
	}

	private static int input(int type, int x, int y) {
		push(type, y, green);
		push(rotate(type), x + (type == 3 ? -1 : 0), blue);
		return move(green) + move(blue);
	}

	private static int rotate(int type) {
		if (type == 3) {
			return 2;
		}
		if (type == 2) {
			return 3;
		}
		return 1;
	}

	private static int move(boolean[][] map) {
		int result = 0;
		for (int i = 6; i >= 3; i--) {
			boolean isDelete = true;
			for (int j = 0; j < 4; j++) {
				if (!map[i][j]) {
					isDelete = false;
				}
			}
			if (isDelete) {
				result++;
				down(i++, map);
			}
		}

		while (isPossible(map)) {
			down(6, map);
		}
		return result;
	}

	private static boolean isPossible(boolean[][] map) {
		for (int j = 0; j < 4; j++) {
			if (map[2][j]) return true;
		}
		return false;
	}

	private static void down(int now, boolean[][] map) {
		for (int k = now; k >= 1; k--) {
			for (int j = 0; j < 4; j++) {
				map[k][j] = map[k - 1][j];
			}
		}
	}

	private static void push(int type, int y, boolean[][] target) {
		for (int k = 3; k <= 7; k++) {
			switch (type) {
			case 2:
				if (target[k][y] || target[k][y + 1]) {
					target[k - 1][y] = true;
					target[k - 1][y + 1] = true;
					return;
				}
				break;
			default:
				if (target[k][y]) {
					target[k - 1][y] = true;
					if (type == 3) {
						target[k - 2][y] = true;
					}
					return;
				}
				break;
			}
		}
	}

	private static void init() {
		green = new boolean[8][4];
		blue = new boolean[8][4];
		for (int i = 0; i < 4; i++) {
			green[7][i] = true;
			blue[7][i] = true;
		}
	}

	private static int findNum() {
		int count = 0;
		for (int i = 1; i <= 6; i++) {
			for (int j = 0; j < 4; j++) {
				if (green[i][j]) {
					count++;
				}
				if (blue[i][j]) count++;
			}
		}
		return count;
	}

	private static void print() {
		System.out.println("green");
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.print(green[i][j] ? "■" : "□");
			}
			System.out.println();
		}
		System.out.println();

		System.out.println("blue");
		for (int i = 3; i >= 0; i--) {
			for (int j = 0; j < 7; j++) {
				System.out.print(blue[j][i] ? "■" : "□");
			}
			System.out.println();
		}
		System.out.println();
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
