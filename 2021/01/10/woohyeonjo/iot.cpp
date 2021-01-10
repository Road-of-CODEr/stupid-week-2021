// main.cpp
#ifndef _CRT_SECURE_NO_WARNINGS
#define _CRT_SECURE_NO_WARNINGS
#endif

#include <stdio.h>
#include <string.h>

#define MEMORYSIZE		65536

static unsigned char mem[MEMORYSIZE];

#define CMD_PUT		100
#define CMD_DEL		200
#define CMD_GET 	300
#define CMD_GETKEY	400

#define MAXLEN 12

static unsigned char key[MAXLEN + 1], value[MAXLEN + 1];

extern bool init(int N);
extern void put(unsigned char key_in[MAXLEN + 1], unsigned char value_in[MAXLEN + 1]);
extern void del(unsigned char key_in[MAXLEN + 1]);
extern void get(unsigned char key_in[MAXLEN + 1], unsigned char value_out[MAXLEN + 1]);
extern void getkey(unsigned char value_in[MAXLEN + 1], unsigned char key_out[MAXLEN + 1]);

bool memread(unsigned char dest[], int pos, int len) {
	if (pos < 0 || len <= 0 || pos + len > MEMORYSIZE)
		return false;

	for (register int i = 0; i < len; ++i)
		dest[i] = mem[pos + i];

	return true;
}

bool memwrite(unsigned char src[], int pos, int len) {
	if (pos < 0 || len <= 0  || pos + len > MEMORYSIZE)
		return false;

	for (register int i = 0; i < len; ++i)
		mem[pos + i] = src[i];

	return true;
}

static bool ztrcmp(unsigned char *a, unsigned char *b) {
	while (*a && *a == *b) ++a, ++b;
	return *a == *b;
}

static int TC, totalscore;
static int Q, N;
static bool okay;
 
static bool run() {
	unsigned char key_u[MAXLEN + 1], value_u[MAXLEN + 1];
	
	scanf("%d %d", &Q, &N);
	
	if (N < 10 || N > 2500)
		return false;
	
	bool okay = init(N);

	for (int q = 0; q < Q; ++q) {
		int cmd;
		scanf("%d", &cmd);

		switch(cmd) {
		case CMD_PUT:
			scanf("%s %s", key, value);
			if (okay) put(key, value);
			break;
		case CMD_DEL:
			scanf("%s", key);
			if (okay) del(key);
			break;
		case CMD_GET:
			scanf("%s %s", key, value);
			if (okay) {
				get(key, value_u);
				if (!ztrcmp(value, value_u))
					okay = false;
			}
			break;
		case CMD_GETKEY:
			scanf("%s %s", value, key);
			if (okay) {
				getkey(value, key_u);
				if (!ztrcmp(key, key_u))
					okay = false;
			}
			break;
		default:
			break;
		}
	}

	return okay;
}

int main() {
    //freopen("eval_input.txt", "r", stdin);

	setbuf(stdout, NULL);
    scanf("%d", &TC);

	totalscore = 0;
    for (int testcase = 1; testcase <= TC; ++testcase) {
		int score = run() ? 1000 : 0;
		printf("#%d %d\n", testcase, score);
		totalscore += score;
    }
	printf("total score = %d\n", totalscore / 10 / TC);
    return 0;
}


// solution.cpp
bool mstrncmp(unsigned char *a, unsigned char *b, int n) {
    int i = 0;
    while(i < n && *a && *a == *b)
        ++i, ++a, ++b;
    if( i == n ) return true;
    return *a == *b;
}

int mstrlen(unsigned char *a) {
    int len = 0;
    while (*a)
        ++a, ++len;
    return len;
}


extern bool memread(unsigned char dest[], int pos, int len);
extern bool memwrite(unsigned char src[], int pos, int len);

#define MAXLEN 12
#define MAX_TABLE 2500
#define DATA_LEN 11
#define IDX_LEN 2
#define FULL_LEN (DATA_LEN+IDX_LEN)
#define MY_IDX(k,is_val) (k + is_val * MAX_TABLE) * FULL_LEN

