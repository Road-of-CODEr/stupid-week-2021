class Solution {
public:

int longestPalindromeSubseq(string s, vector<vector<int>> &dp) {
    for (int len = 1; len <= s.size(); ++len) 
        for (auto i = 0; i + len <= s.size(); ++i) 
            dp[i][i + len] = s[i] == s[i + len - 1] ? 
                dp[i + 1][i + len - 1] + (len == 1 ? 1 : 2) : 
                    max(dp[i][i + len - 1],  dp[i + 1][i + len]);
    return dp[0][s.size()];
}    
int longestPalindrome(string w1, string w2) {
    int sz = w1.size() + w2.size(), res = 0;
    vector<vector<int>> dp(sz + 1, vector<int>(sz + 1));
    longestPalindromeSubseq(w1 + w2, dp);
    for (int i = 0; i < w1.size(); ++i)
        for (int j = w2.size() - 1; j >= 0; --j)
            if (w1[i] == w2[j]) {
                res = max(res, dp[i][w1.size() + j + 1]);
                break;
            }
    return res;
}
};