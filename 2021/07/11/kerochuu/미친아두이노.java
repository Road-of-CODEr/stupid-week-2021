import java.io.*;
import java.util.*;

//@author : blog.naver.com/kerochuu 
//@date : 2021. 7. 7.        
public class 미친아두이노 {
	private static class Node {
		int x, y;

		Node(int x, int y) {
			this.x = x;
			this.y = y;
		}

		void move(int dir) {
			this.x += dx[dir];
			this.y += dy[dir];
		}
	}

	static int R, C, now = 0;
	static int[][][] map;
	static Node js;
	static Queue<Node> q = new LinkedList<>();
	static int[] dx = { 1, 1, 1, 0, 0, 0, -1, -1, -1 };
	static int[] dy = { -1, 0, 1, -1, 0, 1, -1, 0, 1 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = stoi(st.nextToken());
		C = stoi(st.nextToken());

		init();

		for (int i = 0; i < R; i++) {
			char[] input = br.readLine().toCharArray();
			for (int j = 0; j < C; j++) {
				switch (input[j]) {
				case 'I':
					js = new Node(i, j);
					break;
				case 'R':
					map[i][j][now] = 1;
					q.add(new Node(i, j));
					break;
				}
			}
		}

		System.out.println(simulation(br.readLine().toCharArray()));
	}

	private static String simulation(char[] input) {
		for (int i = 0; i < input.length; i++, now = (now + 1) % 2) {
			js.move(input[i] - '1');
			if (loseTheGame()) {
				return "kraj " + (i + 1);
			}
		}
		return print();
	}

	private static boolean loseTheGame() {
		int size = q.size(), next = (now + 1) % 2;

		for (int s = 0; s < size; s++) {
			Node n = q.poll();

			if (map[n.x][n.y][now] == 1) {
				map[n.x][n.y][now] = 0;
				if (isEncounter(n))
					return true;
				map[n.x][n.y][next]++;
				q.add(n);
			} else if (map[n.x][n.y][now] > 1) {
				map[n.x][n.y][now] *= -1;
				map[n.x][n.y][now]++;
			} else {
				map[n.x][n.y][now]++;
			}
		}
		return false;
	}

	private static boolean isEncounter(Node n) {
		int dir = -1, length = Integer.MAX_VALUE;

		for (int i = 0; i < 9; i++) {
			int temp = Math.abs(js.x - (n.x + dx[i])) + Math.abs(js.y - (n.y + dy[i]));
			if (length > temp) {
				length = temp;
				dir = i;
			}
		}
		n.move(dir);
		return js.x == n.x && js.y == n.y;
	}

	private static String print() {
		StringBuilder sb = new StringBuilder();
		map[js.x][js.y][now] = -1;
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				switch (map[i][j][now]) {
				case -1:
					sb.append('I');
					break;
				case 1:
					sb.append('R');
					break;
				default:
					sb.append('.');
					break;
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	private static void init() {
		map = new int[R][C][2];
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
