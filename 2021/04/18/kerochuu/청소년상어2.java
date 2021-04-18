package baekjoon;

import java.io.*;
import java.util.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 4. 15.                   
public class 청소년상어2 {
	private static class Node {
		int x, y, num, dir;

		Node(int num, int dir) {
			this.num = num;
			this.dir = dir;
		}

		Node(int x, int y, int dir) {
			this.x = x;
			this.y = y;
			this.dir = dir;
		}
	}

	static int size = 4;
	static Node shark;
	static Node[][] map = new Node[4][4];

	static int[] dx = { -1, -1, 0, 1, 1, 1, 0, -1 };
	static int[] dy = { 0, -1, -1, -1, 0, 1, 1, 1 };
	static char[] dirChar = { '↑', '↖', '←', '↙', '↓', '↘', '→', '↗' };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		for (int i = 0; i < size; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < size; j++) {
				map[i][j] = new Node(stoi(st.nextToken()), stoi(st.nextToken()) - 1);
			}
		}

		shark = new Node(map[0][0].num, map[0][0].dir);
		map[0][0] = null;
		System.out.println(backTracking(0, 0, shark.dir, shark.num, map));
	}

	private static int backTracking(int sx, int sy, int dir, int allSum, Node[][] nowMap) {
//		print(sx, sy, nowMap);
		int tempSum = allSum;
		Node[][] nextMap = move(sx, sy, nowMap);

		for (int i = 1; i < size; i++) {
			int nx = sx + dx[dir] * i;
			int ny = sy + dy[dir] * i;
			if (isInside(nx, ny)) {
				if (nextMap[nx][ny] != null) {
					Node temp = nextMap[nx][ny];
					nextMap[nx][ny] = null;
					tempSum = Math.max(tempSum, backTracking(nx, ny, temp.dir, allSum + temp.num, nextMap));
					nextMap[nx][ny] = temp;
				}
			} else {
				return tempSum;
			}
		}
		return tempSum;
	}

	private static Node[][] move(int sx, int sy, Node[][] map) {
		Node[][] copyMap = new Node[size][size];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (map[i][j] != null) {
					copyMap[i][j] = new Node(map[i][j].num, map[i][j].dir);
				}
			}
		}

		for (int idx = 1; idx <= 16; idx++) {
			Node n = find(idx, copyMap);
			if (n != null) {
				for (int d = 0; d < 8; d++) {
					int nx = n.x + dx[(n.dir + d) % 8];
					int ny = n.y + dy[(n.dir + d) % 8];

					if (isInside(nx, ny) && (nx != sx || ny != sy)) {
						if (copyMap[nx][ny] != null) {
							Node temp = copyMap[nx][ny];
							copyMap[nx][ny] = new Node(copyMap[n.x][n.y].num, (n.dir + d) % 8);
							copyMap[n.x][n.y] = new Node(temp.num, temp.dir);
						} else {
							copyMap[nx][ny] = new Node(copyMap[n.x][n.y].num, (n.dir + d) % 8);
							copyMap[n.x][n.y] = null;
						}
						break;
					}
				}
//				print(sx, sy, copyMap);
			}
		}
		return copyMap;
	}

	private static Node find(int idx, Node[][] map) {

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (map[i][j] != null && map[i][j].num == idx) {
					return new Node(i, j, map[i][j].dir);
				}
			}
		}
		return null;
	}

	private static void print(int sx, int sy, Node[][] map) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i == sx && j == sy) {
					System.out.format("   ■    ");
				} else if (map[i][j] != null) {
					System.out.format("(%2d, %c) ", map[i][j].num, dirChar[map[i][j].dir]);
				} else {
					System.out.format("   □    ");
				}
			}
			System.out.println();
		}
		System.out.println("----------------");
	}

	private static boolean isInside(int nx, int ny) {
		return nx >= 0 && ny >= 0 && nx < size && ny < size;
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
