#include <stdio.h>

#define MAX_N 50001
#define MAX_M 500001
#define INF 10000000

struct Node {
    Node* prev;
    int to, cost;
} m[MAX_M * 2];

struct Heap {
    int size;
    Node *memory[MAX_M];

    void init() { size = 0; }

    int isEmpty() { return size == 0 ? true : false; }

    void push(Node* node) {
        memory[size] = node;

        int cur = size;

        while (cur > 0 && memory[cur]->cost < memory[(cur - 1) / 2]->cost)
        {
            Node *temp = memory[(cur - 1) / 2];
            memory[(cur - 1) / 2] = memory[cur];
            memory[cur] = temp;
            cur = (cur - 1) / 2;
        }

        size++;
    }

    Node* pop() {
        Node* res = memory[0];
        memory[0] = memory[--size];

        int cur = 0;
        while(cur * 2 + 1 < size) {
            int child;

            if(cur * 2 + 2 == size) {
                child = cur * 2 + 1;
            } else {
                child = memory[cur * 2 + 1]->cost < memory[cur * 2 + 2]->cost ? cur * 2 + 1 : cur * 2 + 2;
            }

            if(memory[cur] < memory[child]) break;

            Node *temp = memory[cur];
            memory[cur] = memory[child];
            memory[child] = temp;

            cur = child;
        }

        return res;
    }

} pq;

int N, M, T, X, ans;
int midx;
int dist[2][MAX_N];
Node *adj[2][MAX_N];

Node *alloc() { return &m[midx++]; }

void resetDist() {
    for(int i = 0 ; i < MAX_N ; ++i) {
        dist[0][i] = INF;
        dist[1][i] = INF;
    }
    dist[0][X] = 0;
    dist[1][X] = 0;
}

void init() {
    pq.init();
    midx = 0;
    ans = 0;

    for(int i = 0 ; i < MAX_N ; ++i) {
        adj[0][i] = 0;
        adj[1][i] = 0;
    }
}

void dijkstra(int i) {
    while(!pq.isEmpty()) {
        Node* cur = pq.pop();

        for(Node* pp = adj[i][cur->to] ; pp != 0 ; pp = pp->prev) {
            if(dist[i][pp->to] > pp->cost + dist[i][cur->to]) {
                dist[i][pp->to] = pp->cost + dist[i][cur->to];
                pq.push(pp);
            }
        }

    }
}


int main() {
    scanf("%d", &T);

    for(int t = 1 ; t <= T ; ++t) {
        init();

        scanf("%d %d %d", &N, &M, &X);

        int s, e, c;
        for(int i = 0 ; i < M ; ++i){
            scanf("%d %d %d", &s, &e, &c);

            Node* node = alloc();
            node->to = e;
            node->cost = c;

            node->prev = adj[1][s];
            adj[1][s] = node;

            Node* node2 = alloc();
            node2->to = s;
            node2->cost = c;

            node2->prev = adj[0][e];
            adj[0][e] = node2;
        }

        resetDist();

        for(int i = 0 ; i < 2 ; ++i) {
            Node* node = alloc();
            node->to = X;
            node->cost = 0;

            pq.push(node);

            dijkstra(i);

        }

        for(int j = 1 ; j <= N ; ++j) {
            ans = dist[0][j] + dist[1][j] > ans ? dist[0][j] + dist[1][j] : ans;
        }

        printf("#%d %d\n", t, ans);
    }
}
