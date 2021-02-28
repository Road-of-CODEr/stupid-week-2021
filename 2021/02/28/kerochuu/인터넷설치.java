package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2020. 9. 27.                   
public class 인터넷설치 {
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

	static int N, M, free;
	static boolean[] visit;
	static ArrayList<Node>[] map;
	static PriorityQueue<Node> pq = new PriorityQueue<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = stoi(st.nextToken());
		M = stoi(st.nextToken());
		free = stoi(st.nextToken());

		init();

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int v1 = stoi(st.nextToken());
			int v2 = stoi(st.nextToken());
			int cost = stoi(st.nextToken());
			map[v1].add(new Node(v2, cost));
			map[v2].add(new Node(v1, cost));
		}

		visit[1] = true;
		pq.add(new Node(1, 0));
		System.out.println(dijkstra());

	}

	private static int dijkstra() {
		PriorityQueue<Integer> cost = new PriorityQueue<Integer>(new Comparator<Integer>() {
			@Override
			public int compare(Integer arg0, Integer arg1) {
				return arg1 - arg0;
			}
		});

		while (!pq.isEmpty()) {
			Node now = pq.poll();
			if (now.idx == N) {
				return resultCalc(cost);
			}

			for (Node next : map[now.idx]) {
				if (!visit[next.idx]) {
					visit[next.idx] = true;
					pq.add(new Node(next.idx, now.cost + next.cost));
					cost.add(next.cost);
					if (next.idx == N) {
						return resultCalc(cost);
					}
				}
			}
		}
		return -1;
	}

	private static int resultCalc(PriorityQueue<Integer> cost) {

		for (int i = 0; i < free && !cost.isEmpty(); i++) {
			System.out.println("제외됨 " + cost.poll());
		}
		return cost.isEmpty() ? 0 : cost.peek();
	}

	private static void init() {
		visit = new boolean[N + 1];
		map = new ArrayList[N + 1];
		for (int i = 1; i <= N; i++) {
			map[i] = new ArrayList<>();
		}
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}

}
