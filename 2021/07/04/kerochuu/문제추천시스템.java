package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 6. 28.                   
public class 문제추천시스템 {
	private static class Node {
		int L, G;

		Node(int L, int G) {
			this.L = L;
			this.G = G;
		}
	}

	static int N, M;
	static StringBuilder sb = new StringBuilder();

	static TreeMap<Integer, ArrayList<Integer>>[] first_tm = new TreeMap[101];
	static TreeMap<Integer, ArrayList<Integer>> second_tm = new TreeMap<>();
	static HashMap<Integer, ArrayList<Node>> hm = new HashMap<>();

	public static void main(String[] args) throws IOException {
		init();

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = stoi(st.nextToken());

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			input(stoi(st.nextToken()), stoi(st.nextToken()), stoi(st.nextToken()));
		}

		st = new StringTokenizer(br.readLine());
		M = stoi(st.nextToken());

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			input(st);
		}

		System.out.println(sb.toString());
	}

	private static void input(int P, int L, int G) { // 문제번호, 난이도, 분류
		if (!first_tm[G].containsKey(L)) {
			first_tm[G].put(L, new ArrayList<>());
		}
		first_tm[G].get(L).add(P);
		// System.out.println("first_tm[" + G + "].get(" + L + ").add(" + P +
		// ")");

		if (!second_tm.containsKey(L)) {
			second_tm.put(L, new ArrayList<>());
		}
		second_tm.get(L).add(P);

		if (!hm.containsKey(P)) {
			hm.put(P, new ArrayList<>());
		}
		hm.get(P).add(new Node(L, G));
	}

	private static void input(StringTokenizer st) {
		String order = st.nextToken();

		switch (order) {
		case "add":
			int P = stoi(st.nextToken()), L = stoi(st.nextToken()), G = stoi(st.nextToken());
			input(P,L,G);
			Collections.sort(first_tm[G].get(L));
			Collections.sort(second_tm.get(L));
			break;
		case "recommend":
			recommend(stoi(st.nextToken()), stoi(st.nextToken()) == 1);
			break;
		case "recommend2":
			recommend2(stoi(st.nextToken()) == 1);
			break;
		case "recommend3":
			recommend3(stoi(st.nextToken()) == 1, stoi(st.nextToken()));
			break;
		case "solved":
			solved(stoi(st.nextToken()));
			break;
		}
	}

	private static void recommend(int G, boolean isBig) {
//		System.out.println("first_tm[" + G + "] 에서 찾는중... ");
		if (isBig) {
//			System.out.println(first_tm[G].lastKey() + " 일때...list는...");
			ArrayList<Integer> list = first_tm[G].get(first_tm[G].lastKey());
//			System.out.println("list size = " + list.size());
			sb.append(list.get(list.size() - 1) + "\n");
		} else {
			ArrayList<Integer> list = first_tm[G].get(first_tm[G].firstKey());
//			System.out.println("list size = " + list.size());
			sb.append(list.get(0) + "\n");
		}
	}

	private static void recommend2(boolean isBig) {
		if (isBig) {
			ArrayList<Integer> list = second_tm.get(second_tm.lastKey());
//			System.out.println("list size = " + list.size());
			sb.append(list.get(list.size() - 1) + "\n");
		} else {
			ArrayList<Integer> list = second_tm.get(second_tm.firstKey());
//			System.out.println("list size = " + list.size());
			sb.append(list.get(0) + "\n");
		}
	}

	private static void recommend3(boolean isBig, int L) {
		if (isBig) {
			if (second_tm.higherKey(L) != null) {
				ArrayList<Integer> list = second_tm.get(second_tm.higherKey(L - 1));
				sb.append(list.get(0) + "\n");
//				System.out.println(L + " 보다 크면서 제일 가까운 난이도 = " + second_tm.higherKey(L - 1));
			} else {
				sb.append("-1\n");
			}
		} else {
			if (second_tm.lowerKey(L) != null) {
				ArrayList<Integer> list = second_tm.get(second_tm.lowerKey(L));
				sb.append(list.get(list.size() - 1) + "\n");
//				System.out.println(L + " 보다 작으면서 제일 가까운 난이도 = " + second_tm.lowerKey(L));
			} else {
				sb.append("-1\n");
			}
		}
	}

	private static void solved(int P) {
//		System.out.println("solved " + P);
		ArrayList<Node> list = hm.get(P);

		for (Node n : list) {
			for (int idx = 0; idx < first_tm[n.G].get(n.L).size(); idx++) {
				if (first_tm[n.G].get(n.L).get(idx) == P) {
//					 System.out.println("first_tm[" + n.G + "].get(" + n.L +
//					 ").get(" + idx + ") = " + P + " 지움");
					first_tm[n.G].get(n.L).remove(idx);
					if (first_tm[n.G].get(n.L).isEmpty()) {
						first_tm[n.G].remove(n.L);
					}
					break;
				}
			}

			for (int idx = 0; idx < second_tm.get(n.L).size(); idx++) {
				if (second_tm.get(n.L).get(idx) == P) {
					// System.out.println("second_tm.get(" + n.L + ").get(" +
					// idx + ") = " + P + " 지움");

					second_tm.get(n.L).remove(idx);
					if (second_tm.get(n.L).isEmpty()) {
						second_tm.remove(n.L);
					}
					break;
				}
			}
		}
		hm.remove(P);
	}

	private static void init() {
		for (int i = 1; i <= 100; i++) {
			first_tm[i] = new TreeMap<>();
		}
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
