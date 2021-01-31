class Solution {
public:
int dp[1005][1005];
int kthLargestValue(vector<vector<int>>& matrix, int k) {
	dp[0][0] = matrix[0][0];
	vector<int> v;
	for (int i = 0; i < matrix[0].size(); i++) {
		dp[0][i] = matrix[0][i];
	}
	for (int i = 1; i < matrix.size(); i++) {
		for(int j=0;j<matrix[i].size();j++){
			dp[i][j] = dp[i-1][j] ^ matrix[i][j];
		}
	}
	for (int i = 0; i < matrix.size(); i++) {
		for (int j = 1; j < matrix[i].size(); j++) {
			dp[i][j] = dp[i][j] ^ dp[i][j - 1];
		}
	}
	for (int i = 0; i < matrix.size(); i++) {
		for (int j = 0; j < matrix[i].size(); j++) {
			v.push_back(dp[i][j]);
		}
	}
	sort(v.begin(), v.end(),greater<int>());
	return v[k-1];
}
};