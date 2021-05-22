package swea;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 5. 10.                   
import java.io.*;
import java.util.*;

public class 단순2진암호코드 {

	static int R, C;
	static int[][] table = new int[50][100];
	static int[] data = new int[8];
	static HashMap<Integer, Integer> hm = new HashMap<>();
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		preProcess();

		int tnum = stoi(st.nextToken());
		for (int t = 1; t <= tnum; t++) {
			boolean flag = true;
			st = new StringTokenizer(br.readLine());
			R = stoi(st.nextToken());
			C = stoi(st.nextToken());

			for (int i = 0; i < R; i++) {
				char[] temp = br.readLine().toCharArray();
				if (flag && isPassCode(temp) && isResult()) {
					sb.append("#" + t + " " + print() + "\n");
					flag = false;
				}
			}
			if (flag) {
				sb.append("#" + t + " 0" + "\n");
			}
		}
		System.out.println(sb.toString());
	}

	private static boolean isResult() {
		int allSum = 0;
		for (int i = 0; i < 8; i += 2) {
			allSum += data[i] * 3 + data[i + 1];
		}
		return allSum % 10 == 0;
	}

	private static int print() {
		int allSum = 0;
		for (int i = 0; i < 8; i++) {
			allSum += data[i];
		}
		return allSum;
	}

	private static boolean isPassCode(char[] temp) {
		int idx = findLastBit(temp);
		if (idx < 0) return false;

		for (int i = 0; i < 8; i++) {
			int num = convert(idx + i * 7, idx + i * 7 + 7, temp);
			if (hm.containsKey(num)) {
				data[i] = hm.get(num);
			} else {
				return false;
			}
		}
		return true;
	}

	private static int convert(int start, int end, char[] temp) {
		int allSum = 0;
		for (int i = start, pow = 6; i < end; i++, pow--) {
			if (temp[i] == '1') {
				allSum += 1 << pow;
			}
		}
		return allSum;
	}

	private static int findLastBit(char[] temp) {
		for (int i = temp.length - 1; i >= 56; i--) {
			if (temp[i] == '1') {
				return i - 55;
			}
		}
		return -1;
	}

	private static void preProcess() {
		hm.put(13, 0);
		hm.put(25, 1);
		hm.put(19, 2);
		hm.put(61, 3);
		hm.put(35, 4);
		hm.put(49, 5);
		hm.put(47, 6);
		hm.put(59, 7);
		hm.put(55, 8);
		hm.put(11, 9);
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
