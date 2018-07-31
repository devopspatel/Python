import datetime

def prime_v1(n):
    l = []
    i = 2
    while (len(l) < n):
        if (i == 2 or i == 3):
            l.append(i)
        else:
            for a in range(2, i):
                if (i%a == 0):
                    break
                elif (a == i - 1):
                    l.append(i)
        i += 1
    return (l)

#############################################################################################################

def prime_v2(n):
    l = []
    i = 2
    while (len(l) < n):
        if (i == 2 or i == 3):
            l.append(i)
            i += 1
        else:
            for a in range(2, i):
                if (i%a == 0):
                    break
                elif (a == i - 1):
                    l.append(i)
            i += 1
    return (l)

#############################################################################################################

n = 5
start = datetime.datetime.now()
print (f'V1 PRIMEs: {n} >> {prime_v1(n)}')
# prime(n)
end = datetime.datetime.now()
print (f'V1 TIME DELTA: {end - start}')



start = datetime.datetime.now()
print (f'V2 PRIMEs: {n} >> {prime_v2(n)}')
# prime_v2(n)
end = datetime.datetime.now()
print (f'V2 TIME DELTA: {end - start}')