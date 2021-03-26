from collections import deque
from typing import List
from sys import stdin


class Solution:
    def tomato(self, M: int, N: int, H: int, graph: List[List[List[int]]]):
        # 좌표 값
        dx = [-1, 0, 1, 0, 0, 0]
        dy = [0, 1, 0, -1, 0, 0]
        dz = [0, 0, 0, 0, -1, 1]
        queue = deque()
        for i in range(H):
            for j in range(N):
                for k in range(M):
                    if graph[i][j][k] == 1:
                        # 1일 때 익은거 큐에 추가
                        queue.append([i, j, k])
        while queue:
            z, x, y = queue.popleft()

            # 왼, 오, 위, 아래, z 2개 돌리기
            for i in range(6):
                nx = x + dx[i]
                ny = y + dy[i]
                nz = z + dz[i]

                if 0 <= nx < n and 0 <= ny < m and 0 <= nz < h:
                    # 0이면 기존 1에서 1을 +해서 큐에 append 나중에 결과는 1빼야됨 result-1
                    if graph[nz][nx][ny] == 0:
                        graph[nz][nx][ny] = graph[z][x][y] + 1
                        queue.append([nz, nx, ny])

        # 결과 확인
        z = 1
        result = -1
        for i in graph:
            for j in i:
                for k in j:
                    if k == 0:
                        z = 0
                    result = max(result, k)
        if z == 0:
            print(-1)
        elif result == 1:
            print(0)
        else:
            print(result - 1)


m, n, h = map(int, stdin.readline().split())
a = [[list(map(int, stdin.readline().split())) for i in range(n)] for depth in range(h)]
Solution().tomato(m, n, h, a)