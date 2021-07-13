package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 7. 3.                   
public class 두배열의합 {

	static int T, N, M;
	static int[] A, B;
	static ArrayList<Integer> list_A = new ArrayList<>();
	static ArrayList<Integer> list_B = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		T = stoi(st.nextToken());

		st = new StringTokenizer(br.readLine());
		A = new int[N = stoi(st.nextToken())];

		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			A[i] = stoi(st.nextToken());
		}

		st = new StringTokenizer(br.readLine());
		B = new int[M = stoi(st.nextToken())];

		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < M; i++) {
			B[i] = stoi(st.nextToken());
		}

		preProcess(list_A, A, N);
		preProcess(list_B, B, M);
		
		System.out.println(findResult());
	}

	private static long findResult() {
		int idx_a = 0;
		int idx_b = list_B.size() - 1;
		long result = 0;
		while (idx_a < list_A.size() && idx_b >= 0) {

			int sum_a = list_A.get(idx_a), sum_b = list_B.get(idx_b);
			long cnt_a = 0, cnt_b = 0;
			if (sum_a + sum_b == T) {
				while (idx_a < list_A.size() && list_A.get(idx_a) == sum_a) {
					idx_a++;
					cnt_a++;
				}
				while (idx_b >= 0 && list_B.get(idx_b) == sum_b) {
					idx_b--;
					cnt_b++;
				}
				result += cnt_a * cnt_b;
			}
			if (sum_a + sum_b > T) {
				idx_b--;
			}
			if (sum_a + sum_b < T) {
				idx_a++;
			}
		}
		return result;
	}

	private static void preProcess(ArrayList<Integer> list, int[] origin, int size) {
		for (int i = 0; i < size; i++) {
			int sum = 0;
			for (int j = i; j < size; j++) {
				list.add(sum += origin[j]);
			}
		}
		Collections.sort(list);
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
