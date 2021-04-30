package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 4. 20.                   
public class 어른상어 {
	private static class Info {
		int num, time;

		Info(int sharkNum, int time) {
			this.num = sharkNum;
			this.time = time;
		}
	}

	private static class Shark implements Comparable<Shark> {
		int num, x, y;

		Shark(int sharkNum, int x, int y) {
			this.num = sharkNum;
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(Shark o) {
			return o.num - this.num;
		}
	}

	private static int N, M, K, time = 0;
	private static int[] nowDir;
	private static int[][][] rotate; // num, now, next

	private static int[] dx = { -1, 1, 0, 0 };
	private static int[] dy = { 0, 0, -1, 1 };

	private static Info[][][] map;
	private static LinkedList<Shark> list = new LinkedList<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = stoi(st.nextToken());
		M = stoi(st.nextToken());
		K = stoi(st.nextToken());

		init();

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				input(stoi(st.nextToken()), i, j);
			}
		}

		Collections.sort(list); // 입력된 상어 거꾸로 정렬

		// 현재 방향 입력
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= M; i++) {
			nowDir[i] = stoi(st.nextToken()) - 1;
		}

		// 상어 별 회전방향 입력
		for (int i = 1; i <= M; i++) {
			for (int j = 0; j < 4; j++) {
				st = new StringTokenizer(br.readLine());
				for (int k = 0; k < 4; k++) {
					rotate[i][j][k] = stoi(st.nextToken()) - 1;
				}
			}
		}

//		print();
		for (time = 0; time <= 1000 && !isOnlyOne(time);) {
			move(time++);
//			print();
		}
		System.out.println(time > 1000 ? -1 : time);
	}

	private static void move(int time) {
		int size = list.size();
lp:		for (int i = 0; i < size; i++) {
			Shark s = list.pollFirst();
			map[s.x][s.y][(time + 1) % 2] = new Info(s.num, time + K); // 핵심
			for (int dir : rotate[s.num][nowDir[s.num]]) {
				int nx = s.x + dx[dir];
				int ny = s.y + dy[dir];
				if (isEmpty(nx, ny, time)) { // 빈 곳으로
					map[nx][ny][(time + 1) % 2] = new Info(s.num, time + K + 1);
					list.addLast(new Shark(s.num, nx, ny));
					nowDir[s.num] = dir;
					continue lp;
				}
			}

			for (int dir : rotate[s.num][nowDir[s.num]]) {
				int nx = s.x + dx[dir];
				int ny = s.y + dy[dir];
				if (isCozy(s.num, nx, ny, time)) { // 자기 냄새쪽으로
					map[nx][ny][(time + 1) % 2] = new Info(s.num, time + K + 1);
					list.addLast(new Shark(s.num, nx, ny));
					nowDir[s.num] = dir;
					continue lp;
				}
			}
		}
	}

	private static boolean isEmpty(int nx, int ny, int time) {
		if (nx >= 0 && ny >= 0 && nx < N && ny < N) {
			if (time - map[nx][ny][time % 2].time >= 0) {
				return true;
			}
		}
		return false;
	}

	private static boolean isCozy(int num, int nx, int ny, int time) {
		if (nx >= 0 && ny >= 0 && nx < N && ny < N) {
			if (map[nx][ny][time % 2].num == num) {
				return true;
			}
		}
		return false;
	}

	private static boolean isOnlyOne(int time) {
		int size = list.size();
		for (int i = 0; i < size; i++) {
			Shark s = list.pollFirst();
			if (map[s.x][s.y][time % 2].num == s.num) {
				list.addLast(s);
			}
		}
		return list.size() == 1;
	}

	private static void input(int num, int x, int y) {
		if (num != 0) {
			map[x][y][0] = new Info(num, K);
			map[x][y][1] = new Info(num, K);
			list.add(new Shark(num, x, y));
		}
	}

	private static void init() {
		nowDir = new int[M + 1];
		rotate = new int[M + 1][4][4];
		map = new Info[N][N][2];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				map[i][j][0] = new Info(987654321, -K * 2);
				map[i][j][1] = new Info(987654321, -K * 2);
			}
		}
	}

	private static void print() {
		int size = list.size();
		int[][] map = new int[N][N];
		for (int i = 0; i < size; i++) {
			Shark s = list.pollFirst();
			map[s.x][s.y] = s.num;
			list.addLast(s);
		}

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] == 0) {
					System.out.print("0 ");
				} else {
					System.out.print(map[i][j] + " ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
