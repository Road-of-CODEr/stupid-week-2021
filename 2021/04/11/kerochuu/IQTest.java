package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 4. 10.   
public class IQTest {

	static int N;
	static int[] data;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = stoi(st.nextToken());
		data = new int[N];

		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			data[i] = stoi(st.nextToken());
		}

		switch (N) {
		case 1:
			System.out.println('A');
			break;
		case 2:
			if (data[0] == data[1]) {
				System.out.println(data[0]);
			} else {
				System.out.println('A');
			}
			break;
		default:
			System.out.println(calc());
			break;
		}
	}

	private static String calc() {
		if (data[0] == data[1]) {
			for (int i = 1; i < N; i++) {
				if (data[i] != data[i - 1]) {
					return "B";
				}
			}
			return data[0] + "";
		}

		if ((data[2] - data[1]) % (data[1] - data[0]) != 0) {
			return "B";
		}

		int a = (data[2] - data[1]) / (data[1] - data[0]);
		int b = data[1] - data[0] * a;

		for (int i = 1; i < N; i++) {
			if (data[i] != data[i - 1] * a + b) {
				return "B";
			}
		}
		return (a * data[N - 1] + b) + "";
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
