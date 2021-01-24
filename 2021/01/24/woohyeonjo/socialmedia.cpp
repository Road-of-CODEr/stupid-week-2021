#define MAX_USER 1001
#define MAX_FUNC_CALL 100001
#define nullptr 0

struct Post {
    Post* next;
    int pID, uID, like, timestamp;
} postList[MAX_FUNC_CALL];

int pidx = 0;

Post* malloc() { return &postList[++pidx]; }

int user;
bool visited[MAX_FUNC_CALL];
int heap[MAX_FUNC_CALL];
int heapSize;
int followMap[MAX_USER][MAX_USER];
Post board[MAX_USER];

bool compare(const Post* A, const Post* B, int curTime) {
    int timeA = curTime - A->timestamp;
    int timeB = curTime - B->timestamp;

    if(timeA <= 1000 && timeB <= 1000) {
        if(A->like == B->like) {
            return A->timestamp > B->timestamp ? true : false;
        } else {
            return A->like > B->like ? true : false;
        }
    } else if(timeA <= 1000 || timeB <= 1000) {
        return timeA <= 1000 ? true : false;
    } else {
        return A->timestamp > B->timestamp ? true : false;
    }
}

int heapPush(int value, int curTime)
{
    heap[heapSize] = value;

    int current = heapSize;
    while (current > 0 && compare(&postList[heap[current]], &postList[heap[(current - 1) / 2]], curTime))
    {
        int temp = heap[(current - 1) / 2];
        heap[(current - 1) / 2] = heap[current];
        heap[current] = temp;
        current = (current - 1) / 2;
    }

    heapSize = heapSize + 1;

    return 1;
}

int heapPop(int curTime)
{
    int res = heap[0];
    heapSize = heapSize - 1;

    heap[0] = heap[heapSize];
    heap[heapSize] = 0;

    int current = 0;
    while (current * 2 + 1 < heapSize)
    {
        int child;
        if (current * 2 + 2 == heapSize)
        {
            child = current * 2 + 1;
        }
        else
        {
            child = compare(&postList[heap[current * 2 + 1]], &postList[heap[current * 2 + 2]], curTime) ? current * 2 + 1 : current * 2 + 2;
        }

        if (compare(&postList[heap[current]], &postList[heap[child]], curTime))
        {
            break;
        }

        int temp = heap[current];
        heap[current] = heap[child];
        heap[child] = temp;

        current = child;
    }
    return res;
}

void init(int N)
{
    user = N;
    heapSize = 0;
    pidx = 0;
    for(int i = 1 ; i <= N ; ++i) {
        for(int j = 1 ; j <= N ; ++j) {
            if(i == j) followMap[i][j] = 1;
            else followMap[i][j] = 0;
        }
        board[i].next = nullptr;
    }

    for(int i = 0 ; i < MAX_FUNC_CALL ; ++i) visited[i] = false;
}

void follow(int uID1, int uID2, int timestamp)
{
    followMap[uID1][uID2] = 1;
}

void makePost(int uID, int pID, int timestamp)
{
    Post* newPost = malloc();
    Post* p = &board[uID];

    newPost->like = 0;
    newPost->pID = pID;
    newPost->uID = uID;
    newPost->timestamp = timestamp;

    while(p->next && compare(newPost, p->next, timestamp) == 1) p = p->next;

    newPost->next = p->next;
    p->next = newPost;
}

void like(int pID, int timestamp)
{
    postList[pID].like++;
}

void getFeed(int uID, int timestamp, int pIDList[])
{
    for(int i = 0 ; i <= user ; ++i) {
        if(followMap[uID][i]) {
            for(Post* now = &board[i] ; now ; now = now->next) {
                if(now->timestamp == 0 || visited[now->pID]) continue;
                heapPush(now->pID, timestamp);
                visited[now->pID] = true;
            }
        }
    }

    if(heapSize == 0) return;

    for(int i = 0 ; i < 10 ; ++i) {
        if(heapSize == 0) pIDList[i] = 0;
        else {
            pIDList[i] = heapPop(timestamp);
            visited[pIDList[i]] = false;
        }
    }

    while(heapSize != 0) visited[heapPop(timestamp)] = false;
}
