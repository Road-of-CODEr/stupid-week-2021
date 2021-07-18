package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 7. 18.                   
public class Rain2 {

	static int R, C;
	static int[][] origin = new int[100][100];
	static int[][] temp = new int[100][100];

	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };

	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int tnum = stoi(st.nextToken());
		for (int t = 1; t <= tnum; t++) {
			st = new StringTokenizer(br.readLine());
			R = stoi(st.nextToken());
			C = stoi(st.nextToken());
			for (int i = 0; i < R; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < C; j++) {
					origin[i][j] = stoi(st.nextToken());
					if (i == 0 || i == R - 1 || j == 0 || j == C - 1) temp[i][j] = origin[i][j]; // boundary
					else temp[i][j] = 1000;
				}
			}
			sb.append("Case #" + t + ": " + simulation() + "\n");
		}
		System.out.print(sb.toString());
	}

	private static int simulation() {
		while (isContinue(false));
		return count();
	}

	private static boolean isContinue(boolean flag) {
		for (int x = 1; x < R - 1; x++) {
			for (int y = 1; y < C - 1; y++) {
				int now = temp[x][y];
				for (int i = 0; i < 4; i++) {
					int nx = x + dx[i];
					int ny = y + dy[i];
					now = Math.min(now, Math.max(origin[nx][ny], temp[nx][ny]));
				}
				if (now < temp[x][y]) {
					temp[x][y] = now;
					flag = true;
				}
			}
		}
		return flag;
	}

	private static int count() {
		int allSum = 0;
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (temp[i][j] > origin[i][j]) {
					allSum += temp[i][j] - origin[i][j];
				}
			}
		}
		return allSum;
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
