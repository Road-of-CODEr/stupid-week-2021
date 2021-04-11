class comp {
public:
	bool operator()(pair<int, int> m, pair<int, int> n)
	{
		double x = m.first, y = m.second, w = n.first, z = n.second;
		return ((x + 1) / (y + 1) - x / y) < ((w + 1) / (z + 1) - w / z);
	}
};

class Solution {
public:
double maxAverageRatio(vector<vector<int>>& classes, int extraStudents) {


	priority_queue<pair<int, int>,vector<pair<int, int>>,comp> pq;
	for (int i = 0; i < classes.size(); i++) {
		pq.push({ classes[i][0],classes[i][1] });
	}
	for (int i = 0; i < extraStudents; i++) {
		int a = pq.top().first, b = pq.top().second;
		pq.pop();
		pq.push({ a + 1,b + 1 });
	}
	double sum = 0;
	while (!pq.empty()) {
		double a = pq.top().first, b = pq.top().second;
		sum += a / b;
		pq.pop();
	}
	return (sum)/classes.size();
}


};