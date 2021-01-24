class Solution {
public:
int mod = 1000000007;
int sum[100005];
int waysToSplit(vector<int>& nums) {
	long long ans = 0;
	sum[0] = nums[0];
	for (int i = 1; i < nums.size(); i++) {
		sum[i] = sum[i - 1] + nums[i];
	}

	for (int i = 0, j = 0, k = 0; i < nums.size() - 2; i++) {
		while (j <= i || (j < nums.size() - 1 && sum[j] < sum[i] * 2))
			j++;
		while (k < j || (k < nums.size() - 1 && sum[k] - sum[i] <= sum[nums.size() - 1] - sum[k]))
			k++;
		ans = (ans + (k - j)) % mod;
	}

	return ans;
}
};