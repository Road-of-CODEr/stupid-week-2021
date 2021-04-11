package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 4. 5.                   
public class 마법사상어와화염구 {

	private static class Node {
		int x, y;

		Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	private static class Value {
		int weight, speed, dir;

		Value(int weight, int speed, int dir) {
			this.weight = weight;
			this.speed = speed;
			this.dir = dir;
		}
	}

	static int N, M, K;
	static Queue<Value>[][][] map;
	static Queue<Node> q = new LinkedList<Node>();

	static int[] dx = { -1, -1, 0, 1, 1, 1, 0, -1 };
	static int[] dy = { 0, 1, 1, 1, 0, -1, -1, -1 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = stoi(st.nextToken());
		M = stoi(st.nextToken());
		K = stoi(st.nextToken());

		init();

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			input(stoi(st.nextToken()) - 1, stoi(st.nextToken()) - 1, stoi(st.nextToken()), stoi(st.nextToken()),
					stoi(st.nextToken()));
		}

		for (int i = 0; i < K; i++) {
//			print(0);
			move(0, 1);
			divide(1, 0);
		}
//		print(0);
		System.out.println(calc());
	}

	private static void move(int now, int next) {
		int size = q.size();
		for (int i = 0; i < size; i++) {
			Node n = q.poll();
			Value v = map[n.x][n.y][now].poll();

			int nx = mod(n.x + dx[v.dir] * v.speed);
			int ny = mod(n.y + dy[v.dir] * v.speed);

			q.add(new Node(nx, ny));
			map[nx][ny][next].add(v);
		}
	}

	private static void divide(int now, int next) {
		int size = q.size(), count;
		for (int i = 0; i < size; i++) {
			Node n = q.poll();
			if (map[n.x][n.y][now].size() > 1) {
				count = map[n.x][n.y][now].size();
				Value v = map[n.x][n.y][now].poll();
				boolean isCross = true;
				while (!map[n.x][n.y][now].isEmpty()) {
					Value temp = map[n.x][n.y][now].poll();
					if (v.dir % 2 != temp.dir % 2) {
						isCross = false;
					}
					v.speed += temp.speed;
					v.weight += temp.weight;
				}
				v.speed /= count;
				v.weight /= 5;
				if (v.weight > 0) {
					for (int dir = isCross ? 0 : 1; dir < 8; dir += 2) {
						map[n.x][n.y][next].add(new Value(v.weight, v.speed, dir));
						q.add(new Node(n.x, n.y));
					}
				}
			} else if (map[n.x][n.y][now].size() == 1) {
				map[n.x][n.y][next].add(map[n.x][n.y][now].poll());
				q.add(n);
			}
		}
	}

	private static int calc() {
		int allSum = 0;
		while (!q.isEmpty()) {
			Node n = q.poll();
			Value v = map[n.x][n.y][0].poll();
			allSum += v.weight;
		}
		return allSum;
	}

	private static void input(int x, int y, int weight, int speed, int dir) {
		map[x][y][0].add(new Value(weight, speed, dir));
		q.add(new Node(x, y));
	}

	private static void init() {
		map = new LinkedList[N][N][2];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				for (int k = 0; k < 2; k++) {
					map[i][j][k] = new LinkedList<Value>();
				}
			}
		}
	}

	private static void print(int now) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j][now].size() == 0) {
					System.out.print(". ");
				} else {
					System.out.print(map[i][j][now].size() + " ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	private static int mod(int idx) {
		return (idx %= N) < 0 ? idx + N : idx;
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
