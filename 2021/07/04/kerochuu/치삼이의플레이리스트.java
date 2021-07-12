package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 6. 30.                   
public class 치삼이의플레이리스트 {
	private static class Music {
		String name;
		int size, factor = 0;

		Music(String name, int size) {
			this.name = name;
			this.size = size;
		}
	}

	static int N, M, S, idx = 0, sec = 0;
	static StringBuilder sb = new StringBuilder();
	static ArrayList<Music> list = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = stoi(st.nextToken());
		M = stoi(st.nextToken());
		S = stoi(st.nextToken());

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			list.add(new Music(st.nextToken(), stoi(st.nextToken())));
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			order(st.nextToken(), st);
		}
		System.out.print(sb.toString());
	}

	private static void order(String order, StringTokenizer st) {
		switch (order) {
		case "L":
			L(stoi(st.nextToken()));
			break;
		case "AR":
			AR();
			break;
		case "R":
			R(st.nextToken());
			break;
		case "P":
			P(st.nextToken(), stoi(st.nextToken()));
			break;
		case "AE":
			AE();
			break;
		case "E":
			E(st.nextToken());
			break;
		}
	}

	private static void L(int T) {
		for (; idx < list.size();) {
			boolean flag = true;
			if (T > list.get(idx).size - sec) { // ??
				list.get(idx).factor += (idx + 1) * (list.get(idx).size - sec);
				T -= (list.get(idx).size - sec);
				sec = 0;
			} else {
				list.get(idx).factor += (idx + 1) * T;
				sec += T;
				T = 0;
			}

			if (list.get(idx).factor >= S) {
				flag = false;
				sb.append(list.get(idx).name + "\n");
				remove(idx);
			}
			if (T <= 0) {
				return;
			}
			if (flag) {
				idx++;
			}
		}
	}

	private static void AR() {
		int max = -1, tempIdx = -1;
		for (int i = list.size() - 1; i >= 0; i--) {
			if (max <= list.get(i).factor) {
				max = list.get(i).factor;
				tempIdx = i;
			}
		}
		if (tempIdx == -1) {
			sb.append("-1\n");
		} else {
			sb.append(list.get(tempIdx).name + "\n");
			remove(tempIdx);
		}
	}

	private static void R(String name) {
		for (int i = 0; i < list.size(); i++) {
			if (name.equals(list.get(i).name)) {
				sb.append(list.get(i).factor + "\n");
				remove(i);
				return;
			}
		}
		sb.append("-1\n");
	}

	private static void P(String name, int size) {
		list.add(0, new Music(name, size));
		idx = 0;
		sec = 0;
	}

	private static void AE() {
		int result = -1;
		for (int i = 0; i < list.size(); i++) {
			result = Math.max(list.get(i).factor, result);
		}
		sb.append(result + "\n");
	}

	private static void E(String name) {
		for (int i = 0; i < list.size(); i++) {
			if (name.equals(list.get(i).name)) {
				sb.append(list.get(i).factor + "\n");
				return;
			}
		}
		sb.append("-1\n");
	}

	private static void remove(int pick) {
		if (pick < idx) {
			idx--;
		} else if (pick == idx) {
			sec = 0;
		}
		list.remove(pick);
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