unsigned long myhash(unsigned char *str)
{
    int i=0;
	unsigned long hash = 5381;
	int c;

	while ( i<12 && (c = *str++))
	{
		hash = (((hash << 5) + hash) + c) % MAX_TABLE;
        i++;
	}

	return hash % MAX_TABLE;
}

void encode(unsigned char in[MAXLEN + 1]){
    if( mstrlen(in) != 12 ) return;
    for(int i=0; i<8; i++){
        in[i] |=  ((in[11] & (1 << i) ) << (7 - i)) ;
    }
}

void decode(unsigned char in[MAXLEN + 1]){
    for(int i=0; i<DATA_LEN; i++){
        if(in[i] == '\0') return;
    }
    in[11] = 0;
    for(int i=0; i<8; i++){
        in[11] |= ((in[i] & ( 1 << 7 )) >> (7 - i));
        in[i] = (in[i] & 0x7f);
    }
    in[12] = '\0';
}

int add(unsigned char in[MAXLEN + 1], int is_val){
    int k = myhash(in);
    unsigned char temp[1];
    while(1){
        memread(temp, MY_IDX(k,is_val), 1);
        if(temp[0] == '\0'){
            encode(in);
            memwrite(in, MY_IDX(k,is_val), 11);
            return k;
        }
        else {
            k++;
            if(k == MAX_TABLE) k=0;
        }
    }
}


int find(unsigned char in[MAXLEN + 1], int is_val ){
    int k = myhash(in);
    unsigned char temp[DATA_LEN];
    encode(in);
    while(1){
        memread(temp,MY_IDX(k,is_val), DATA_LEN);
        if(mstrncmp(temp,in,DATA_LEN)){
            return k;
        }
        else {
            k++;
            if(k == MAX_TABLE) k = 0;
        }
    }
}

bool init(int N) {
    unsigned char temp[1] = {0};
    for(int i=0; i<MAX_TABLE; i++){
        memwrite(temp, MY_IDX(i, 0), 1);
        memwrite(temp, MY_IDX(i, 1), 1);
    }
	return true;
}

void put(unsigned char key_in[MAXLEN + 1], unsigned char value_in[MAXLEN + 1]) {
    int k_idx = add(key_in, 0);
    int v_idx = add(value_in, 1);
    unsigned char temp[2];
    temp[0] = v_idx/100;
    temp[1] = v_idx%100;
    memwrite(temp, MY_IDX(k_idx,0) + DATA_LEN , 2);
    temp[0] = k_idx/100;
    temp[1] = k_idx%100;
    memwrite(temp, MY_IDX(v_idx,1) + DATA_LEN, 2);
}

void del(unsigned char key_in[MAXLEN + 1]) {
	int k_idx = find(key_in, 0);
    unsigned char temp[2] = {0, };
    memwrite(temp, MY_IDX(k_idx, 0), 1);
    memread(temp,MY_IDX(k_idx, 0) + DATA_LEN, 2);
    int v_idx = temp[0] * 100 + temp[1];
    temp[0] = '\0';
    memwrite(temp, MY_IDX(v_idx, 1), 1);
}

void get(unsigned char key_in[MAXLEN + 1], unsigned char value_out[MAXLEN + 1]) {
    int k_idx = find(key_in, 0);
    unsigned char temp[2] = {0, };
    memread(temp,MY_IDX(k_idx, 0) + DATA_LEN, 2);
    int v_idx = temp[0] * 100 + temp[1];
    memread(value_out, MY_IDX(v_idx, 1), DATA_LEN);
    decode(value_out);
}

void getkey(unsigned char value_in[MAXLEN + 1], unsigned char key_out[MAXLEN + 1]) {
    int v_idx = find(value_in, 1);
    unsigned char temp[2] = {0, };
    memread(temp, MY_IDX(v_idx, 1) + DATA_LEN, 2);
    int k_idx = temp[0] * 100 + temp[1];
    memread(key_out, MY_IDX(k_idx, 0), DATA_LEN);
    decode(key_out);
}
