def swaps(s, t, k):
#     ss = list(s)
#     tt = list(t)
    unmatched = []
    
    ret = False
    if (len(s) == len(t)):
        for i in range(len(s)):
            if (s[i] == t[i]):
                pass
            else:
                unmatched.append(i)
    
    ul = len(unmatched)
    if ((int(ul/2) + ul % 2) <= k):
        for i in range(len(unmatched)):
            print (i)
    
    return unmatched

s = 'conserve'
t = 'converse'
k = 1
print (f'S: {s} - T: {t} - K: {k} >> ANSWER: {swaps(s, t, k)}')