package jungOl;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 5. 15.                   
public class Uniqueness {
	private static class Node implements Comparable<Node> {
		String name;
		ArrayList<Integer> list;

		Node(String name, ArrayList<Integer> list) {
			this.name = name;
			this.list = list;
		}

		@Override
		public int compareTo(Node o) {
			return this.list.get(0) - o.list.get(0);
		}
	}

	static ArrayList<String> list = new ArrayList<>();
	static ArrayList<Node> result = new ArrayList<>();
	static HashMap<String, ArrayList<Integer>> hm = new HashMap<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int size = stoi(st.nextToken());

		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= size; i++) {
			input(st.nextToken(), i);
		}

		System.out.print(print());
	}

	private static void input(String name, int idx) {
		if (!hm.containsKey(name)) {
			hm.put(name, new ArrayList<>());
		} else if (hm.get(name).size() == 1) {
			list.add(name);
		}
		hm.get(name).add(idx);
	}

	private static String print() {
		StringBuilder sb = new StringBuilder();
		if (list.isEmpty()) {
			sb.append("unique\n");
		} else {
			calc();
			for (Node n : result) {
				sb.append(n.name + " ");
				for (int idx : n.list) {
					sb.append(idx + " ");
				}
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	private static void calc() {
		for (String name : list) {
			result.add(new Node(name, hm.get(name)));
		}
		Collections.sort(result);
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
