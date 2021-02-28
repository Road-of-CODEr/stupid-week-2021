class Solution {
public:
int dp[1005][1005];
int go(int l,int r, int cnt, vector<int>& nums, vector<int>& mul) {
	if (cnt == mul.size()) 
		return 0;
	
	int& ret = dp[l][r];
	if (ret != -1)
		return ret;
	ret = max(go(l+1,r,cnt + 1,nums,mul)+nums[l]*mul[cnt],
		go(l, r + 1, cnt + 1, nums, mul) + nums[nums.size() - 1 - r] * mul[cnt]);
	return ret;
}

int maximumScore(vector<int>& nums, vector<int>& multipliers) {
	memset(dp, -1, sizeof(dp));
	return go(0,0,0,nums, multipliers);
}
};