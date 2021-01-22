class Solution:
    def countPairs(self, deliciousness: List[int]) -> int:
        ans = 0
        freq = defaultdict(int)
        for x in deliciousness:
            for k in range(22):
                ans += freq[2**k-x]
            freq[x] +=1
        return ans % 1_000_000_007