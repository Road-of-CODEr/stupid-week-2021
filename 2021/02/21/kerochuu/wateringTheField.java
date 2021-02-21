package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 2. 20.                   
public class wateringTheField {
	private static class Node implements Comparable<Node> {
		long x, y, cost;

		Node(int x, int y) {
			this.x = x;
			this.y = y;
		}

		Node(int x, int y, long cost) {
			this.x = x;
			this.y = y;
			this.cost = cost;
		}

		@Override
		public int compareTo(Node o) {
			if (this.cost > o.cost) {
				return 1;
			} else {
				return -1;
			}
		}
	}

	static int N, C;
	static int[] parents;
	static ArrayList<Node> list = new ArrayList<>();
	static PriorityQueue<Node> pq = new PriorityQueue<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = stoi(st.nextToken());
		C = stoi(st.nextToken());
		init();

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			list.add(new Node(stoi(st.nextToken()), stoi(st.nextToken())));
		}

		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				pq.add(new Node(i, j, calc(i, j)));
			}
		}

		System.out.println(kruscal());
	}

	private static long kruscal() {
		int count = 1;
		long allSum = 0;
		while (!pq.isEmpty() && count != N) {
			Node n = pq.poll();
			if (n.cost >= C && union((int) n.x, (int) n.y)) {
				count++;
				allSum += n.cost;
			}
		}
		return count == N ? allSum : -1;
	}

	private static long calc(int a, int b) {
		return (long) (Math.pow((list.get(a).x - list.get(b).x), 2) + Math.pow((list.get(a).y - list.get(b).y), 2));
	}

	private static boolean union(int a, int b) {
		a = find(a);
		b = find(b);
		if (a != b) {
			parents[b] = a;
			return true;
		}
		return false;
	}

	private static int find(int a) {
		if (parents[a] < 0) {
			return a;
		}
		return parents[a] = find(parents[a]);
	}

	private static void init() {
		parents = new int[N];
		for (int i = 0; i < N; i++) {
			parents[i] = -1;
		}
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}

}
