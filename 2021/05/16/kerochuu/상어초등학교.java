package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 5. 16.                   
public class 상어초등학교 {

	static int N, X, Y;
	static int[][] map;
	static HashSet[][] info;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = stoi(st.nextToken());
		init();
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				st = new StringTokenizer(br.readLine());
				int num = stoi(st.nextToken());
				HashSet<Integer> hs = new HashSet<>();
				for (int k = 0; k < 4; k++) {
					hs.add(stoi(st.nextToken()));
				}
				input(num, hs);
			}
		}
		System.out.println(calc());
	}

	private static int calc() {
		int allSum = 0;
		for (int x = 1; x <= N; x++) {
			for (int y = 1; y <= N; y++) {
				int count = 0;
				for (int dir = 0; dir < 4; dir++) {
					int nx = x + dx[dir];
					int ny = y + dy[dir];

					if (info[x][y].contains(map[nx][ny])) {
						count++;
					}
				}
				allSum += (count == 0 ? 0 : Math.pow(10, count - 1));
			}
		}
		return allSum;
	}

	private static void input(int num, HashSet<Integer> hs) {
		X = -1;
		Y = -1;
		findPosition(hs);
		map[X][Y] = num;
		info[X][Y] = hs;
	}

	private static void findPosition(HashSet<Integer> hs) {
		int Empty = -1, Friends = -1;
		for (int x = 1; x <= N; x++) {
			for (int y = 1; y <= N; y++) {
				if (map[x][y] == 0) {
					int tempE = 0, tempF = 0;
					for (int d = 0; d < 4; d++) {
						int nx = x + dx[d];
						int ny = y + dy[d];
						if (map[nx][ny] == 0) {
							tempE++;
						}
						if (hs.contains(map[nx][ny])) {
							tempF++;
						}
					}

					if (tempF > Friends) {
						X = x;
						Y = y;
						Friends = tempF;
						Empty = tempE;
					} else if (tempF == Friends) {
						if (tempE > Empty) {
							X = x;
							Y = y;
							Empty = tempE;
						}
					}
				}
			}
		}
	}

	private static void init() {
		info = new HashSet[N + 2][N + 2];
		map = new int[N + 2][N + 2];
		for (int i = 0; i < N + 2; i++) {
			map[i][0] = map[i][N + 1] = map[0][i] = map[N + 1][i] = -1;
		}
	}

	private static void print() {
		for (int i = 1; i < N + 1; i++) {
			for (int j = 1; j < N + 1; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
