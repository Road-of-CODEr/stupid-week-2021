package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 5. 1.                   
public class 공 {

	static int D = 50, F = 1000;
	static int[][] dp;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		init();

		int tnum = stoi(st.nextToken());
		for (int t = 1; t <= tnum; t++) {
			st = new StringTokenizer(br.readLine());
			sb.append(calc(stoi(st.nextToken()), stoi(st.nextToken())) + "\n");
		}
		print();
		System.out.println(sb.toString());
	}

	private static void init() {
		dp = new int[D + 1][F + 1];

		for (int j = 1; j <= F; j++) {
			dp[1][j] = j; // 공이 하나일때는 현재 floor만큼 확인해야 됨
		}

		for (int i = 2; i <= D; i++) {
			dp[i][1] = 1; // 남는 floor가 하나일때는 1번만 더 하면 됨
			for (int j = 2; j <= F; j++) {
				dp[i][j] = -1; // dp table 초기화
			}
		}
	}

	private static int calc(int dama, int floor) {
		int result = dp[1][floor]; // 최소 현재 floor만큼은 확인해야 됨
		int pickD = 1, pickF = floor;
		if (dp[dama][floor] >= 0) return dp[dama][floor];

		for (int now = 1; now <= floor; now++) {
			// createDP(dama - 1, now - 1) = now에서 공이 깨짐
			// createDP(dama, floor - now) = now에서 공이 안깨짐
			if (result > 1 + Math.max(calc(dama - 1, now - 1), calc(dama, floor - now))) {
				if(calc(dama - 1, now - 1) >= calc(dama, floor - now)) {
					pickD = dama-1;
					pickF = now-1;
				} else {
					pickD = dama;
					pickF = floor - now;
				}
			}
			result = Math.min(result, 1 + Math.max(calc(dama - 1, now - 1), calc(dama, floor - now)));
		}
		
		System.out.println("dama,floor = " + dama +"," + floor + " 일 때 " + pickD + "," + pickF + "=" + dp[pickD][pickF] + " 선택");

		return dp[dama][floor] = result;
	}
	
	private static void print() {
		for(int i = 1; i <= 5; i++) {
			for(int j = 1; j <= 5; j++) {
				System.out.format("%2d ", dp[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
