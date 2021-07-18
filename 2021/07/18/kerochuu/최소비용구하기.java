package baekjoon;

import java.io.*;
import java.util.*;

//@author : blog.naver.com/kerochuu 
//@date : 2021. 7. 15. 
public class 최소비용구하기 {
	private static class Node implements Comparable<Node> {
		int idx, cost;

		Node(int idx, int cost) {
			this.idx = idx;
			this.cost = cost;
		}

		@Override
		public int compareTo(Node o) {
			return this.cost - o.cost;
		}
	}

	static int N, M;
	static boolean[] visit;
	static Queue<Node>[] map;
	static PriorityQueue<Node> pq = new PriorityQueue<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = stoi(st.nextToken());
		st = new StringTokenizer(br.readLine());
		M = stoi(st.nextToken());
		init();

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			input(stoi(st.nextToken()), stoi(st.nextToken()), stoi(st.nextToken()));
		}

		st = new StringTokenizer(br.readLine());
		System.out.println(dijkstra(stoi(st.nextToken()), stoi(st.nextToken())));

	}

	private static int dijkstra(int start, int dest) {
		pq.add(new Node(start, 0));
		visit[start] = true;

		while (!pq.isEmpty()) {
			Node now = pq.poll();
			if (now.idx == dest) {
				return now.cost;
			}
			while (!map[now.idx].isEmpty()) {
				Node next = map[now.idx].poll();
				pq.add(new Node(next.idx, now.cost + next.cost));
			}
		}
		return -1;
	}

	private static void input(int a, int b, int cost) {
		map[a].add(new Node(b, cost));
		// map[b].add(new Node(a, cost));
	}

	private static void init() {
		map = new LinkedList[N + 1];
		visit = new boolean[N + 1];
		for (int i = 1; i <= N; i++) {
			map[i] = new LinkedList<>();
		}
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}