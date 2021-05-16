class Solution {
public:
int maxSumMinProduct(vector<int>& nums) {
	long long ans;
	vector<long long> dp(nums.size()+1), st;
	for (int i = 0; i < nums.size(); i++) {
		dp[i+1] = dp[i] + nums[i];
	}
	for (int i = 0; i <= nums.size(); i++) {
		while (!st.empty()&& (i ==nums.size() || nums[st.back()]>nums[i])) {
			int j = st.back();
			st.pop_back();
			ans = max(ans, nums[j] * (dp[i] - dp[st.empty() ? 0 : st.back() + 1]));
			//ans = max(ans, nums[j] * (dp[i-1] - st.empty()?0:dp[st.back()]));
		}
		st.push_back(i);
	}

	return ans% 1000000007;
}
};