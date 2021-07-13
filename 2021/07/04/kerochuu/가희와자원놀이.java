package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 7. 3.                   
public class 가희와자원놀이 {
	private static class Node {
		boolean isAcquire;
		int idx, target;

		Node(int idx, String type, int target) {
			this.idx = idx;
			this.isAcquire = type.equals("acquire");
			this.target = target;
		}
	}

	static int N, T, idx = 0;
	static int[] order;
	static Node[] draw;
	static String[] card;
	static HashSet<Integer> picked = new HashSet<>();
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = stoi(st.nextToken());
		T = stoi(st.nextToken());
		init();

		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < T; i++) {
			order[i] = stoi(st.nextToken()) - 1;
		}

		for (int i = 0; i < T; i++) {
			card[i] = br.readLine();
		}

		simulation();
		System.out.print(sb.toString());
	}

	private static void simulation() {
		for (int t = 0; t < T; t++) {
			if (draw[order[t]] == null) {
				String[] cardInfo = card[idx++].split(" ");
				if (cardInfo[1].equals("next")) {
					sb.append(cardInfo[0] + "\n");
					continue;
				} else {
					draw[order[t]] = new Node(stoi(cardInfo[0]), cardInfo[1], stoi(cardInfo[2]));
				}
			}

			sb.append(draw[order[t]].idx + "\n");
			
			if (draw[order[t]].isAcquire) {
				if (!picked.contains(draw[order[t]].target)) { // acquire 가능 할 때
					picked.add(draw[order[t]].target);
					draw[order[t]] = null;
				}
			} else { // release는 항상 가능함이 보장됨
				picked.remove(draw[order[t]].target);
				draw[order[t]] = null;
			}
		}
	}

	private static void init() {
		order = new int[T];
		card = new String[T];
		draw = new Node[N];
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
