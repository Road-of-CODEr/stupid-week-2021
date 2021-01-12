package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 1. 10.                   
public class Facebook {

	static int N, M, size;
	static int[][] map;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = stoi(st.nextToken());
		init();

		for (int i = 0; i < N; i++) {
			input(br.readLine().toCharArray(), i);
		}

		st = new StringTokenizer(br.readLine());
		M = stoi(st.nextToken());

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			sb.append(calc(stoi(st.nextToken()) - 1, stoi(st.nextToken()) - 1));
		}

		System.out.print(sb.toString());
	}

	private static String calc(int a, int b) {
		int count = 0;
		int temp;

		for (int i = size - 1; i >= 0; i--) {
			temp = map[a][i] & map[b][i];

			while (temp != 0) {
				temp &= temp - 1;
				count++;
			}
		}
		return count + "\n";
	}
	
	private static void input(char[] temp, int i) {
		int allSum = 0;
		int key = 1;
		int idx = size - 1;
		for (int j = temp.length - 1; j >= 0; j--, key <<= 1) {
			if (temp[j] == '1') {
				allSum += key;
			}

			if (key == 1 << 31) {
				map[i][idx--] = allSum;
				key = 1;
				allSum = 0;
			}
		}
		map[i][idx--] = allSum;
	}

	private static void init() {
		size = (N / 30) + 1;
		map = new int[N][size];
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
