package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 7. 18.                   
public class 불켜기2 {

	private static class Node {
		int x, y;

		Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	static int N, M;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };

	static ArrayList<Node>[][] swcList;
	static Queue<Node> q = new LinkedList<>();
	static boolean[][] visit, swcOn, movePossible;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = stoi(st.nextToken());
		M = stoi(st.nextToken());
		init();

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			input(stoi(st.nextToken()), stoi(st.nextToken()), stoi(st.nextToken()), stoi(st.nextToken()));
		}

		System.out.println(bfs());
	}

	private static int bfs() {
		swcOn[1][1] = visit[1][1] = true;
		q.add(new Node(1, 1));

		while (!q.isEmpty()) {
			Node now = q.poll();
			for (Node next : swcList[now.x][now.y]) {
				swcOn[next.x][next.y] = true;
				if (movePossible[next.x][next.y] && !visit[next.x][next.y]) {
					visit[next.x][next.y] = true;
					q.add(new Node(next.x, next.y));
				}
			}

			for (int i = 0; i < 4; i++) {
				int nx = now.x + dx[i];
				int ny = now.y + dy[i];

				if (isInside(nx, ny)) {
					if (swcOn[nx][ny] && !visit[nx][ny]) {
						visit[nx][ny] = true;
						q.add(new Node(nx, ny));
					}
					movePossible[nx][ny] = true;
				}
			}
		}
		return count();
	}

	private static boolean isInside(int nx, int ny) {
		if (nx >= 1 && ny >= 1 && nx <= N && ny <= N) {
			return true;
		}
		return false;
	}

	private static int count() {
		int allSum = 0;
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				if (swcOn[i][j]) allSum++;
			}
		}
		return allSum;
	}

	private static void init() {
		visit = new boolean[N + 1][N + 1];
		swcOn = new boolean[N + 1][N + 1];
		movePossible = new boolean[N + 1][N + 1];
		swcList = new ArrayList[N + 1][N + 1];

		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				swcList[i][j] = new ArrayList<>();
			}
		}
	}

	private static void input(int sx, int sy, int tx, int ty) {
		swcList[sx][sy].add(new Node(tx, ty));
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
