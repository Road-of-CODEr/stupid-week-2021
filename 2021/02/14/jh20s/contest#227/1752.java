class Solution {
    public boolean check(int[] nums) {
        boolean rot = false;
        int i;
        for( i=1; i<nums.length; ++i){
            if (nums[i]<nums[i-1]){
                if(rot){
                    return false;
                }
                rot = true;
            }
        }
        if(rot){
            return nums[0]>=nums[nums.length-1];
        }
        return true;
    }
}