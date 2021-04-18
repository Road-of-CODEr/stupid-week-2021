class Solution {
public:
    int maxAscendingSum(vector<int>& nums) {
        
        int sum = nums[0];
        int max_v = nums[0];
        
        for(int i=1; i< nums.size(); i++){
            if(nums[i]<=nums[i-1]){
                max_v = max(sum, max_v);
                sum = 0;
            }
            sum += nums[i];
        }
        
        return (sum>max_v)?sum:max_v;
        
    }
};