package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 6. 28.                   
public class 문명 {
	private static class Node {
		int x, y;

		Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	static int N, K, count = 1, T = 0;
	static int[][] map;
	static int[] parents;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static Queue<Node> q = new LinkedList<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = stoi(st.nextToken());
		K = stoi(st.nextToken());

		init();

		for (int i = 1; i <= K; i++) {
			st = new StringTokenizer(br.readLine());
			input(i, stoi(st.nextToken()) - 1, stoi(st.nextToken()) - 1);
		}
		
		while (count != K && bfs(T++)) {}
		System.out.println(T);
	}

	private static boolean bfs(int time) {
		int size = q.size();
		for (int s = 0; s < size; s++) {
			Node now = q.poll();

			for (int i = 0; i < 4; i++) {
				int nx = now.x + dx[i];
				int ny = now.y + dy[i];

				if (isRange(nx, ny) && map[nx][ny] == 0) {
					map[nx][ny] = map[now.x][now.y];
					q.add(new Node(nx, ny));

						int tx = nx + dx[i];
						int ty = ny + dy[i];

						if (isRange(tx, ty) && map[nx][ny] != map[tx][ty] && map[tx][ty] != 0) {
							if (union(map[nx][ny], map[tx][ty]) && ++count == K) {
								return false;
							}
						}
				}
			}
		}
		return true;
	}

	private static boolean isRange(int x, int y) {
		return x >= 0 && y >= 0 && x < N && y < N;
	}

	private static void input(int idx, int x, int y) {
		q.add(new Node(x, y));
		map[x][y] = idx;

		for (int i = 0; i < 4; i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];

			if (isRange(nx, ny) && map[nx][ny] != 0 && union(map[nx][ny], map[x][y])) {
				count++;
			}
		}
	}

	static int find(int a) {
		return parents[a] < 0 ? a : (parents[a] = find(parents[a]));
	}

	static boolean union(int a, int b) {
		if ((a = find(a)) != (b = find(b))) {
			parents[b] = a;
			return true;
		}
		return false;
	}

	private static void init() {
		map = new int[N][N];
		parents = new int[K + 1];

		for (int i = 1; i <= K; i++) parents[i] = -1;
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
