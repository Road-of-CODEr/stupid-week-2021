class Solution {
public:
int minOperations(vector<int>& target, vector<int>& arr) {
	vector<int> v;
	map<int, int> m;
	for (int i = 0; i < target.size(); i++) {
		m[target[i]] = i+1;
	}
	for (int i = 0; i < arr.size(); i++) {
		if (m.find(arr[i]) == m.end())
			continue;
		int val = m[arr[i]];
		auto it = lower_bound(v.begin(), v.end(), val);
		if (it != v.end()) {
			v[it - v.begin()] = val;
		}
		else {
			v.push_back(val);
		}
	}
	return target.size() - v.size();
}
};