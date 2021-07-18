package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 7. 12.                   
public class 전단지돌리기 {

	static int N, S, D;
	static int[] dist;
	static ArrayList<Integer>[] map;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = stoi(st.nextToken());
		S = stoi(st.nextToken());
		D = stoi(st.nextToken());
		init();

		for (int i = 1; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			input(stoi(st.nextToken()), stoi(st.nextToken()));
		}

		findDepth(S, 0);
		System.out.println(calc());
	}

	private static int findDepth(int now, int parent) {
		int max = 0;
		for (int next : map[now]) {
			if (next != parent) {
				max = Math.max(max, findDepth(next, now) + 1);
			}
		}
		dist[now] = max;
		return max;
	}

	private static int calc() {
		int count = 0;
		for (int i = 1; i <= N; i++) {
			if (dist[i] >= D && i != S) {
				count++;
			}
		}
		return count << 1;
	}

	private static void input(int a, int b) {
		map[a].add(b);
		map[b].add(a);
	}

	private static void init() {
		map = new ArrayList[N + 1];
		dist = new int[N + 1];
		for (int i = 1; i <= N; i++) {
			map[i] = new ArrayList<>();
		}
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
