class Solution(object):
    def maximumTime(self, time):
        time = list(time)
        if time[0] =='?':
            time[0] = '1' if (time[1] !='?' and time[1] >='4') else '2'
        if time[1] =='?':
            time[1] = '9' if (time[0] <='1') else '3'
        if time[3] =='?':
            time[3] = '5'
        if time[4] =='?':
            time[4] = '9'
        return "".join(time)