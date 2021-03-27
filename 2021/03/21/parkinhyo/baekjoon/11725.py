from sys import stdin
import collections


class Solution:
    def findParents(self, N: int, graph):
        queue = collections.deque()
        visited = [False] * 100001
        visited[1] = True
        parents = [0] * 100001

        # 루트 1을 뺴서 큐에 넣음
        while graph[1]:
            v = graph[1].pop(0)
            queue.append(v)

        # 큐에서 왼쪽부터 빼면서 그 값의 그래프 딸려 있는거 있나 보고 visited 체크해서 뺴면서 트리 만듬.
        while queue:
            u = queue.popleft()
            visited[u] = True
            while graph[u]:
                v = graph[u].pop(0)
                if not visited[v]:
                    parents[v] = u
                    queue.append(v)
        # 출력
        for i in range(2, N + 1):
            if parents[i] == 0:
                print(1)
            else:
                print(parents[i])


N = int(stdin.readline())
graph = collections.defaultdict(list)

# 입력 받은 값으로 그래프 양방향으로 생성
for i in range(N - 1):
    u, v = map(int, stdin.readline().split())
    graph[u].append(v)
    graph[v].append(u)
Solution().findParents(N, graph)