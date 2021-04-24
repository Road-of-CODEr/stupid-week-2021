package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 4. 9.                   
public class 마법사상어와토네이도 {
	private static class Node {
		int x, y;

		Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	static int N, count = 1, result = 0;
	static int[][] map;
	static Node n;

	static int[] dx = { 0, 1, 0, -1, 1, 1, -1, -1, 0, -2, 0, 2 };
	static int[] dy = { -1, 0, 1, 0, 1, -1, 1, -1, 2, 0, -2, 0 };
	// 7 7 10 10 1 1 2 2 5
	static int[][] sx = { { -1, 1, -1, 1, -1, 1, -2, 2, 0 }, { 0, 0, 1, 1, -1, -1, 0, 0, 2 },
			{ -1, 1, -1, 1, -1, 1, -2, 2, 0 }, { 0, 0, -1, -1, 1, 1, 0, 0, -2 } };
	static int[][] sy = { { 0, 0, -1, -1, 1, 1, 0, 0, -2 }, { -1, 1, -1, 1, -1, 1, -2, 2, 0 },
			{ 0, 0, 1, 1, -1, -1, 0, 0, 2 }, { -1, 1, -1, 1, -1, 1, -2, 2, 0 } };
	static int[] value = { 7, 7, 10, 10, 1, 1, 2, 2, 5 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = stoi(st.nextToken());
		map = new int[N][N];
		n = new Node(N >> 1, N >> 1);

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = stoi(st.nextToken());
			}
		}

		simulation();
		System.out.println(result);
	}

	private static void simulation() {
		for (int length = 1, dir = 0; length < N; length++) {
			move(dir, length);
			dir = (dir + 1) % 4;
			move(dir, length);
			dir = (dir + 1) % 4;
		}
		move(0, N - 1);
	}

	private static void move(int dir, int length) {
		for (int i = 0; i < length; i++) {
//			map[n.x][n.y] = count++;
			spread(n.x + dx[dir], n.y + dy[dir], dir);
			n.x += dx[dir];
			n.y += dy[dir];
//			print();
		}
	}

	private static void spread(int x, int y, int dir) {
		int origin = map[x][y];
		int copy = origin;
		map[x][y] = 0;
		for (int i = 0; i < 9; i++) {
			int nx = x + sx[dir][i];
			int ny = y + sy[dir][i];

			int temp = (origin * value[i]) / 100;
			copy -= temp;
			if (nx >= 0 && ny >= 0 && nx < N && ny < N) {
				map[nx][ny] += temp;
			} else {
				result += temp;
			}
		}
		origin = copy;
		int nx = x + dx[dir];
		int ny = y + dy[dir];
		if (nx >= 0 && ny >= 0 && nx < N && ny < N) {
			map[nx][ny] += origin;
		} else {
			result += origin;
		}
	}

	private static void print() {
		System.out.println(" result = " + result + " 일 때");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] != 0) System.out.format("%2d ", map[i][j]);
				else {
					System.out.print("   ");
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
