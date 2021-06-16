class Solution {
public:
bool mergeTriplets(vector<vector<int>>& triplets, vector<int>& target) {
	int check[3] = { 0, };

	for (int i = 0; i < triplets.size(); i++) {
		for (int j = 0; j < 3;j++) {
			int a = j, b = (j + 1) % 3, c = (j + 2) % 3;
			if (triplets[i][a] == target[a] && triplets[i][b] <= target[b] && triplets[i][c] <= target[c]) {
				check[a] = 1;
			}

		}
	}

	return check[0] == 1 && check[1] == 1 && check[2] == 1;
}
};