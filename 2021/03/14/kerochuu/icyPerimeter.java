package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 3. 14.                   
public class icyPerimeter {

	private static class Node {
		int x, y;

		Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	static int N, SIZE = 0;
	static char[][] map = new char[1002][1002];
	static int[][] region = new int[1002][1002];
	static int[] area = new int[1000000], perimeter = new int[1000000];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = stoi(st.nextToken());
		char[] data;
		for (int i = 0; i < N + 2; i++)
			map[0][i] = map[N + 1][i] = '.';
		for (int i = 1; i <= N; i++) {
			map[i][0] = map[i][N + 1] = '.';
			data = br.readLine().toCharArray();
			for (int j = 1; j <= N; j++)
				map[i][j] = data[j - 1];
		}

		for (int i = 1; i <= N; i++)
			for (int j = 1; j <= N; j++)
				if (map[i][j] == '#' && region[i][j] == 0) visit(i, j, ++SIZE);

		find_perimeters();

		int best_a = 0, best_p = 0;
		for (int i = 1; i <= SIZE; i++)
			if (area[i] > best_a || (area[i] == best_a && perimeter[i] < best_p)) {
				best_a = area[i];
				best_p = perimeter[i];
			}

		System.out.println(best_a + " " + best_p);
	}

	private static void visit(int i, int j, int r) {
		Stack<Node> to_visit = new Stack<>();
		to_visit.push(new Node(i, j));
		while (!to_visit.empty()) {
			Node n = to_visit.pop();
			if (region[n.x][n.y] != 0 || map[n.x][n.y] == '.') continue;
			region[n.x][n.y] = SIZE;
			area[SIZE]++;
			to_visit.push(new Node(n.x - 1, n.y));
			to_visit.push(new Node(n.x + 1, n.y));
			to_visit.push(new Node(n.x, n.y - 1));
			to_visit.push(new Node(n.x, n.y + 1));
		}
	}

	private static void find_perimeters() {
		for (int i = 1; i <= N; i++)
			for (int j = 1; j <= N; j++) {
				int r = region[i][j];
				if (r == 0) continue;
				if (region[i - 1][j] == 0) perimeter[r]++;
				if (region[i + 1][j] == 0) perimeter[r]++;
				if (region[i][j - 1] == 0) perimeter[r]++;
				if (region[i][j + 1] == 0) perimeter[r]++;
			}
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
