class Solution {
public:
int longestBeautifulSubstring(string word) {
	int cnt = 0;
	int ans = 0;
	int now = 0;	
	vector<char> v = { 'a','e','i','o','u','k' };

	int i = 0;
	while (i<word.size()) {
		if ((word[i] == v[now]) || (cnt!=0 && word[i] == v[now + 1])) {
			cnt++;
			if (word[i] == v[now + 1])
				now++;
			if (now == 4) {
				ans = max(ans, cnt);
			}
		}
		else {
			if (word[i] == 'a') i--;
			cnt = 0;
			now = 0;
		}
		i++;
	}

	return ans;
}
};