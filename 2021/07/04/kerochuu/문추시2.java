package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 7. 3.                   
public class 문추시2 {

	private static class Info {
		int L, G;

		Info(int L, int G) {
			this.L = L;
			this.G = G;
		}
	}

	static final int MOD = 100001;
	static int N, P;
	static HashMap<Integer, Info> P_L;
	static TreeSet<Integer> ts;
	static TreeSet<Integer>[] ts_group;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = stoi(st.nextToken());
		init();

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			add(stoi(st.nextToken()), stoi(st.nextToken()), stoi(st.nextToken()));
		}

		st = new StringTokenizer(br.readLine());
		P = stoi(st.nextToken());

		for (int i = 0; i < P; i++) {
			st = new StringTokenizer(br.readLine());
			order(st.nextToken(), st);
		}

		System.out.println(sb.toString());
	}

	private static void order(String order, StringTokenizer st) {
		switch (order) {
		case "add":
			add(stoi(st.nextToken()), stoi(st.nextToken()), stoi(st.nextToken()));
			return;
		case "recommend":
			recommend(stoi(st.nextToken()), stoi(st.nextToken()) == 1);
			return;
		case "recommend2":
			recommend2(stoi(st.nextToken()) == 1);
			return;
		case "recommend3":
			recommend3(stoi(st.nextToken()) == 1, stoi(st.nextToken()) * MOD);
			return;
		case "solved":
			solved(stoi(st.nextToken()));
			return;
		}
	}
	
	// 문제 번호 P, 난이도 L, 알고리즘 분류 G
	private static void add(int P, int L, int G) {
		int hash = convert(L, P);
		ts.add(hash);
		ts_group[G].add(hash);
		P_L.put(P, new Info(L, G));
	}

	private static void recommend(int G, boolean isFindLast) {
		sb.append(extract_P(isFindLast ? ts_group[G].last() : ts_group[G].first()) + "\n");
	}

	private static void recommend2(boolean isFindLast) {
		sb.append(extract_P(isFindLast ? ts.last() : ts.first()) + "\n");
	}

	private static void recommend3(boolean isFindLast, int key) {
		if(isFindLast ? ts.higher(key) != null : ts.lower(key) != null) {
			sb.append(extract_P(isFindLast ? ts.higher(key) : ts.lower(key)) + "\n");
		} else {
			sb.append("-1\n");
		}
	}

	private static void solved(int P) {
		int L = P_L.get(P).L;
		int G = P_L.get(P).G;
		int hash = convert(L, P);
		ts_group[G].remove(hash);
		ts.remove(hash);
	}

	private static int convert(int L, int P) {
		return L * MOD + P;
	}

	private static int extract_P(int hash) {
		return hash % MOD;
	}

	private static void init() {
		P_L = new HashMap<>();
		ts = new TreeSet<>();
		ts_group = new TreeSet[101];
		for (int i = 1; i <= 100; i++) {
			ts_group[i] = new TreeSet<>();
		}
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
