class Solution {
public:
    bool checkOnesSegment(string s) {
        int check=0;
        int now =0;
        for(int i=0;i<s.size();i++){
            if(now!=s[i]){
                now =s[i];
                if(now =='1')check++;
            }
        }
        return check<=1?true:false;
    }
};