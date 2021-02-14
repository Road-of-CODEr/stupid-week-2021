class Solution {
public:
int maximumScore(int a, int b, int c) {
	vector<int> v = { a,b,c };
	int ans = 0;
	sort(v.begin(), v.end());
	a = v[0], b = v[1], c = v[2];
	int del = min(a, c-b);
	
    ans += del;
    a = max(0, a - del);
	c -= del;
    
    ans += a;
	b -= a / 2 +  (a%2? 1:0);
	c -= a / 2;

    
    ans += min(b, c);

    
	return ans;
}
};