class Solution {
public:
int maximumRemovals(string s, string p, vector<int>& removable) {
	vector<int> v(s.size(),s.size()*2);
	for (int i = 0; i < removable.size(); i++) {
		v[removable[i]] = i;
	}
	
	int l = 0, r = removable.size();
	while (l < r) {
		int mid = (l + r +1 ) / 2;
		int j = 0;
		for (int i = 0; i < s.size() && j<p.size(); i++) {
			if (v[i]>=mid && s[i] == p[j]) {
				j++;
			}
		}
		if (j == p.size()) {
			l = mid;
		}
		else {
			r = mid - 1;
		}
	}


	return r;
}
};