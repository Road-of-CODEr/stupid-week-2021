package baekjoon;

import java.io.*;
import java.util.StringTokenizer;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 6. 17.                   
public class 숫자카드2 {
	private static class Node {
		int key, value;

		Node(int key, int value) {
			this.key = key;
			this.value = value;
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

		void findAndAdd(int num) {
			for (int i = 0; i < size; i++) {
				if (data[i].key == num) {
					data[i].value++;
					return;
				}
			}
			add(new Node(num, 1));
		}
	}

	private static class KeroMap {
		KeroList[] list;

		KeroMap() {
			list = new KeroList[MOD];
			for (int i = 0; i < MOD; i++) {
				list[i] = new KeroList();
			}
		}

		void put(int num) {
			list[hashCode(num)].findAndAdd(num);
		}

		int hashCode(int num) {
			int temp = Math.abs(num);
			int hash = KEY;
			while (temp != 0) {
				hash = mod(((hash << 5) + hash) + (temp % 10));
				temp /= 10;
			}
			return hash;
		}

		int mod(int hash) {
			return (hash %= MOD) >= 0 ? hash : hash + MOD;
		}

		int get(int num) {
			int hash = hashCode(num);
			for (int i = 0; i < list[hash].size; i++) {
				if (list[hash].get(i).key == num) {
					return list[hash].get(i).value;
				}
			}
			return 0;
		}
	}

	static int N, M;
	static int MOD = 100003, KEY = 5381;
	static KeroMap keroMap = new KeroMap();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = stoi(st.nextToken());

		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			keroMap.put(stoi(st.nextToken()));
		}

		st = new StringTokenizer(br.readLine());
		M = stoi(st.nextToken());

		StringBuilder sb = new StringBuilder();
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < M; i++) {
			sb.append(keroMap.get(stoi(st.nextToken())) + " ");
		}

		System.out.println(sb.toString());
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
