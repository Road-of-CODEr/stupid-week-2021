package baekjoon;

import java.io.*;
import java.util.StringTokenizer;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 3. 15.                   
public class bovineBallet {
	private static class Node {
		int x, y;

		Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	static int N, valid = 1, maxY, minY, maxX, minX, dir;
	static int[] dx = { 0, 1, 0, -1 };
	static int[] dy = { -1, 0, 1, 0 };
	static int[][] d = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
	static Node[] foot = new Node[4];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = stoi(st.nextToken());

		foot[0] = new Node(0, 0);
		foot[1] = new Node(1, 0);
		foot[2] = new Node(0, 1);
		foot[3] = new Node(1, 1);

		char[] data;
		boolean flag = false;

		for (int i = 0; i < N; i++) {
			data = br.readLine().toCharArray();
			if (!(flag = move(data))) {
				break;
			}
		}

		System.out.println(flag ? ((maxY - minY + 1) * (maxX - minX + 1)) : -1);
	}

	private static boolean move(char[] data) {
		int f = 0;
		if (data[0] == 'F' && data[1] == 'R') f = 1;
		else if (data[0] == 'R') if (data[1] == 'L') f = 2;
		else f = 3;

		if (data[2] == 'P') {
			for (int i = 0; i < 4; i++) {
				int ny = foot[f].y + foot[i].x - foot[f].x;
				int nx = foot[f].x + foot[f].y - foot[i].y;
				foot[i].y = ny;
				foot[i].x = nx;
			}
			dir = (dir + 1) % 4;
		}
		else {
			int m = 0;
			if (data[2] == 'R') m = 1;
			if (data[2] == 'B') m = 2;
			if (data[2] == 'L') m = 3;
			m = (m + dir) % 4;

			foot[f].y += dy[m];
			foot[f].x += dx[m];

			for (int i = 0; i < 4; i++)
				if (f != i && foot[f].y == foot[i].y && foot[f].x == foot[i].x) return false;
		}

		for (int i = 0; i < 4; i++) {
			if (minY > foot[i].y) minY = foot[i].y;
			if (maxY < foot[i].y) maxY = foot[i].y;
			if (minX > foot[i].x) minX = foot[i].x;
			if (maxX < foot[i].x) maxX = foot[i].x;
		}
		return true;
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}