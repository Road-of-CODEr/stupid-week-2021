package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 4. 29.                   
public class 마법사상어와블리자드 {
	static int N, M;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int[] tx = { 0, 1, 0, -1 };
	static int[] ty = { -1, 0, 1, 0 };
	static int[][] map;
	static int[] result = new int[4];
	static Queue<Integer> q = new LinkedList<>();
	static ArrayList<Integer> list = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = stoi(st.nextToken());
		M = stoi(st.nextToken());

		map = new int[N][N];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = stoi(st.nextToken());
			}
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			simulation(stoi(st.nextToken()) - 1, stoi(st.nextToken()));
		}

		System.out.println(result[1] + result[2] * 2 + result[3] * 3);
	}

	private static void simulation(int dir, int length) {
		destroy(N >> 1, N >> 1, dir, length);
		list.clear();
		alignMap(N >> 1, N >> 1);
		while (findNum(N >> 1, N >> 1)) {}
		divide();
		draw(N >> 1, N >> 1);
	}

	private static void draw(int x, int y) {
		if(q.isEmpty()) return;
		int[] tx = { 0, 1, 0, -1 };
		int[] ty = { -1, 0, 1, 0 };
		int dir = 0;

		for (int length = 1; length < N; length++) {
			for (int rotate = 0; rotate < 2; rotate++) {
				for (int step = 0; step < length; step++) {
					x += tx[dir];
					y += ty[dir];
					map[x][y] = q.poll();
					if (q.isEmpty()) {
						return;
					}
				}
				dir = (dir + 1) % 4;
			}
		}

		for (int length = 1; length < N; length++) {
			x += tx[dir];
			y += ty[dir];
			map[x][y] = q.poll();
			if (q.isEmpty()) {
				return;
			}
		}
	}

	private static void alignMap(int x, int y) {
		int dir = 0;

		for (int length = 1; length < N; length++) {
			for (int rotate = 0; rotate < 2; rotate++) {
				for (int step = 0; step < length; step++) {
					x += tx[dir];
					y += ty[dir];
					if (map[x][y] != 0) {
						list.add(map[x][y]);
						map[x][y] = 0;
					}
				}
				dir = (dir + 1) % 4;
			}
		}

		for (int length = 1; length < N; length++) {
			x += tx[dir];
			y += ty[dir];
			if (map[x][y] != 0) {
				list.add(map[x][y]);
				map[x][y] = 0;
			}
		}
	}

	private static void divide() {
		q.clear();
		if (list.size() == 0) return;
		int count = 1;
		int now = list.get(0);

		for (int i = 1; i < list.size(); i++) {
			if (now == list.get(i)) {
				count++;
			} else {
				q.add(count);
				q.add(now);
				now = list.get(i);
				count = 1;
				if (q.size() == (N * N)) {
					System.out.println("으잉???");
					return;
				}
			}
		}
		q.add(count);
		q.add(now);
	}

	private static void destroy(int x, int y, int dir, int length) {
		for (int i = length - 1; i >= 0; i--) {
			x += dx[dir];
			y += dy[dir];
			map[x][y] = 0;
		}
	}

	private static boolean findNum(int x, int y) {
		boolean isAgain = false;
		int now = -1, count = 1;
		for (int i = 0; i < list.size(); i++) {
			now = list.get(i);
			for (int j = i + 1; j < list.size(); j++) {
				if (now == list.get(j)) {
					count++;
				} else {
					if (count >= 4) {
						isAgain = true;
						result[now] += count;
						for (int k = 0; k < count; k++) {
							list.remove(i);
						}
					}
					count = 1;
					break;
				}
			}
		}
		return isAgain;
	}

	private static void print() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.format("%2d ", map[i][j]);
			}
			System.out.println();
		}
		System.out.println(result[1] + " " + result[2] + " " + result[3]);

		System.out.println();
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}

}
