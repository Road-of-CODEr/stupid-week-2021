class Solution {
public:
int minOperations(vector<int>& nums1, vector<int>& nums2) {
	if (nums1.size() * 6 < nums2.size() || nums2.size() * 6 < nums1.size())
		return -1;
	int sum1 = accumulate(begin(nums1), end(nums1), 0);
	int sum2 = accumulate(begin(nums2), end(nums2), 0);
	int diff = abs(sum1 - sum2);
	int cnt[7] = { 0, }, ans = 0;
	if (sum1 > sum2) {
		swap(nums1, nums2);
	}
	for (auto n : nums1)
		cnt[6 - n]++;
	for (auto n : nums2)
		cnt[n - 1]++;
	for (int i = 5; i > 0 && diff>0; i--) {
		int k = min(cnt[i], diff / i + (diff % i ? 1 : 0));
		ans += k;
		diff -= i * k;
	}
	return ans;
}
};