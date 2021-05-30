package baekjoon;

import java.io.*;
import java.util.StringTokenizer;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 5. 26.                   
public class hacking {
	private static class Node {
		char[] c;

		Node(char[] c, int idx) {
			this.c = new char[M];
			for (int i = 0; i < M; i++) {
				this.c[i] = c[idx + i];
			}
		}
	}

	private static class KeroList {
		Node[] data;
		int size, capacity = 8;

		KeroList() {
			data = new Node[capacity];
			size = 0;
		}

		void init() {
			size = 0;
		}

		void reallocate() {
			Node[] temp = new Node[capacity <<= 1];
			for (int i = 0; i < size; i++) {
				temp[i] = data[i];
			}
			data = temp;
		}

		void add(Node n) {
			if (size == capacity) {
				reallocate();
			}
			if (!isContain(n.c)) {
				data[size++] = n;
			}
		}

		boolean isContain(char[] c) {
			lp: for (int i = 0; i < size; i++) {
				for (int j = 0; j < M; j++) {
					if (data[i].c[j] != c[j]) {
						continue lp;
					}
				}
				return true;
			}
			return false;
		}
	}

	private static class KeroMap {
		KeroList[] keroList;

		KeroMap() {
			keroList = new KeroList[MOD];
			for (int i = 0; i < MOD; i++) {
				keroList[i] = new KeroList();
			}
		}

		void init() {
			for (int i = 0; i < MOD; i++) {
				keroList[i].init();
			}
		}

		void add(char[] input) {
			int hash = 0;
			int key = 1;

			for (int i = 0; i < input.length - M + 1; i++) {
				if (i == 0) {
					for (int j = 0; j < M; j++) {
						hash = mod(hash + mod(input[M - 1 - j] * key));
						if (j != M - 1) {
							key = mod((key * KEY));
						}
					}
				} else {
					hash = mod(KEY * (hash - mod(input[i - 1] * key)) + input[i + M - 1]);
				}

				keroList[hash].add(new Node(input, i));
			}
		}

		boolean isContain(char[] c) {
			int hash = 0, key = 1;
			for (int j = 0; j < M; j++) {
				hash = mod(hash + mod(c[M - 1 - j] * key));
				key = mod((key * KEY));
			}
			return keroList[hash].isContain(c);
		}

		int mod(int num) {
			return (num %= MOD) < 0 ? num + MOD : num;
		}
	}

	static int N, K, M;
	static final int MOD = 100003, KEY = 5381;
	static KeroMap keroMap = new KeroMap();
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int tnum = stoi(st.nextToken());

		for (int t = 1; t <= tnum; t++) {
			keroMap.init();
			st = new StringTokenizer(br.readLine());
			N = stoi(st.nextToken());
			M = stoi(st.nextToken());
			K = stoi(st.nextToken());

			keroMap.add(br.readLine().toCharArray());
			dfs(0, new char[M]);
		}
		System.out.print(sb.toString());
	}

	private static boolean dfs(int depth, char[] c) {
		if (depth == M) {
			if (!keroMap.isContain(c)) {
				for (int i = 0; i < M; i++) {
					sb.append(c[i]);
				}
				sb.append("\n");
				return true;
			}
			return false;
		}

		for (int i = 0; i < K; i++) {
			c[depth] = (char) ('a' + i);
			if (dfs(depth + 1, c)) {
				return true;
			}
		}
		return false;
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
