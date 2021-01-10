package baekjoon;

import java.io.*;
import java.util.StringTokenizer;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 1. 10.                   
public class 사이클게임 {

	static int N, M;
	static int[] parents;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = stoi(st.nextToken());
		M = stoi(st.nextToken());

		init();
		for (int i = 1; i <= M; i++) {
			st = new StringTokenizer(br.readLine());
			if (isCycle(stoi(st.nextToken()), stoi(st.nextToken()))) {
				System.out.println(i);
				return;
			}
		}
		System.out.println(0);
	}

	private static int find(int a) {
		return parents[a] < 0 ? a : (parents[a] = find(parents[a]));
	}

	private static boolean isCycle(int a, int b) {
		if ((a = find(a)) == (b = find(b))) {
			return true;
		}
		parents[a] = b;
		return false;
	}

	private static void init() {
		parents = new int[N];
		for (int i = 0; i < N; i++) {
			parents[i] = -1;
		}
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
