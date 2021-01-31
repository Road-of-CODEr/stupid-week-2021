#define MAX_N 1000
#define MAX_M 20
#define NULL 0
#define MAX_STAR_IN_MAP 20000
#define MAX_STAR_IN_CONSTELLATION 20
 
struct Result {
    int y, x;
};
 
 
struct Pos {
    int y, x;
};
Pos constellation[4][MAX_STAR_IN_CONSTELLATION];
Pos center_star;
Pos sttars[MAX_STAR_IN_MAP];
int sidx;
int NN;
int MM;
int map[MAX_N][MAX_N];
void init(int N, int M, int Map[MAX_N][MAX_N])
{
    sidx = 0;
    MM = M;
    NN = N;
    int i, j;
    for (i = 0; i < N; i++) {
        for (j=0; j < N; j++) {
            map[i][j] = Map[i][j];
            if (map[i][j])
                sttars[sidx++] = { i, j};
        }
    }
}
Result findConstellation(int stars[MAX_M][MAX_M]) {
    Pos *center, *p;
    int idx = 0;
    int i, j, k, r ,x, y;
    for (i = 0; i < MM; i++) {
        for (j = 0; j < MM; j++) {
            if (stars[i][j] == 1)
                constellation[0][idx++] = { i, j };
            else if (stars[i][j] == 9)
                center_star = { i,j };
        }
    }
    for (i = 0; i < idx; i++) {
        constellation[0][i] = { constellation[0][i].y - center_star.y , constellation[0][i].x - center_star.x };
        constellation[1][i] = { -constellation[0][i].x, constellation[0][i].y };
        constellation[2][i] = { -constellation[0][i].y,-constellation[0][i].x };
        constellation[3][i] = { constellation[0][i].x, -constellation[0][i].y };
    }
    for (i = 0; i < ::sidx; i++) {
        center = &sttars[i];
        if (!map[center->y][center->x]) {
            sttars[i--] = sttars[--sidx];
            continue;
        }
        for (r = 0; r < 4; r++) {
            p = constellation[r];
            j = 0;
            for (; j < idx; j++) {
                y = center->y + p[j].y;
                x = center->x + p[j].x;
                if (y < 0 || y >= ::N) {
                    break;
                }
                if (x < 0 || x >= ::N) {
                    break;
                }
                if (!map[y][x])
                    break;
            }
            if (j == idx) {
                map[center->y][center->x] = 0;
                for (k = 0; k < idx; k++)
                    map[center->y + p[k].y][center->x + p[k].x] = 0;
                return { center->y, center->x };
            }
        }
    }
    return { -1, -1 };
     
}
