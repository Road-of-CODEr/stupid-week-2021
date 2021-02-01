class Solution {
public:
int go1(string a, string b) {
	int ans = INT_MAX;
	for (int i = 1; i <= 'z'-'a'; i++) {
		int cnt = 0;
		for (int j = 0; j < a.size(); j++) {
			if (a[j] - 'a' >= i)
				cnt++;
		}
		for (int j = 0; j < b.size(); j++) {
			if (b[j] - 'a' < i)
				cnt++;
		}
		ans = min(ans, cnt);
	}
	return ans;
}

int go2(string a, string b) {
	int ans = INT_MAX;
	for (int i = 0; i <= 'z' - 'a'; i++) {
		int cnt = 0;
		for (int j = 0; j < a.size(); j++) {
			if (a[j] - 'a' != i)
				cnt++;
		}
		for (int j = 0; j < b.size(); j++) {
			if (b[j] - 'a' != i)
				cnt++;
		}
		ans = min(ans, cnt);
	}
	return ans;
}

int minCharacters(string a, string b) {
	return min(go2(a, b), min(go1(a, b), go1(b, a)));
}
};