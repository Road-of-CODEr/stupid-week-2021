void quickSort(int input[], int pt[], int first, int last);
 
#define MAX_N    100
#define MAX_TOOL 50
#define MAXT     100000
#define R   register
#define SWAP(a,b) {int tmp = a; a = b; b = tmp;}
 
struct TOOL {
    bool run;
    int stepnum;
    int processtime;
    TOOL* next;
}tool[MAX_N * MAX_TOOL], * complete[MAXT + 1];
 
struct STEP {
    int num_tools;
    int num_wating_lot;
    TOOL* tools[MAX_TOOL];
}step[MAX_N + 1];
 
int N, last_time;
 
void run(int cur_time, int stepno) {
    STEP* s = &step[stepno];
 
    for (R int i = 0; (i < s->num_tools) && (0 < s->num_wating_lot); i++) {
        TOOL* t = s->tools[i];
        if (t->run)  continue;
 
        R int end_time = cur_time + t->processtime;
        t->run = true;
        t->next = complete[end_time];
        complete[end_time] = t;
        s->num_wating_lot--;
    }
}
 
int available_steps[100];
void update(int time) {
    for (R int t = last_time + 1; t <= time; t++) {
        R int num_available = 0;
        for (TOOL* m = complete[t]; m; m = m->next) {
            m->run = false;
 
            step[m->stepnum + 1].num_wating_lot++;
            available_steps[num_available++] = m->stepnum;
            available_steps[num_available++] = m->stepnum + 1;
        }
        for (R int n = 0; n < num_available; n++) run(t, available_steps[n]);
    }
    last_time = time;
}
 
void init(int _N) {
    N = _N;
    last_time = -1;
    for (R int i = 0; i <= N; i++) {
        step[i].num_tools = 0;
        step[i].num_wating_lot = 0;
    }
    for (R int i = 0; i <= MAXT; i++) complete[i] = 0;
}
 
void setupTool(int T, int stepNo[5000], int procTime[5000]) {
    quickSort(procTime, stepNo, 0, T - 1);
 
    for (R int i = 0; i < T; i++) {
        R int stepno = stepNo[i];
        tool[i] = { false, stepno, procTime[i], 0 };
 
        step[stepno].tools[step[stepno].num_tools] = &tool[i];
        step[stepno].num_tools++;
    }

}
 
void addLot(int time, int number) {
    update(time);
 
    step[0].num_wating_lot += number;
    run(time, 0);
}
 
int simulate(int time, int wip[MAX_N]) {
    update(time);
 
    for (R int i = 0; i < N; i++) {
        wip[i] = step[i].num_wating_lot;
 
        for (R int j = 0; j < step[i].num_tools; j++) {
            if (step[i].tools[j]->run) wip[i]++;
        }
    }
 
    return step[N].num_wating_lot;
}
 
void quickSort(int input[], int pt[],  int first, int last)
{
    int pivot,i,j;
 
    if (first < last)    {
        pivot = first;
        i = first;
        j = last;
 
        while (i < j)        {
            while (input[i] <= input[pivot] && i < last) i++;
            while (input[j] > input[pivot])   j--;
            if (i < j)   {
                SWAP(input[i], input[j]);
                SWAP(pt[i], pt[j]);
            }
        }
        SWAP(input[pivot], input[j]);
        SWAP(pt[pivot], pt[j]);
 
        quickSort(input, pt, first, j - 1);
        quickSort(input, pt, j + 1, last);
    }
}
