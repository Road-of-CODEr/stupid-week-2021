package jungOl;

import java.io.*;
import java.util.StringTokenizer;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 5. 16.                   
public class 단어세기 {
	private static class Node implements Comparable<Node> {
		char[] key;
		int value;

		Node(char[] key, int value) {
			this.key = key;
			this.value = value;
		}

		Node(char[] key) {
			this.key = key;
		}

		@Override
		public int compareTo(Node o) {
			for (int i = 0; i < this.key.length && i < o.key.length; i++) {
				if (this.key[i] != o.key[i]) {
					return this.key[i] - o.key[i];
				}
			}
			return this.key.length - o.key.length;
		}
	}

	private static class KeroList {
		Node[] data;
		int size, capacity = 8;

		KeroList() {
			data = new Node[capacity];
			size = 0;
		}

		void init() {
			size = 0;
		}

		void reallocate() {
			Node[] temp = new Node[capacity <<= 1];
			for (int i = 0; i < size; i++) {
				temp[i] = data[i];
			}
			data = temp;
		}

		boolean add(char[] c) {
			if (size == capacity) {
				reallocate();
			}
			if (!isContain(c)) {
				data[size++] = new Node(c, 1);
				return true;
			} else {
				return false;
			}
		}

		boolean isContain(char[] c) {
			lp: for (int i = 0; i < size; i++) {
				if (data[i].key.length == c.length) {
					for (int j = 0; j < c.length; j++) {
						if (data[i].key[j] != c[j]) {
							continue lp;
						}
					}
					data[i].value++;
					return true;
				}
			}
			return false;
		}

		int getValue(char[] c) {
			lp2: for (int i = 0; i < size; i++) {
				if (data[i].key.length == c.length) {
					for (int j = 0; j < c.length; j++) {
						if (data[i].key[j] != c[j]) {
							continue lp2;
						}
					}
					return data[i].value;
				}
			}
			return -1;
		}

		char[] getKey(int idx) {
			return data[idx].key;
		}

		void swap(int a, int b) {
			Node temp = new Node(data[a].key, data[a].value);
			data[a] = new Node(data[b].key, data[b].value);
			data[b] = temp;
		}
	}

	private static class KeroMap {
		KeroList[] list;
		KeroList inputList;

		KeroMap() {
			list = new KeroList[MOD];
			for (int i = 0; i < MOD; i++) {
				list[i] = new KeroList();
			}
			inputList = new KeroList();
		}

		void init() {
			for (int i = 0; i < MOD; i++) {
				list[i].init();
			}
			inputList.init();
		}

		void put(char[] c) {
			int hash = hashCode(c);
			if (list[hash].add(c)) {
				inputList.add(c);
			}
		}

		int hashCode(char[] c) {
			int hash = KEY;
			for (int i = 0; i < c.length; i++) {
				hash = mod(((hash << 5) + 1) * c[i]);
			}
			return hash;
		}

		int mod(int num) {
			return num % MOD;
		}

		int getValue(char[] c) {
			int hash = hashCode(c);
			return list[hash].getValue(c);
		}

		void mergeSort(int start, int end) {
			if (start == end) {
				return;
			}
			int mid = (start + end) >> 1;
			mergeSort(start, mid);
			mergeSort(mid + 1, end);
			merge(start, mid, end);
		}

		void merge(int start, int mid, int end) {
			int l = start;
			int r = mid + 1;
			Node[] temp = new Node[end - start + 1];
			int idx = 0;

			while (l <= mid && r <= end) {
				Node L = inputList.data[l];
				Node R = inputList.data[r];

				if (L.compareTo(R) < 0) {
					temp[idx++] = inputList.data[l++];
				} else {
					temp[idx++] = inputList.data[r++];
				}
			}

			while (l <= mid) {
				temp[idx++] = inputList.data[l++];
			}

			while (r <= end) {
				temp[idx++] = inputList.data[r++];
			}

			for (idx = 0; idx < temp.length; idx++) {
				inputList.data[start + idx] = temp[idx];
			}
		}

	}

	static final int MOD = 100003, KEY = 5381;
	static KeroMap keroMap = new KeroMap();
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		do {
			st = new StringTokenizer(br.readLine());
		} while (input(st));

		System.out.print(sb.toString());
	}

	private static boolean input(StringTokenizer st) {
		keroMap.init();
		boolean flag = true;
		while (st.hasMoreTokens()) {
			char[] input = st.nextToken().toCharArray();
			if (flag) {
				if (isEnd(input)) {
					return false;
				}
				flag = false;
			}
			keroMap.put(input);
		}

		print();
		return true;
	}
	
	private static void print() {
		keroMap.mergeSort(0, keroMap.inputList.size - 1);

		for (int i = 0; i < keroMap.inputList.size; i++) {
			for (int j = 0; j < keroMap.inputList.data[i].key.length; j++) {
				sb.append(keroMap.inputList.data[i].key[j]);
			}
			sb.append(" : " + keroMap.getValue(keroMap.inputList.data[i].key) + "\n");
		}
	}

	private static boolean isEnd(char[] input) {
		if (input[0] == 'E' && input[1] == 'N' && input[2] == 'D') {
			return true;
		}
		return false;
	}
}
