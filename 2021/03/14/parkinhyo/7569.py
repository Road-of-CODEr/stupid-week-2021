from typing import List
from collections import deque
from sys import stdin


class Solution:
    def tomato(self, M: int, N: int, H: int, graph: List[List[List[int]]]):
        queue = deque()
        count = 0
        answer = 0
        visited = [[[False] * (M + 1) for i in range(N + 1)] for j in range(H + 1)]
        # print(graph)
        tomato_complete = False
        for h in range(H):
            for n in range(N):
                if 0 not in graph[h][n]:
                    tomato_complete = True
        if tomato_complete:
            print(0)
            return

        for h in range(H):
            for n in range(N):
                for m in range(M):
                    if graph[h][n][m] == 1:
                        queue.append([h, n, m, count])
                    if graph[h][n][m] == -1:
                        visited[h][n][m] = True
        if not queue:
            print(-1)
            return

        while queue:
            h, n, m, count = queue.popleft()
            if not visited[h][n][m]:
                visited[h][n][m] = True
                answer = max(answer, count)
                count += 1
                if h - 1 >= 0:
                    if graph[h - 1][n][m] == 0:
                        graph[h - 1][n][m] = 1
                        queue.append([h - 1, n, m, count])
                if h + 1 < H:
                    if graph[h + 1][n][m] == 0:
                        graph[h + 1][n][m] = 1
                        queue.append([h + 1, n, m, count])
                if n - 1 >= 0:
                    if graph[h][n - 1][m] == 0:
                        graph[h][n - 1][m] = 1
                        queue.append([h, n - 1, m, count])
                if n + 1 < N:
                    if graph[h][n + 1][m] == 0:
                        graph[h][n + 1][m] = 1
                        queue.append([h, n + 1, m, count])
                if m - 1 >= 0:
                    if graph[h][n][m - 1] == 0:
                        graph[h][n][m - 1] = 1
                        queue.append([h, n, m - 1, count])
                if m + 1 < M:
                    if graph[h][n][m + 1] == 0:
                        graph[h][n][m + 1] = 1
                        queue.append([h, n, m + 1, count])

        tomato_false = False
        for h in range(H):
            for n in range(N):
                if 0 in graph[h][n]:
                    tomato_false = True
        if tomato_false:
            print(-1)
            return

        print(answer)


# [[[0, -1, 0, 0, 0], [-1, -1, 0, 1, 1], [0, 0, 0, 1, 1]]]
# [[[0, 0, 0, 0, 0], [0, 0, 0, 0, 0], [0, 0, 0, 0, 0]],
# [[0, 0, 0, 0, 0], [0, 0, 1, 0, 0], [0, 0, 0, 0, 0]]]
# Solution().tomato(4, 3, 2, [[[1,1,1,1],[1,1,1,1],[1,1,1,1]],[[1,1,1,1],[-1,-1,-1,-1],[1,1,1,-1]]])
graph = []
M, N, H = map(int, stdin.readline().split())
for h in range(H):
    tmp = []
    for n in range(N):
        tmp.append(list(map(int, stdin.readline().split())))
    graph.append(tmp)
Solution().tomato(M, N, H, graph)
