class Solution {
public:
int minOperations(string s) {
	int l = 0, r = 0;
	for (int i = 0; i < s.size(); i++) {
		if (i % 2) {
			l += ((s[i] == '0') ? 1 : 0);
			r += ((s[i] == '1') ? 1 : 0);
		}
		else {
			l += ((s[i] == '1') ? 1 : 0);
			r += ((s[i] == '0') ? 1 : 0);
		}
	}
	return min(l, r);
}
};