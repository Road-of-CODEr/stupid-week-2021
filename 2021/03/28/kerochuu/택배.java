package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 3. 28.                   
public class 택배 {
	private static class Node implements Comparable<Node> {
		int start, end, value;

		Node(int start, int end, int value) {
			this.start = start;
			this.end = end;
			this.value = value;
		}

		@Override
		public int compareTo(Node o) {
			if(this.end > o.end) {
				return 1;
			}
			
			if (this.end < o.end) {
				return -1;
			} else if (this.end == o.end) {
				if (this.start < o.start) {
					return -1;
				}
			}
			return 1;
		}
	}

	static int N, C, M, allSum = 0;
	static int[] weight;
	static ArrayList<Node> list = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = stoi(st.nextToken());
		C = stoi(st.nextToken());
		weight = new int[N + 1];

		st = new StringTokenizer(br.readLine());
		M = stoi(st.nextToken());

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			list.add(new Node(stoi(st.nextToken()), stoi(st.nextToken()), stoi(st.nextToken())));
		}
		Collections.sort(list);
		System.out.println(calc());
	}

	private static int calc() {
		for (Node n : list) {
			putAsPossible(n.start, n.end, n.value);
		}
		return allSum;
	}

	private static void putAsPossible(int start, int end, int value) {
		int temp, max = 0;
		for (int i = start; i < end; i++) {
			max = Math.max(max, weight[i]);
		}

		allSum += (temp = Math.min(value, C - max));

		for (int i = start; i < end; i++) {
			weight[i] += temp;
		}
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}

}
