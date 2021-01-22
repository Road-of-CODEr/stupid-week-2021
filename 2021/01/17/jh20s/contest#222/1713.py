class Solution:
    def minOperations(self, target, arr):
        arr_dic = {a:i for i ,a in enumerate(target)}
        stack = []
        for i in arr:
            if i not in arr_dic:
                continue
            bi = bisect.bisect_left(stack,arr_dic[i])
            if bi == len(stack):
                stack.append(0)
            stack[bi] = arr_dic[i]

        return len(target) - len(stack)
        