class Solution {
public:
string maxValue(string n, int x) {
	string ans;
	int g;
	if (n[0] == '-') {
		g = n.size();
		for (int i = 1; i <n.size(); i++) {
			if (n[i] - '0' > x) {
				g = i;
				break;
			}
		}
	}
	else {
		g = n.size();
		for (int i = 0; i <n.size(); i++) {
			if (n[i] - '0' < x) {
				g = i;
				break;
			}
		}
	}
	ans = n.substr(0, g);
	ans += x+'0';
	ans += n.substr(g);
	return ans;
}

};