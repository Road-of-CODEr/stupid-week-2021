package jungOl;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 5. 15.                   
public class 연속부분합 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = stoi(st.nextToken());

		int dp = 0, result = Integer.MIN_VALUE;
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			result = Math.max(dp = calc(stoi(st.nextToken()), dp), result);
		}
		System.out.println(result);
	}

	private static int calc(int num, int dp) {
		return Math.max(num, num + dp);
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
