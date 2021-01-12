// main.cpp
#ifndef _CRT_SECURE_NO_WARNINGS
#define _CRT_SECURE_NO_WARNINGS
#endif

#include <stdio.h>

extern void init();
extern void insert(int data);
extern int remove(int index);
extern int searchByIndex(int index);

#define INSERT	100
#define REMOVE	200
#define SBI		300

////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

int main() {
    setbuf(stdout, NULL);
    int T;
    scanf("%d", &T);
    int N;
    int cmd, ret;
    int idx, data;
    for (int TC = 1; TC <= T; TC++) {

        init();

        int score = 100;

        scanf("%d", &N);

        for (int i = 0; i < N; i++) {
            scanf("%d", &cmd);

            switch (cmd) {

            case INSERT:
                scanf("%d", &data);
                insert(data);

                break;

            case REMOVE:
                scanf("%d %d", &idx, &data);

                ret = remove(idx);

                if (ret != data) {
                    score = 0;
                }
                break;

            case SBI:
                scanf("%d %d", &idx, &data);

                ret = searchByIndex(idx);

                if (ret != data) {
                    score = 0;
                }
                break;
            }
        }

        printf("#%d %d\n", TC, score);
    }

    return 0;
}


// solution.cpp
#define INPUT_MAX 100001
#define BUCKET_MAX 1001
#define DIVIDER 1000
#define nullptr 0

struct Node {
	Node* next;
	int data;
};

Node memory[INPUT_MAX];
Node head[BUCKET_MAX];
int midx;

Node* malloc() {
	return &memory[midx++];
}

void init() {
	for (register int i = 0; i < INPUT_MAX; ++i) {
		memory[i].next = nullptr;
	}

	for (register int i = 0; i < BUCKET_MAX; ++i) {
		head[i].next = nullptr;
		head[i].data = 0;
	}

	midx = 0;
}

void insert(int data) {
	int key = data / DIVIDER;

	register Node* newNode = malloc();
	register Node* p = &head[key];

	newNode->data = data;
	p->data++;

	while (p->next && data > p->next->data) p = p->next;

	newNode->next = p->next;
	p->next = newNode;
}

int remove(int index) {
	int cnt = 0;
	int key = 0;
	int res = 0;

	while (index >= cnt + head[key].data) cnt += head[key++].data;

	register Node* p = &head[key];
	p->data--;

	index -= cnt;
	while (index--) p = p->next;

	res = p->next->data;
	p->next = p->next->next;
	return res;
}

int searchByIndex(int index) {
	int cnt = 0;
	int key = 0;
	int res = 0;

	while (index >= cnt + head[key].data) cnt += head[key++].data;

	register Node* p = &head[key];

	index -= cnt;
	while (index--) p = p->next;

	res = p->next->data;
	return res;
}
