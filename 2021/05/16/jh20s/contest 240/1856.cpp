class Solution {
public:
bool areSentencesSimilar(string sentence1, string sentence2) {
	if (sentence1.size() > sentence2.size()) {
		swap(sentence1, sentence2);
	}
	vector<string> s1, s2;
	sentence1 = sentence1+ " ";
	sentence2 = sentence2 + " ";
	string s="";
	for (int i = 0; i < sentence1.size(); i++) {
		if (sentence1[i] == ' '){
			s1.push_back(s);
			s = "";
		}
		else {
			s = s + sentence1[i];
		}
	}
	s = "";
	for (int i = 0; i < sentence2.size(); i++) {
		if (sentence2[i] == ' ') {
			s2.push_back(s);
			s = "";
		}
		else {
			s = s + sentence2[i];
		}
	}
	
	int l = 0, r = 0;
	for (l = 0; l < s1.size(); l++) {
		if (s1[l] == s2[l]) {
			continue;
		}
		else {
			break;
		}
	}
	for (r = 0; r < s1.size();r++) {
		if (s1[s1.size()-1-r] == s2[s2.size()-1-r]) {
			continue;
		}
		else {
			break;
		}
	}
	if (l == s1.size() || r == s1.size() || (l + r) == s1.size()) 
		return true;
	else
		return false;
}

};