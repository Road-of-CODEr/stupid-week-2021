class Solution {
public:
 string largestMerge(string s1, string s2) {
        if (s1.size() == 0  || s2.size() == 0)
            return s1 + s2;
        if (s1 > s2)
            return s1[0] + largestMerge(s1.substr(1), s2);
        return s2[0] + largestMerge(s1, s2.substr(1));
    }
};