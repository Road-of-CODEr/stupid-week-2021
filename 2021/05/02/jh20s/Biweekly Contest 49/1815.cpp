class Solution {
public:
int rev(int num) {
	int ret = 0;
	while (num) {
		ret = ret * 10 + num % 10;
		num /= 10;
	}
	return ret;
}

int countNicePairs(vector<int>& nums) {
	int ret = 0;
	vector<int> v;
	map<int, int> m;
	for (int i = 0; i < nums.size(); i++) {
		int f = nums[i] - rev(nums[i]);
		if(m.count(f))
			ret = (ret+m[f])%1000000007;
		m[f]++;
	}
	return ret;
}
};