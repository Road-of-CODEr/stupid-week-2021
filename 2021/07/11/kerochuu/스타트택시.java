package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 7. 5.                   
public class 스타트택시 {

	private static class Node {
		int x, y, fuel, cost;

		Node(int x, int y) {
			this.x = x;
			this.y = y;
		}

		Node(int x, int y, int fuel) {
			this.x = x;
			this.y = y;
			this.fuel = fuel;
		}

		Node(int x, int y, int fuel, int cost) {
			this.x = x;
			this.y = y;
			this.fuel = fuel;
			this.cost = cost;
		}
	}

	static int N, M, K;
	static boolean[][] map, visit;
	static int[] dx = { -1, 0, 0, 1 };
	static int[] dy = { 0, -1, 1, 0 };
	static HashMap<Integer, Node> hm = new HashMap<>();
	static Queue<Node> q = new LinkedList<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = stoi(st.nextToken());
		M = stoi(st.nextToken());
		K = stoi(st.nextToken());

		init();

		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= N; j++) {
				if (stoi(st.nextToken()) == 1) {
					map[i][j] = true;
				}
			}
		}

		st = new StringTokenizer(br.readLine());
		q.add(new Node(stoi(st.nextToken()), stoi(st.nextToken()), K));

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			hm.put(convert(stoi(st.nextToken()), stoi(st.nextToken())),
					new Node(stoi(st.nextToken()), stoi(st.nextToken())));
		}

		System.out.println(simulation());
	}

	private static int simulation() {
		for (int i = 0; i < M; i++) {
			if (findCustomer() && transport()) {
				continue;
			}
			return -1;
		}
		return q.peek().fuel;
	}

	private static boolean findCustomer() {
		visitInit();
		boolean isFind = false;
		ArrayList<Node> temp = new ArrayList<>();
		if (hm.containsKey(convert(q.peek().x, q.peek().y))) {
			return true;
		}
		while (!q.isEmpty()) {
			int size = q.size();
			for (int s = 0; s < size; s++) {
				Node n = q.poll();

				for (int i = 0; i < 4; i++) {
					int nx = n.x + dx[i];
					int ny = n.y + dy[i];

					if (isPossible(nx, ny)) {
						visit[nx][ny] = true;
						if (hm.containsKey(convert(nx, ny))) {
							temp.add(new Node(nx, ny, n.fuel - 1, 0));
//							q.add(new Node(nx, ny, n.fuel - 1, 0));
							isFind = true;
						} else if (n.fuel > 0) {
							q.add(new Node(nx, ny, n.fuel - 1));
						}
					}
				}
			}
			if (isFind) {
				q.clear();
				Collections.sort(temp, new Comparator<Node>() {

					@Override
					public int compare(Node o1, Node o2) {
						if (o1.fuel == o2.fuel) {
							if (o1.x == o2.x) {
								return o1.y - o2.y;
							}
							return o1.x - o2.x;
						} else {
							return o2.fuel - o1.fuel;
						}
					}
				});
				q.add(temp.get(0));
				return true;
			}
		}
		return false;
	}

	private static boolean transport() {
		visitInit();
		int sx = q.peek().x, sy = q.peek().y;
		Node dest = hm.get(convert(sx, sy));
		while (!q.isEmpty()) {
			Node n = q.poll();

			for (int i = 0; i < 4; i++) {
				int nx = n.x + dx[i];
				int ny = n.y + dy[i];

				if (isPossible(nx, ny) && n.fuel > 0) {
					visit[nx][ny] = true;
					if (nx == dest.x && ny == dest.y) {
						q.clear();
						q.add(new Node(nx, ny, n.fuel - 1 + (n.cost + 1) * 2));
						hm.remove(convert(sx, sy));
						return true;
					} else {
						q.add(new Node(nx, ny, n.fuel - 1, n.cost + 1));
					}
				}
			}
		}
		return false;
	}

	private static int convert(int x, int y) {
		return x * (N + 1) + y;
	}

	private static void init() {
		map = new boolean[N + 1][N + 1];
		visit = new boolean[N + 1][N + 1];
	}

	private static boolean isPossible(int nx, int ny) {
		return nx >= 1 && ny >= 1 && nx <= N && ny <= N && !map[nx][ny] && !visit[nx][ny];
	}

	private static void visitInit() {
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				visit[i][j] = false;
			}
		}
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
