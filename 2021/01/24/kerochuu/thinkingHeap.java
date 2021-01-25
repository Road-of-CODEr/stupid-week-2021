package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 1. 24.                   
public class thinkingHeap {

	static int N, k, p, size = 1;
	static int[] data;
	static HashSet<Integer> hs = new HashSet<>();
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = stoi(st.nextToken());
		init();

		st = new StringTokenizer(br.readLine());
		k = stoi(st.nextToken());
		p = stoi(st.nextToken());

		data[p] = k;
		if (upIsPossible() && downIsPossible(p)) {
			System.out.print(print());
		} else {
			System.out.println("-1");
		}
		printTree();
	}

	private static boolean upIsPossible() {
		int count = 0;
		int now = p;
		while ((now >>= 1) != 0) {
			hs.add(data[now] = k - (++count));
		}
		return data[1] > 0;
	}

	private static boolean downIsPossible(int now) {
		if (now <= N) {
			hs.add(data[now] = k++);
			int left = now <<= 1;
			int right = left + 1;
			return downIsPossible(left) && downIsPossible(right);
		}
		return k <= N + 1;
	}

	private static String print() {
		int num = 1;
		for (int i = 1; i <= N; i++) {
			if (data[i] == 0) {
				while (hs.contains(num)) {
					num++;
				}
				sb.append(num++ + "\n");
			} else {
				sb.append(data[i] + "\n");
			}
		}
		return sb.toString();
	}

	private static void init() {
		while ((size <<= 1) <= N);
		data = new int[size];
	}

	private static void printTree() {
		for (int i = 1, key = 2; i <= N; i++) {
			if (i == key) {
				key <<= 1;
				System.out.println();
			}
			System.out.print(data[i] + " ");
		}
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
