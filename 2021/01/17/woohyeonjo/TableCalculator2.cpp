// user.cpp

#define MAXR		99
#define MAXC		26

enum {
    NULL,
    NUM,
    ADD,
    SUB,
    MUL,
    DIV,
    MAX,
    MIN,
    SUM,
};

struct Exp {
    bool solved;
    int type;
    int p1[2];
    int p2[2];
} exp[MAXR + 1][MAXC + 1];

int ans[MAXR + 1][MAXC + 1];
int RR, CC;

int max(int a, int b) { return a > b ? a : b; }
int min(int a, int b) { return a > b ? b : a; }

int len(const char *str) {
    int res = 0;

    if(*str == '-') str++;
    while(*str++) res++;

    return res;
}

int stoi(const char *str) {
    int res = 0;
    int sign = 1;
    int d = 1;
    int l = len(str);
    int i = l - 1;

    if(*str == '-') {
        sign = -1;
        str++;
    }

    while(i >= 0) {
        res += d * (str[i] - '0');
        d *= 10;
        i--;
    }

    return res * sign;
}

void setAddr(const char *str, Exp *exp) {
    int open = 3;
    int close = open;
    int comma = open;
    char sr[3] = {0,};
    char er[3] = {0,};

    while(str[comma] != ',') comma++;
    while(str[close] != ')') close++;

    sr[0] = str[open + 2];
    if(open + 3 != comma) sr[1] = str[open + 3];
    er[0] = str[comma + 2];
    if(comma + 3 != close) er[1] = str[comma + 3];

    exp->p1[0] = stoi(sr);
    exp->p1[1] = str[open + 1] - 'A' + 1;
    exp->p2[0] = stoi(er);
    exp->p2[1] = str[comma + 1] - 'A' + 1;
}

void init(int C, int R) {
    RR = R;
    CC = C;
    for(int r = 1 ; r <= R ; ++r) {
        for(int c = 1 ; c <= C ; ++c) {
            exp[r][c].solved = false;
            exp[r][c].type = NULL;
            exp[r][c].p1[0] = 0;
            exp[r][c].p1[1] = 0;
            exp[r][c].p2[0] = 0;
            exp[r][c].p2[1] = 0;
            ans[r][c] = 0;
        }
    }
}

void set(int col, int row, char input[]) {
    if((*input >= '0' && *input <= '9') || *input == '-') {
        exp[row][col].type = NUM;
        ans[row][col] = stoi(input);
        return;
    }

    setAddr(input, &exp[row][col]);

    switch(input[2]) {
        case 'D': exp[row][col].type = ADD; break;
        case 'B': exp[row][col].type = SUB; break;
        case 'L': exp[row][col].type = MUL; break;
        case 'V': exp[row][col].type = DIV; break;
        case 'X': exp[row][col].type = MAX; break;
        case 'N': exp[row][col].type = MIN; break;
        case 'M': exp[row][col].type = SUM; break;
    }
}

int update(int r, int c) {
    if(exp[r][c].type == NUM || exp[r][c].solved) return ans[r][c];

    Exp *now = &exp[r][c];
    int res = 0, rr = 0, cc = 0;

    now->solved = true;

    if(exp[r][c].type == NULL) {
        ans[r][c] = 0;
        return 0;
    }

    if(now->type == ADD) res = update(now->p1[0],now->p1[1]) + update(now->p2[0], now->p2[1]);
    else if(now->type == SUB) res = update(now->p1[0],now->p1[1]) - update(now->p2[0], now->p2[1]);
    else if(now->type == MUL) res = update(now->p1[0],now->p1[1]) * update(now->p2[0], now->p2[1]);
    else if(now->type == DIV) res = update(now->p1[0],now->p1[1]) / update(now->p2[0], now->p2[1]);
    else {
        rr = now->p1[0];
        cc = now->p1[1];
        res = update(rr, cc);

        if(now->p1[0] == now->p2[0]) {
            for(int i = cc + 1 ; i <= now->p2[1] ; ++i) {
                if(now->type == MAX) res = max(res, update(rr, i));
                else if(now->type == MIN) res = min(res, update(rr, i));
                else res += update(rr, i);
            }
        } else {
            for(int i = rr + 1 ; i <= now->p2[0] ; ++i) {
                if(now->type == MAX) res = max(res, update(i, cc));
                else if(now->type == MIN) res = min(res, update(i, cc));
                else res += update(i, cc);
            }
        }
    }

    ans[r][c] = res;
    return ans[r][c];
}

void update(int value[MAXR][MAXC]) {
    for(int r = 1 ; r <= RR ; ++r) {
        for(int c = 1 ; c <= CC ; ++c) {
            exp[r][c].solved = false;
        }
    }

    for(int r = 0 ; r < RR ; ++r) {
        for(int c = 0 ; c < CC ; ++c) {
            value[r][c] = update(r + 1, c + 1);
        }
    }
}

