package jungOl;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 5. 16.                   
public class 회전초밥_중 {
	
	private static class Node {
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (getClass() != obj.getClass()) return false;
			Node other = (Node) obj;
			if (name == null) {
				if (other.name != null) return false;
			} else if (!name.equals(other.name)) return false;
			return true;
		}

		int X, Y;
		String name;
		
		Node(int X, int Y, String name) {
			this.X = X;
			this.Y = Y;
			this.name = name;
		}
	}

	static int N, D, K, C, size = 0;
	static final int MOD = 100003, KEY = 5381;
	static int[] list, count;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = stoi(st.nextToken());
		D = stoi(st.nextToken());
		K = stoi(st.nextToken());
		C = stoi(st.nextToken());
		init();
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			list[i] = (stoi(st.nextToken()));
		}
		
		Node n1 = new Node(2, 3, "apple");
		Node n2 = new Node(1, 1, "apple");
		System.out.println(n1.hashCode() + " " + n2.hashCode());
		
		System.out.println("-----------------------");
		HashMap<String, String> hm1 = new HashMap<>();
		HashMap<String, String> hm2 = new HashMap<>();
		hm1.put("apple", "kerochuu");
		hm2.put("apple", "kerochuu");
		TreeMap<String, Integer> tm = new TreeMap<>();
		
		HashSet<String> hs = new HashSet<>();
		
		Iterator<String> it = hs.iterator();
		
		
		Integer temp1 = Integer.MAX_VALUE;
		Integer temp2 = Integer.MAX_VALUE;
		System.out.println(temp1 + " " + temp1.hashCode());
		System.out.println(temp2 + " " + temp2.hashCode());
		temp1.hashCode();
		System.out.println("hm1 = ");
		for(Entry e : hm1.entrySet()) {
			System.out.println(e.getKey() + " " + e.getValue() + " " + e.hashCode());
		}
		
		System.out.println("hm2 = ");
		for(Entry e : hm2.entrySet()) {
			System.out.println(e.getKey() + " " + e.getValue() + " " + e.hashCode());
		}
		
		

//		System.out.println(calc());
	}

	private static int calc() {
		int max = 0;
		for (int i = 0; i < N; i++) {
			if (i == 0) {
				add(C);
				for (int k = 0; k < K; k++) {
					add(list[k]);
				}
			} else {
				remove(list[i - 1]);
				add(list[(i + K - 1) % N]);
			}
			max = Math.max(max, size);
		}
		return max;
	}

	private static void add(int num) {
		if (count[num]++ == 0) size++;
	}

	private static void remove(int num) {
		if (--count[num] == 0) size--;
	}

	private static void init() {
		list = new int[N];
		count = new int[D + 1];
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
