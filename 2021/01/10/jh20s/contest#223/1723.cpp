class Solution {
public:
int cal[12] = {};
int nowVal = 0;
int dfs(vector<int>& jobs, int k,int i, int gVal) {
	if (gVal >= nowVal) {
		return nowVal;
	}
	if (i == jobs.size()) {
		return nowVal = gVal;
	}
	unordered_set<int> uset;
	for (int j = 0; j < k; j++) {
		if (!uset.insert(cal[j]).second) {
			continue;
		}
		cal[j] += jobs[i];
		dfs(jobs, k, i + 1, max(gVal, cal[j]));
		cal[j] -= jobs[i];
	}
	return nowVal;
}
int minimumTimeRequired(vector<int>& jobs, int k) {
	
	sort(jobs.begin(), jobs.end(), greater<int>());
	for (int i = 0; i < jobs.size(); i++) {
		nowVal += jobs[i];
	}
	return dfs(jobs, k, 0, 0);
}
};