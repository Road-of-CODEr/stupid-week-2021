class Solution {
public:
bool areAlmostEqual(string s1, string s2) {
	int cnt = 0;	
	for (int i = 0; i < s1.size(); i++) {
		if (s1[i] != s2[i])
			cnt++;
	}
	sort(s1.begin(), s1.end());
	sort(s2.begin(), s2.end());
	for (int i = 0; i < s1.size(); i++) {
		if (s1[i] != s2[i])
			return false;
	}

	return cnt > 2 ? false : true;
}
};