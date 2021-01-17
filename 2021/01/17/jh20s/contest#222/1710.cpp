class Solution {
public:
int maximumUnits(vector<vector<int>>& boxTypes, int truckSize) {

	int ans = 0;
	vector<pair<int, int>> v;

	for (int i = 0; i < boxTypes.size(); i++) {
		v.push_back({ boxTypes[i][1], boxTypes[i][0] });
	}
	sort(v.begin(), v.end(),greater<pair<int,int>>());

	for (int i = 0; i < v.size() && truckSize>0; i++) {
		int cnt = min(v[i].second, truckSize);
		ans += cnt * v[i].first;
		truckSize -= cnt;
    }
	return ans;
}
};