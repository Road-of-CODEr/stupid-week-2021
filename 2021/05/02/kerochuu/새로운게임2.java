package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2020. 5. 4.                   
public class 새로운게임2 {
	private static class Node {
		int idx, x, y;

		Node(int idx, int x, int y) {
			this.idx = idx;
			this.x = x;
			this.y = y;
		}
	}

	private static class Info {
		int x, y, type;

		Info(int x, int y, int type) {
			this.x = x;
			this.y = y;
			this.type = type;
		}
	}

	static int size, K;
	static int[] dx = { 0, 0, -1, 1 };
	static int[] dy = { 1, -1, 0, 0 };
	static LinkedList<Node>[][] list;
	static HashMap<Integer, Info> hm = new HashMap<>();
	static int[][] color;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		size = stoi(st.nextToken());
		K = stoi(st.nextToken());

		init();

		for (int i = 1; i <= size; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= size; j++) {
				color[i][j] = stoi(st.nextToken());
			}
		}

		for (int i = 1; i <= K; i++) {
			st = new StringTokenizer(br.readLine());
			int x = stoi(st.nextToken());
			int y = stoi(st.nextToken());
			int t = stoi(st.nextToken()) - 1;
			list[x][y].add(new Node(i, x, y));
			hm.put(i, new Info(x, y, t));
		}

		int time = 0;

		while (++time < 1000 && underFour()) {
			print();
		}

	}

	private static boolean underFour() {
		boolean flag = true;
		for (int idx = 1; idx <= K; idx++) {
			Info info = hm.get(idx);
			int nx = info.x + dx[info.type];
			int ny = info.y + dy[info.type];

			LinkedList<Node> temp = new LinkedList<>();
			switch (color[nx][ny]) {
			case 0:
				while (!list[info.x][info.y].isEmpty()) {
					temp.addLast(list[info.x][info.y].pollLast());
					if (temp.peekLast().idx == idx) {
						break;
					}
				}

				while (!temp.isEmpty()) {
					hm.put(temp.peek().idx, new Info(nx, ny, hm.get(temp.peek().idx).type));
					list[nx][ny].add(temp.pollFirst());
				}
				break;
			case 1:
				while (!list[info.x][info.y].isEmpty()) {
					temp.addLast(list[info.x][info.y].pollLast());
					if (temp.peekLast().idx == idx) {
						break;
					}
				}

				while (!temp.isEmpty()) {
					hm.put(temp.peekLast().idx, new Info(nx, ny, hm.get(temp.peekLast().idx).type));
					list[nx][ny].add(temp.pollLast());
				}
				break;
			case 2:

				break;
			}

			if (!list[nx][ny].isEmpty() && list[nx][ny].size() >= 4) {
				flag = false;
			}
		}
		return flag;
	}

	private static void print() {

		for (int i = 1; i <= size; i++) {
			for (int j = 1; j <= size; j++) {
				System.out.print(list[i][j].size() + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	private static void init() {
		list = new LinkedList[K + 2][K + 2];
		color = new int[size + 2][size + 2];
		for (int i = 0; i < size + 2; i++) {
			for (int j = 0; j < size + 2; j++) {
				color[i][j] = 2;
			}
		}

		for (int i = 0; i <= K + 1; i++) {
			for (int j = 0; j <= K + 1; j++) {
				list[i][j] = new LinkedList<>();
			}
		}
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
