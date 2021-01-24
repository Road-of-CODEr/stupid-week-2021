package baekjoon;

import java.io.*;
import java.util.StringTokenizer;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 1. 23.                   
public class 나는야포켓몬마스터 {

	private static class Node {
		int num;
		String name;

		Node(int num, String name) {
			this.num = num;
			this.name = name;
		}
	}

	private static class KeroList {
		Node[] data;
		int size, capacity = 8;

		KeroList() {
			data = new Node[capacity];
			size = 0;
		}

		void reallocate() {
			Node[] temp = new Node[capacity <<= 1];
			for (int i = 0; i < size; i++) {
				temp[i] = data[i];
			}
			data = temp;
		}

		void add(Node n) {
			if (size == capacity) {
				reallocate();
			}
			data[size++] = n;
		}

		Node get(int idx) {
			return data[idx];
		}
	}

	private static class KeroMap {
		KeroList[] list;
		boolean isNameToNum;
		int size = 34567;

		KeroMap(boolean isNameToNum) {
			list = new KeroList[size];
			for (int i = 0; i < size; i++) {
				list[i] = new KeroList();
			}
			this.isNameToNum = isNameToNum;
		}

		void put(Node n) {
			int hash = hashCode(n);
			list[hash].add(n);
		}

		String getNumToString(int num) {
			for (int i = 0; i < list[mod(num)].size; i++) {
				if (list[mod(num)].data[i].num == num) {
					return list[mod(num)].data[i].name;
				}
			}
			return "";
		}

		int getStringToNum(String name) {
			int hash = hashCode(name);
			for (int i = 0; i < list[hash].size; i++) {
				if (list[hash].data[i].name.equals(name)) {
					return list[hash].data[i].num;
				}
			}
			return -1;
		}

		int hashCode(Node n) {
			int key = 1;
			int hash = 0;
			if (isNameToNum) {
				for (int i = 0; i < n.name.length(); i++) {
					hash = mod(hash + key * n.name.charAt(i));
					key = mod((key << 1) * 31);
				}
			} else {
				hash = mod(n.num);
			}
			return hash;
		}

		int hashCode(String name) {
			int key = 1;
			int hash = 0;
			for (int i = 0; i < name.length(); i++) {
				hash = mod(hash + key * name.charAt(i));
				key = mod((key << 1) * 31);
			}
			return hash;
		}

		int mod(int hash) {
			return hash % size;
		}
	}

	static KeroMap nameToNum = new KeroMap(true);
	static KeroMap numToName = new KeroMap(false);
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = stoi(st.nextToken());
		int M = stoi(st.nextToken());

		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			input(new Node(i, st.nextToken()));
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			sb.append(query(st.nextToken()) + "\n");
		}
		System.out.print(sb.toString());
	}

	private static void input(Node n) {
		nameToNum.put(n);
		numToName.put(n);
	}

	private static String query(String s) {
		if (s.charAt(0) >= '0' && s.charAt(0) <= '9') {
			return numToName.getNumToString(stoi(s));
		}
		return nameToNum.getStringToNum(s) + "";
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
