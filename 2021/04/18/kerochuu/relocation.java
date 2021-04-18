package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 4. 19.                   
public class relocation {
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

	static int N, M, K, result = Integer.MAX_VALUE;
	static boolean[] isMarket, visit;
	static int[] market, pick;
	static ArrayList<Node>[] list;
	static int[][] table;
	static PriorityQueue<Node> pq = new PriorityQueue<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = stoi(st.nextToken());
		M = stoi(st.nextToken());
		K = stoi(st.nextToken());

		init();

		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			int idx = stoi(st.nextToken()) - 1;
			isMarket[market[i] = idx] = true;
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			input(stoi(st.nextToken()) - 1, stoi(st.nextToken()) - 1, stoi(st.nextToken()));
		}

		calc();

		backTracking(0);
		System.out.println(result);
	}

	private static void calc() {
		for (int m = 0; m < K; m++) {
			for (int idx = 0; idx < N; idx++) {
				table[m][idx] = Integer.MAX_VALUE;
			}
			dijkstra(m);
		}
	}

	private static void dijkstra(int start) {
		pq.clear();
		pq.add(new Node(market[start], 0));

		while (!pq.isEmpty()) {
			Node n = pq.poll();

			if (table[start][n.idx] > n.cost) {
				table[start][n.idx] = n.cost;
				for (Node next : list[n.idx]) {
					if (next.cost + n.cost < table[start][next.idx]) {
						pq.add(new Node(next.idx, next.cost + n.cost));
					}
				}
			}
		}
	}

	private static void backTracking(int depth) {
		if (depth == K) {
			findResult();
		}

		for (int i = 0; i < K; i++) {
			if (!visit[i]) {
				visit[i] = true;
				pick[depth] = i;
				backTracking(depth + 1);
				visit[i] = false;
			}
		}
	}

	private static void findResult() {
		int total = Integer.MAX_VALUE;
		for (int i = 0; i < N; i++) {
			if (!isMarket[i]) {
				total = Math.min(total, table[pick[0]][i] + table[pick[K - 1]][i]);
			}
		}

		for (int i = 1; i < K; i++) {
			total += table[pick[i - 1]][market[pick[i]]];
		}
		result = Math.min(result, total);
	}

	private static void input(int a, int b, int cost) {
		list[a].add(new Node(b, cost));
		list[b].add(new Node(a, cost));
	}

	private static void init() {
		isMarket = new boolean[N];
		visit = new boolean[K];
		market = new int[K];
		pick = new int[K];
		table = new int[K][N];
		list = new ArrayList[N];
		for (int i = 0; i < N; i++) {
			list[i] = new ArrayList<>();
		}
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
