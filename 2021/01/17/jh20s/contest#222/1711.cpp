class Solution {
public:
long long mod = 1000000007;
int sigma(long long n) {
	return (((n - 1) * n )/ 2)%mod;
}

int countPairs(vector<int>& deliciousness) {
	int ans = 0;
	map<int , int> m;
	for (int i = 0; i < deliciousness.size(); i++) {
		m[deliciousness[i]]++;
	}
	vector<int > v;
	for (auto i : m) {
		v.push_back(i.first);
	}
	for (int i = 0; i < v.size(); i++) {
		for (int j = 0, k = 1; j <= 21; j++, k *= 2) {
			if (m.find(k - v[i]) != m.end()) {
				if (v[i] == k - v[i]){
					ans = (ans + sigma(m[v[i]])) % mod;
				}
				else if(v[i]< k-v[i]){
					ans = (ans + m[v[i]] * m[k - v[i]]) % mod;
				}
			}
		}
	}

	return ans;
}
};