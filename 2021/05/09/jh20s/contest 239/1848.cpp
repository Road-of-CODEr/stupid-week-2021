class Solution {
public:
int getMinDistance(vector<int>& nums, int target, int start) {
	int ans = 9999999;
	for (int i = 0; i < nums.size(); i++) {
		if (nums[i] == target) {
			ans = min(ans, abs(i - start));
		}
	}
	return ans;
}
};