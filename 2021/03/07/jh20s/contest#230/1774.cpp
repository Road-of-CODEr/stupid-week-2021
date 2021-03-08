class Solution {
public:
int closestCost(vector<int>& baseCosts, vector<int>& toppingCosts, int target) {
	int dp[40005];
	memset(dp, 0, sizeof(dp));
	int ans = 0;
	for (int i = 0; i < baseCosts.size(); i++) {
		dp[baseCosts[i]] = 1;
	}
	for (int j = 0; j < toppingCosts.size(); j++) {
		for (int i = 20000; i >= 0; i--) {
			if(dp[i]==1){
				dp[i + toppingCosts[j]] = 1;
				dp[i + toppingCosts[j] * 2] = 1;
			}
		}
	}
	for (int i = 0; i <= 20000; i++) {
		if (target - i >= 0 && dp[target - i]) return target - i;
		if (target + i <= 40000 && dp[target + i]) return target + i;
	}
	return ans;
}
};