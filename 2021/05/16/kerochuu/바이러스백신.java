package jungOl;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 5. 10.                   
import java.io.*;
import java.util.*;

public class 바이러스백신 {

	private static class Node {
		int[] num;
		int count;

		Node(int[] list, int start) {
			num = new int[K];
			for (int i = start, idx = 0; i < start + K; i++, idx++) {
				num[idx] = list[i];
			}
			this.count = 0;
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

		void add(int idx, int[] list, int start, int dest) {
			for (int i = 0; i < size; i++) {
				boolean isSame = true;

				for (int k = 0; k < K; k++) {
					if (data[i].num[k] != list[start + k]) {
						isSame = false;
					}
				}
				if (isSame) {
					int[] temp = new int[K];
					for (int t = start; t < start + K; t++) {
						temp[t - start] = list[t];
					}
					if (data[i].count + 1 == idx) {
						data[i].count = idx;
					}
					return;
				}
			}
			if (idx == 0) {
				if (size == capacity) reallocate();
				data[size++] = new Node(list, start);
			}
		}

		void remove(int idx) {
			data[idx] = data[--size];
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

		void divideAndPut(int idx, int[] list) {
			int key = 1;
			int hash = 0;

			for (int i = 0; i < list.length - K; i++) {
				if (i == 0) {
					for (int j = 0; j < K; j++) {
						hash = mod(hash + mod(list[K - 1 - j] * key));
						if (j != K - 1) {
							key = mod((key * KEY));
						}
					}
				} else {
					hash = mod(KEY * (hash - mod(list[i - 1] * key)) + list[i + K - 1]);
				}
				this.list[hash].add(idx, list, i, K);
			}
		}

		boolean isVirus() {
			for (int i = 0; i < MOD; i++) {
				for (int j = 0; j < list[i].size; j++) {
					if (list[i].data[j].count == N - 1) {
						return true;
					}
				}
			}
			return false;
		}

		// list의 크기가 작을수록 searching 속도가 빨라짐
		boolean reallocate(int idx) {
			for (int i = 0; i < MOD; i++) {
				for (int j = 0; j < list[i].size; j++) {
					if (list[i].data[j].count != idx) {
						list[i].remove(j--);
					}
				}
			}
			return false;
		}

		int mod(int num) {
			return (num %= MOD) < 0 ? num + MOD : num;
		}
	}

	static int N, K, MOD = 10007, KEY = 53;
	static KeroMap keroMap = new KeroMap();

	static ArrayList<int[]> list = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = stoi(st.nextToken());
		K = stoi(st.nextToken());

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int size = stoi(st.nextToken());

			int[] temp = new int[size];
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < size; j++) {
				temp[j] = stoi(st.nextToken());
			}
			list.add(temp);
		}

		preProcess();

		System.out.println(keroMap.isVirus() ? "YES" : "NO");
	}

	private static void preProcess() {
		// 길이가 짧은 애들을 먼저 hashMap에 넣는게 유리함
		Collections.sort(list, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1.length - o2.length;
			}
		});

		for (int i = 0; i < N; i++) {
			input(i, list.get(i).length, list.get(i));
			if (i != 0) keroMap.reallocate(i);
		}
	}

	private static void input(int idx, int size, int[] data) {
		if (K > size) {
			return;
		}

		keroMap.divideAndPut(idx, data);
		int[] temp = new int[size];

		for (int i = 0; i < size; i++) {
			temp[i] = data[size - i - 1];
		}

		keroMap.divideAndPut(idx, temp);
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}

}