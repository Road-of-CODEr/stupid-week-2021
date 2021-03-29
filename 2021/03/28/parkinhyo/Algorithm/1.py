def solution(a, b):
    date = {1: 31, 2: 29, 3: 31, 4: 30, 5: 31, 6: 30, 7: 31, 8: 31, 9: 30, 10: 31, 11: 30, 12: 31}
    myDict = {1: "FRI", 2: "SAT", 3: "SUN", 4: "MON", 5: "TUE", 6: "WED", 0: "THU"}
    day = 0
    for mon in range(1, a):
        day += date[mon]

    day += b
    return myDict[day % 7]
