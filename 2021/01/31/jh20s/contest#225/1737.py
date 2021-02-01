class Solution(object):
    def go1(self,a,b):
        ans = sys.maxint
        A = [ord(i)-ord('a') for i in a]
        B = [ord(i)-ord('a') for i in b]
        for i in range(1,26):
            cnt = 0
            for j in A:
                if j >=i:
                    cnt+=1
            for j in B:
                if j < i:
                    cnt+=1
            ans = min(ans,cnt)
        return ans

    def go2(self,a,b):
        ans = sys.maxint
        A = [ord(i)-ord('a') for i in a]
        B = [ord(i)-ord('a') for i in b]
        for i in range(0,26):
            cnt =0
            for j in A:
                if j!= i:
                    cnt +=1
            for j in B:
                if j!= i:
                    cnt +=1
            ans = min(ans,cnt)
        return ans

    def minCharacters(self, a, b):
        return min(self.go2(a,b),min(self.go1(a,b),self.go1(b,a)))