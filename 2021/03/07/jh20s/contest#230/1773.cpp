class Solution {
public:
int countMatches(vector<vector<string>>& items, string ruleKey, string ruleValue) {
	map<string, int> m1, m2, m3;
	for (int i = 0; i < items.size(); i++) {
		m1[items[i][0]]++;
		m2[items[i][1]]++;
		m3[items[i][2]]++;
	}
	if (ruleKey == "type") {
		return m1[ruleValue];
	}
	else if (ruleKey == "color") {
		return m2[ruleValue];
	}
	else if (ruleKey == "name") {
		return m3[ruleValue];
	}
    return 0;
}
};