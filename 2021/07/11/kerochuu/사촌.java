package baekjoon;

import java.io.*;
import java.util.StringTokenizer;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 7. 5.                   
public class 사촌 {

	static int N, M;
	static int[] list = new int[1000];
	static int[] parents = new int[1000];
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		parents[0] = -1;
		while (!isStop(st = new StringTokenizer(br.readLine()))) {
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				list[i] = stoi(st.nextToken());
			}

			sb.append(find() + "\n");
		}

		System.out.print(sb.toString());
	}

	private static int find() {
		int parentIdx = -1, targetIdx = 0;
		for (int i = 1; i < N; i++) {
			if (list[i] == M) {
				targetIdx = i;
			}
			if (list[i] != list[i - 1] + 1) {
				parentIdx++;
			}
			parents[i] = parentIdx;
		}

		print();
		int result = 0;
		for (int i = 1; i < N; i++) {
			if (parents[i] != parents[targetIdx] && parents[parents[i]] == parents[parents[targetIdx]]) {
				result++;
			}
		}
		return result;
	}

	private static boolean isStop(StringTokenizer st) {
		N = stoi(st.nextToken());
		M = stoi(st.nextToken());
		return N == 0 && M == 0;
	}

	private static void print() {
		System.out.println("입력받은 list");
		for (int i = 0; i < N; i++) {
			System.out.format("%2d ", list[i]);
		}
		System.out.println();
		System.out.println("parents[]");
		for (int i = 0; i < N; i++) {
			System.out.format("%2d ", parents[i]);
		}
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
