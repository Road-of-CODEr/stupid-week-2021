package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 4. 10.                 
public class 여왕벌 {

	static int N, size;
	static int[] grow;
	static int[][] data;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		size = stoi(st.nextToken());
		N = stoi(st.nextToken());

		init();

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			input(stoi(st.nextToken()), stoi(st.nextToken()), stoi(st.nextToken()));
		}

		System.out.print(calc());
	}

	private static String calc() {
		int count = 0, idx = 0;
		for (int i = size - 1; i >= 0; i--) {
			data[i][0] = (count += grow[idx++]);
		}

		for (int j = 1; j < size; j++) {
			data[0][j] = (count += grow[idx++]);
		}

		for (int i = 1; i < size; i++) {
			for (int j = 1; j < size; j++) {
				data[i][j] = data[0][j];
			}
		}

		return print();
	}

	private static void input(int zero, int one, int two) {
		grow[zero]++;
		grow[one + zero]++;
	}

	private static String print() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				sb.append((data[i][j] + 1) + " ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	private static void init() {
		data = new int[size][size];
		grow = new int[size << 1];
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
