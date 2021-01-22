class Solution:
    def waysToSplit(self, nums: List[int]) -> int:
        mod = 1000000007
        size, ans, j, k = len(nums),0,0,0
        nums = list(accumulate(nums))
        for i in range(size-2):
            while j <= i or (j <size -1 and nums[j] < nums[i]*2):
                j+=1
            while k<j or (k<size-1 and nums[k]-nums[i] <= nums[-1] -nums[k]):
                k +=1
            ans = (ans+k-j)% mod
        return ans
