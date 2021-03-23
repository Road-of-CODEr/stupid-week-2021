package baekjoon;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 3. 21.                   
import java.io.*;
import java.util.*;

public class 북서풍2 {
	private static class Node implements Comparable<Node> {
		int x, y;

		Node(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(Node o) {
			if (this.x == o.x) {
				return this.y - o.y;
			}
			return this.x - o.x;
		}
	}

	private static class Tree {
		int tree[];
		int h;

		Tree() {
			h = (int) Math.ceil((Math.log(size) / Math.log(2)));
			tree = new int[(1 << (h + 1))];
		}

		int update(int node, int start, int end, int idx) {
			if (idx < start || idx > end) {
				return tree[node];
			}
			if (start == end) {
				return tree[node] += 1;
			}
			int m = (start + end) >> 1;
			return tree[node] = update(node << 1, start, m, idx) + update(node << 1 | 1, m + 1, end, idx);
		}

		long query(int node, int start, int end, int left, int right) {
			if (right < start || left > end) {
				return 0;
			}
			if (left <= start && end <= right) {
				return tree[node];
			}
			int m = (start + end) >> 1;
			return query(node << 1, start, m, left, right) + query(node << 1 | 1, m + 1, end, left, right);
		}

	}

	static int N, size = 0;
	static long result;
	static ArrayList<Node> list = new ArrayList<>();
	static Tree tree;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int tnum = stoi(st.nextToken());
		for (int t = 1; t <= tnum; t++) {
			st = new StringTokenizer(br.readLine());
			N = stoi(st.nextToken());
			result = 0;
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				list.add(new Node(stoi(st.nextToken()), stoi(st.nextToken())));
			} // 인풋 종료

			compression();
			tree = new Tree();
			for (int i = 0; i < list.size(); i++) {
				result += tree.query(1, 1, size, 1, list.get(i).y);
				tree.update(1, 1, size, list.get(i).y);
			}
			sb.append(result + "\n");
			list.clear();
		}
		System.out.println(sb.toString());
	}

	private static void compression() {
		list.sort((o1, o2) -> Integer.compare(o2.y, o1.y));

		size = 0;

		int prev = Integer.MIN_VALUE;
		for (int i = 0; i < list.size(); i++) {
			Node n = list.get(i);
			if (prev == n.y) {
				list.set(i, new Node(n.x, size));
			} else {
				prev = n.y;
				n.y = ++size;
				list.set(i, n);
			}
		}
		Collections.sort(list);
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}

}