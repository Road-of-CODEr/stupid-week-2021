class Solution {
public:
int countGoodRectangles(vector<vector<int>>& rectangles) {
	map<int, int> m;
	for (int i = 0; i < rectangles.size(); i++) {
		int len = min(rectangles[i][0], rectangles[i][1]);
		m[len]++;
	}	
	return max_element(m.begin(), m.end(),m.value_comp())->second;
}
};