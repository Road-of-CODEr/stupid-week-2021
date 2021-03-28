def solution(numbers, hand):
    curLeft = '*'
    curRight = '#'
    midDicttwo = {1: ['1', '3', '5'], 2: ['4', '8', '6'], 3: ['7', '9', '0'], 4: ['*', '#']}
    midDicttfive = {1: ['2', '4', '6', '8'], 2: ['1', '3', '7', '9', '0'], 3: ['*', '#']}
    midDictteight = {1: ['5', '7', '0', '9'], 2: ['2', '4', '6', '*', '#'], 3: ['1', '3']}
    midDicttzero = {1: ['8', '*', '#'], 2: ['5', '7', '9'], 3: ['2', '4', '6'], 4: ['1', '3']}
    left = [1, 4, 7]
    right = [3, 6, 9]
    mid = [2, 5, 8, 0]
    answer = ''
    for number in numbers:
        if number in left:
            answer += 'L'
            curLeft = str(number)
        elif number in right:
            answer += 'R'
            curRight = str(number)
        if number in mid:
            if number == 2:
                rightDistance = 0
                leftDistance = 0
                for key in midDicttwo.keys():
                    if curRight in midDicttwo[key]:
                        rightDistance = key
                    if curLeft in midDicttwo[key]:
                        leftDistance = key
                if rightDistance > leftDistance:
                    answer += 'L'
                    curLeft = str(number)
                elif rightDistance < leftDistance:
                    answer += 'R'
                    curRight = str(number)
                else:
                    if hand == 'left':
                        answer += 'L'
                        curLeft = str(number)
                    else:
                        answer += 'R'
                        curRight = str(number)
            elif number == 5:
                rightDistance = 0
                leftDistance = 0
                for key in midDicttfive.keys():
                    if curRight in midDicttfive[key]:
                        rightDistance = key
                    if curLeft in midDicttfive[key]:
                        leftDistance = key
                if rightDistance > leftDistance:
                    answer += 'L'
                    curLeft = str(number)
                elif rightDistance < leftDistance:
                    answer += 'R'
                    curRight = str(number)
                else:
                    if hand == 'left':
                        answer += 'L'
                        curLeft = str(number)
                    else:
                        answer += 'R'
                        curRight = str(number)

            elif number == 8:
                rightDistance = 0
                leftDistance = 0
                for key in midDictteight.keys():
                    if curRight in midDictteight[key]:
                        rightDistance = key
                    if curLeft in midDictteight[key]:
                        leftDistance = key
                if rightDistance > leftDistance:
                    answer += 'L'
                    curLeft = str(number)
                elif rightDistance < leftDistance:
                    answer += 'R'
                    curRight = str(number)
                else:
                    if hand == 'left':
                        answer += 'L'
                        curLeft = str(number)
                    else:
                        answer += 'R'
                        curRight = str(number)

            elif number == 0:
                rightDistance = 0
                leftDistance = 0
                for key in midDicttzero.keys():
                    if curRight in midDicttzero[key]:
                        rightDistance = key
                    if curLeft in midDicttzero[key]:
                        leftDistance = key
                if rightDistance > leftDistance:
                    answer += 'L'
                    curLeft = str(number)
                elif rightDistance < leftDistance:
                    answer += 'R'
                    curRight = str(number)
                else:
                    if hand == 'left':
                        answer += 'L'
                        curLeft = str(number)
                    else:
                        answer += 'R'
                        curRight = str(number)
    return answer
