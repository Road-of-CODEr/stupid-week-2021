class Solution {
public:
    vector<bool> canEat(vector<int>& candiesCount, vector<vector<int>>& queries) {
        
        long long i, type, day, atmost, n = candiesCount.size(), q = queries.size();
        
        vector<long long>sum(n);
        vector<bool>ans;
        
        sum[0] = candiesCount[0];
        for(i = 1; i < n; i++)
            sum[i] = sum[i-1] + candiesCount[i];
        
        for(i = 0; i < q; i++)
        {
            type = queries[i][0];
            day = queries[i][1];
            atmost = queries[i][2];
            
			
            if((day + 1) * atmost > (type == 0? 0:sum[type-1]) &&  sum[type] >= (day+1) * 1)
                ans.push_back(1);
            else
                ans.push_back(0);

        }
        
        return ans;
    }
};