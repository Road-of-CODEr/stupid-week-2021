class Solution {
public:
    int minElements(vector<int>& nums, int limit, int goal) {
        long long sum=0;
        for(int i=0;i<nums.size();i++){
            sum+=nums[i];
        }
        return abs((sum-goal)/abs(limit)) + ((sum-goal)%abs(limit)?1:0);
    }
};