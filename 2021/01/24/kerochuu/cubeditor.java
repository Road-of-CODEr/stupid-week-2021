package baekjoon;

import java.io.*;

//  @author : blog.naver.com/kerochuu 
//  @date : 2021. 1. 24.                   
public class cubeditor {

	static int result = -1;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = br.readLine();

		for (int i = 0; i < input.length(); i++) {
			result = Math.max(result, kmp(input.substring(i, input.length())));
		}

		System.out.println(result);
	}

	private static int kmp(String s) {
		int j = 0, max = 0;
		int pi[] = new int[s.length()];

		for (int i = 1; i < s.length(); i++) {
			while (j > 0 && s.charAt(i) != s.charAt(j)) {
				j = pi[j - 1];
			}

			if (s.charAt(i) == s.charAt(j)) {
				max = Math.max(max, pi[i] = ++j);
			}
		}
		return max;
	}
}
