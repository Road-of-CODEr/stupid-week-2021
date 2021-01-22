// user.cpp

int f[500000];

void failureFunction(int N, const char *str){
	for (int i = 0; i < 500000; ++i) f[i] = 0;

	int begin = 1, m = 0;
	
	while (begin + m < N) {
		if (str[begin + m] == str[m]) {
			m++;
			f[begin + m - 1] = m;
		}
		else {
			if (m == 0) begin++;
			else {
				begin += (m - f[m - 1]);
				m = f[m - 1];
			}
		}
	}
}


int FindString(int N, char* A, int M, char* B) {
	failureFunction(M, B);

	int answer = 0;
	int begin = 0, m = 0;

	while (begin <= N - M) {
		if (m < M && A[begin + m] == B[m]) {
			m++;

			if (m == M) answer++;
		}
		else {
			if (m == 0) begin++;
			else {
				begin += (m - f[m - 1]);
				m = f[m - 1];
			}
		}
	}

	return answer;
}
