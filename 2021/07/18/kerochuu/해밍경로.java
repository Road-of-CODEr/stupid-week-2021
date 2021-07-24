package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 7. 12.                   
public class 해밍경로 {

	static int N, K, M;
	static int[] list;
	static int[] parents;
	static HashMap<Integer, Integer> numToIdx = new HashMap<>();
	static Queue<Integer> q = new LinkedList<>();

	static Stack<Integer> stack = new Stack<>();
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = stoi(st.nextToken());
		K = stoi(st.nextToken());
		parents = new int[N + 1];
		list = new int[N + 1];

		for (int i = 1; i <= N; i++) {
			input(br.readLine().toCharArray(), i);
		}

		preProcess();

		st = new StringTokenizer(br.readLine());
		M = stoi(st.nextToken());

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			write(stoi(st.nextToken()));
		}
		System.out.println(sb.toString());
	}

	private static void preProcess() {
		init();
		while (!q.isEmpty()) {
			int num = q.poll();
			int idx = numToIdx.get(num);

			int key = 1;
			for (int i = 0; i < K; i++) {
				num ^= key;
				if (numToIdx.containsKey(num) && parents[numToIdx.get(num)] == 0) {
					q.add(num);
					parents[numToIdx.get(num)] = idx;
				}
				num ^= key;
				key <<= 1;
			}
		}
	}

	private static void init() {
		q.add(list[1]);
		parents[1] = -1;
	}

	private static void write(int target) {
		if (parents[target] != 0) {
			stack.add(target);
			while (parents[target] != -1) {
				stack.add(target = parents[target]);
			}
		}
		if (stack.isEmpty()) {
			sb.append("-1");
		} else {
			while (!stack.isEmpty()) {
				sb.append(stack.pop() + " ");
			}
		}
		sb.append("\n");
	}

	private static void input(char[] data, int idx) {
		int num = 0, key = 1;
		for (int i = K - 1; i >= 0; i--) {
			if (data[i] == '1') {
				num += key;
			}
			key <<= 1;
		}
		numToIdx.put(num, idx);
		list[idx] = num;
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
