class Solution {
public:
vector<vector<int>> v;
int check[100005];
int cnt = 0;
void makeGraph(vector<vector<int>>& allowedSwaps) {
	for (int i = 0; i < allowedSwaps.size(); i++) {
		int a = allowedSwaps[i][0], b = allowedSwaps[i][1];
		v[a].push_back(b);
		v[b].push_back(a);
	}
}

void getCycle(int u, int color) {
	if (check[u] != -1) {
		return;
	}
	check[u] = color;
	for (int i = 0; i < v[u].size(); i++) {
		getCycle(v[u][i], color);
	}
	return;
}

int minimumHammingDistance(vector<int>& source, vector<int>& target, vector<vector<int>>& allowedSwaps) {
	int ans = source.size();
	v.resize(source.size());
	memset(check, -1, sizeof(check));
	makeGraph(allowedSwaps);
	
	for (int i = 0; i < source.size(); i++) {
		if (check[i] == -1) {
			getCycle(i,cnt);
			cnt++;
		}
	}
	vector<vector<int>> g1, g2;
	g1.resize(cnt);
	g2.resize(cnt);
	for (int i = 0; i < source.size(); i++) {
		g1[check[i]].push_back(source[i]);
		g2[check[i]].push_back(target[i]);
	}
	for (int i = 0; i < g1.size(); i++) {
		sort(g1[i].begin(), g1[i].end());
		sort(g2[i].begin(), g2[i].end());
		int a = 0, b = 0;
		while (a < g1[i].size() && b < g2[i].size()) {
			if (g1[i][a] == g2[i][b]) {
				a++, b++;
				ans--;
			}
			else if (g1[i][a] < g2[i][b]) {
				a++;
			}
			else if (g1[i][a] > g2[i][b]) {
				b++;
			}
		}
	}
	return ans;
}
};