class Solution {
public:
int arr[405][405];
int cnt[405];
int minTrioDegree(int n, vector<vector<int>>& edges) {
	int ans = 999999999;
	for (int i = 0; i < edges.size(); i++) {
		int u = edges[i][0], v = edges[i][1];
		arr[u][v] = 1;
		arr[v][u] = 1;
		cnt[u]++;
		cnt[v]++;
	}
	for (int i = 1; i <= n; i++) {
		for (int j = i+1; j <= n; j++) {
			if(arr[i][j] ==1){
				for (int k = 1; k <= n; k++) {
					if (arr[k][i] == 1 && arr[k][j] == 1) {
						ans = min(ans, cnt[i] + cnt[j] + cnt[k] - 6);
					}
				}
			}
		}
	}
	return ans == 999999999 ? -1 : ans;
}
};