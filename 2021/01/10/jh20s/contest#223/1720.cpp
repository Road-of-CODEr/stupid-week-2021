class Solution {
public:
vector<int> decode(vector<int>& encoded, int first) {
	vector<int> ans;
	ans.push_back(first);
	for (int i = 0; i < encoded.size();i++) {
		ans.push_back(first ^ encoded[i]);
		first = first ^ encoded[i];
	}
	return ans;
}
};