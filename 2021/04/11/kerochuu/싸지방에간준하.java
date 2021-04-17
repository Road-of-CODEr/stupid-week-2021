package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 4. 11.                   
public class 싸지방에간준하 {

	private static class Node implements Comparable<Node> {
		int start, end;

		Node(int start, int end) {
			this.start = start;
			this.end = end;
		}

		@Override
		public int compareTo(Node o) {
			if (this.start == o.start) {
				return this.end - o.end;
			}
			return this.start - o.start;
		}
	}

	private static class Info implements Comparable<Info> {
		int time, idx;

		Info(int time, int idx) {
			this.time = time;
			this.idx = idx;
		}

		@Override
		public int compareTo(Info o) {
			return this.time - o.time;
		}
	}

	static int N, size = 1000010;
	static int[] data, count;
	static Node[] list;
	
	static PriorityQueue<Info> pq = new PriorityQueue<>();
	static PriorityQueue<Integer> index = new PriorityQueue<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = stoi(st.nextToken());
		init();

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			input(i, stoi(st.nextToken()), stoi(st.nextToken()));
		}

		Arrays.sort(list);

		System.out.println(simulation(findMinNum()));
	}

	private static int findMinNum() {
		int com = 0, result = 0;

		for (int i = 0; i < size; i++) {
			com += data[i];
			result = Math.max(com, result);
		}
		return result;
	}

	private static String simulation(int com) {
		preprocess(com);

		for (int i = 0; i < N; i++) {
			Node n = list[i];
			if (!pq.isEmpty()) {
				while (!pq.isEmpty() && pq.peek().time < n.start) {
					index.add(pq.poll().idx);
				}
			}
			count[index.peek()]++;
			pq.add(new Info(n.end, index.poll()));
		}
		return print(com);
	}

	private static void input(int idx, int start, int end) {
		data[start]++;
		data[end]--;
		list[idx] = new Node(start, end);
	}

	private static void init() {
		data = new int[size];
		list = new Node[N];
	}

	private static void preprocess(int com) {
		count = new int[com + 1];
		for (int i = 1; i <= com; i++) {
			index.add(i);
		}
	}

	private static String print(int com) {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= com; i++) {
			sb.append(count[i] + " ");
		}
		return com + "\n" + sb.toString();
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
