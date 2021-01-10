// main.cpp

#ifndef _CRT_SECURE_NO_WARNINGS
#define _CRT_SECURE_NO_WARNINGS
#endif

#include <stdio.h>

#define MAX_DEPTH	25

extern void init();
extern void insert(int name , int parent);
extern void remove(int name);
extern int findAnce(int name , int ance[]);
extern int findDesc(int name , int target);

#define INSERT		100
#define REMOVE		200
#define FINDANCE	300
#define FINDDESC	400

////////////////////////////////////////////////////////////////////////////////

int main() {
	setbuf(stdout, NULL);

	int T;
	scanf("%d", &T);

	int N;

	int cmd, ret;

	int name, parent , target;
	int ans;

	int len;
	int ance[MAX_DEPTH];
	int ance_ans[MAX_DEPTH];

	for (int TC = 1; TC <= T; TC++) {

		init();

		int score = 100;

		scanf("%d", &N);

		for (int i = 0; i < N; i++) {
			scanf("%d", &cmd);

			switch (cmd) {

			case INSERT:
				scanf("%d %d", &name , &parent);
				insert(name , parent);

				break;

			case REMOVE:
				scanf("%d", &name);
				remove(name);

				break;

			case FINDANCE:
				scanf("%d %d", &name, &len);

				for (int i = 0; i < len; i++) {
					scanf("%d", &ance_ans[i]);
				}

				if (score != 0) {
					ret = findAnce(name, ance);

					if (ret != len) {
						score = 0;
					}

					for (int i = 0; i < len; i++) {
						if (ance[i] != ance_ans[i]) {
							score = 0;
						}
					}
				}

				break;
			case FINDDESC:
				scanf("%d %d %d", &name, &target, &ans);

				ret = findDesc(name, target);

				if (ret != ans) {
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

#define INPUT_MAX 10002

struct Node {
	Node* prev;
	int id;
} m[INPUT_MAX];

int midx;
int parentInfo[INPUT_MAX];
Node* childInfo[INPUT_MAX];

Node* malloc() {
	return &m[midx++];
}

void init() {
	midx = 0;
	malloc();

	for (register int i = 0; i < INPUT_MAX; ++i) {
		parentInfo[i] = 0;
		childInfo[i] = 0;
	}
}

void insert(int name, int parent) {
	parentInfo[name] = parent;

	Node* p = malloc();
	p->id = name;
	p->prev = childInfo[parent];
	childInfo[parent] = p;
}

void remove(int name) {
	parentInfo[name] = 0;
	childInfo[name] = 0;
}

int findAnce(int name, int ance[]) {
	int idx = 0;
	int next = name;
	
	while (next = parentInfo[next]) {
		ance[idx++] = next;
	}

	return idx;
}

int findDesc(int name, int target) {
	int next = target;

	while (next = parentInfo[next]) {
		if (next == name) return 1;
	}

	return 0;
}
