class Solution {
public:
int dp[2][9][9][9][9][1005];
int dr[4] = { 1,-1,0,0 };
int dc[4] = { 0,0,1,-1 };
int cj = 0;
int mj = 0;
int go(vector<string>& grid, int turned, int mr, int mc, int cr, int cc, int cnt) {
	int& ret = dp[turned][mr][mc][cr][cc][cnt];
	if (ret != -1) return ret;
	ret = 0;
	if (cnt >=70) {
		return 0;
	}
	//쥐의 차례
	if (turned == 0) {
		for (int i = 0; i < 4; i++) {
			for(int j=0; j<= mj;j++){
				int nr = mr + dr[i]*j, nc = mc + dc[i]*j;
				if (nr < 0 || nr >= grid.size() || nc < 0 || nc >= grid[0].size() || grid[nr][nc] == '#')
					break;
				if (nr == cr && nc == cc)
					continue;
				if (grid[nr][nc] == 'F')
					return ret = 1;
				if (go(grid, 1, nr, nc, cr, cc, cnt))
					return ret = 1;
			}
		}
		return ret = 0;
	}
	//고양이 차례
	else if (turned == 1) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j <= cj; j++) {
				int nr = cr + dr[i] * j, nc = cc + dc[i] * j;
				if (nr < 0 || nr >= grid.size() || nc < 0 || nc >= grid[0].size() || grid[nr][nc] == '#')
					break;
				if ((nr == mr && nc == mc) || (grid[nr][nc] == 'F'))
					return ret = 0;
				if (!go(grid, 0, mr, mc, nr, nc, cnt + 1))
					return ret = 0;
			}
		}
		return ret = 1;
	}
	return 0;
}

bool canMouseWin(vector<string>& grid, int catJump, int mouseJump) {
	cj = catJump;
	mj = mouseJump;
	memset(dp, -1, sizeof(dp));
	int cr, cc, mr, mc;
	for (int i = 0; i < grid.size(); i++) {
		for (int j = 0; j < grid[i].size(); j++) {
			if (grid[i][j] == 'M'){
				mr = i, mc = j;
				grid[i][j] = '.';
			}
			if (grid[i][j] == 'C') {
				cr = i, cc = j;
				grid[i][j] = '.';
			}
		}
	}
	return go(grid,0,mr,mc,cr,cc,1);
}
};