class Solution {
public:
int maximumPopulation(vector<vector<int>>& logs) {
	vector<pair<int, int>> v;
	for (int i = 0; i < logs.size(); i++) {
		v.push_back({ logs[i][0],1 });
		v.push_back({ logs[i][1],0 });
	}
	sort(v.begin(), v.end());
	int maxCnt = 0, year = 0,cnt=0;
	for (int i = 0; i < v.size(); i++) {
		if (v[i].second == 1) {
			cnt++;
			if (maxCnt < cnt) {
				maxCnt = cnt;
				year = v[i].first;
			}
			else if (maxCnt == cnt && year > v[i].first) {
				year = v[i].first;
			}
		}
		else if (v[i].second == 0) {
			cnt--;
		}
	}
	return year;
}

};