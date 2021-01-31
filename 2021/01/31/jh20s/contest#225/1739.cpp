class Solution {
public:

int minimumBoxes(int n) {
	int ans = 0;
	long long a = 1;
	long long num = 0;
	while (1) {
		int temp = ((a+1) * (a + 2) * (a + 3)) / 6;
		if (temp >= n) break;
		a++;
	}
	ans += (a*(a+1))/2;
	n = n - (a*(a+1)*(a+2))/6;
	a = 0;
	while (1) {
		int temp = a * (a + 1) / 2;
		if (temp >= n) break;
		a++;
	}
	ans += a;
	return ans;
}
};