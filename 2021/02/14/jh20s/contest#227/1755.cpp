class Solution {
public:
int minAbsDifference(vector<int>& nums, int goal) {
	int ret = abs(goal);

	set<int> s1{ 0 }, s2{ 0 };
	for (int i = 0; i < nums.size() / 2; i++) {
		for (auto n : vector<int>(begin(s1), end(s1))){
			if (s1.insert(n + nums[i]).second)
				ret = min(ret, abs(goal - n - nums[i]));
			if (ret == 0) {
				return ret;
			}
		}
	}
	for (int i = nums.size() / 2; i < nums.size(); i++) {
		for(auto n : vector<int>(begin(s2),end(s2)))
			if (s2.insert(n + nums[i]).second) {
				auto it = s1.lower_bound(goal - n - nums[i]);
				if (it != end(s1))
					ret = min(ret, abs(goal - n - nums[i] - *it));
				if (it != begin(s1))
					ret = min(ret, abs(goal - n - nums[i] - *prev(it)));
				if (ret == 0)
					return ret;
			}
	}
	return ret;
}
};