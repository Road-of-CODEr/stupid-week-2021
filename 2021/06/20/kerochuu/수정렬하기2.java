package baekjoon;

import java.io.*;
import java.util.StringTokenizer;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 6. 17.                   
public class 수정렬하기2 {

	private static class KeroHeap {
		int[] data;
		int size;

		KeroHeap() {
			data = new int[N + 1];
			data[0] = Integer.MIN_VALUE;
			size = 1;
		}
		
		boolean isEmpty() {
			return size() == 0;
		}

		int size() {
			return this.size - 1;
		}

		void add(int num) {
			data[size++] = num;
			downToUp();
		}

		int poll() {
			int result = data[1];
			data[1] = data[--size];
			upToDown();
			return result;
		}

		void downToUp() {
			int now = size - 1, next;
			while (data[now] < data[(next = now >> 1)]) {
				swap(now, next);
				now = next;
			}
		}

		void upToDown() {
			int now = 1, next;
			while ((next = now << 1) <= size()) {
				if (next + 1 <= size() && data[next] > data[next + 1]) {
					next++;
				}

				if (data[next] < data[now]) {
					swap(now, next);
					now = next;
				} else {
					return;
				}
			}
		}

		void swap(int a, int b) {
			int temp = data[a];
			data[a] = data[b];
			data[b] = temp;
		}
	}

	static int N;
	static KeroHeap keroHeap;
	static int[] data;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = stoi(st.nextToken());
		keroHeap = new KeroHeap();
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			keroHeap.add(stoi(st.nextToken()));
		}

		StringBuilder sb = new StringBuilder();
		while (!keroHeap.isEmpty()) {
			sb.append(keroHeap.poll() + "\n");
		}
		System.out.println(sb.toString());
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
