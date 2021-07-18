package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 7. 15.                   
public class 날카로운눈 {

	private static class Node {
		long start, limit, plus;

		Node(long start, long limit, long plus) {
			this.start = start;
			this.limit = limit;
			this.plus = plus;
		}
	}

	static final long MAX = 2147483648L;
	static int size;
	static Node[] list;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;

		size = stoi(br.readLine());

		list = new Node[size];

		for (int i = 0; i < size; i++) {
			st = new StringTokenizer(br.readLine());
			list[i] = new Node(stoi(st.nextToken()), stoi(st.nextToken()), stoi(st.nextToken()));
		}

		long idx = parametricSearch();
		if (idx == MAX) {
			System.out.println("NOTHING");
		} else {
			System.out.println(idx + " " + (countNum(idx) - countNum(idx - 1)));
		}
	}

	private static long parametricSearch() {
		long left = 0;
		long right = MAX;
		while (left < right) {
			long mid = (left + right) >> 1;
			if (countNum(mid) % 2 == 0) {
				left = mid + 1;
			} else {
				right = mid;
			}
		}
		return left;
	}

	private static long countNum(long num) {
		long count = 0;
		for (int i = 0; i < size; i++) {
			if (list[i].start <= num) {
				count += (Math.min(num, list[i].limit) - list[i].start) / list[i].plus + 1;
			}
		}
		return count;
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}