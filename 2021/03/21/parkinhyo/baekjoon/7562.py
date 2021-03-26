from sys import stdin
import collections


class Solution:
    def moveKnight(self, l: int, startX: int, startY: int, endX: int, endY: int):
        count = 0
        x = [-2, -2, -1, -1, 1, 1, 2, 2]
        y = [-1, 1, -2, 2, -2, 2, -1, 1]
        queue = collections.deque([[startX, startY, count]])
        visited = [[False] * (l + 1) for tmp in range(l + 1)]

        while queue:
            popX, popY, count = queue.popleft()
            if popX == endX and popY == endY:
                return count
            if not visited[popY][popX]:
                visited[popY][popX] = True
                count += 1
                for i in range(8):
                    appendX = popX + y[i]
                    appendY = popY + x[i]
                    if 0 <= appendX < l and 0 <= appendY < l:
                        queue.append([appendX, appendY, count])


T = int(stdin.readline())
answers = []
for i in range(T):
    l = int(stdin.readline())
    startX, startY = map(int, stdin.readline().split())
    endX, endY = map(int, stdin.readline().split())
    answers.append(Solution().moveKnight(l, startX, startY, endX, endY))
for answer in answers:
    print(answer)