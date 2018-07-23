import datetime

def answer(l):
    count = 0
    calcs = 0
    
    print (f'LENGTH: {len(l)} -- MIN: {min(l)} -- MAX: {max(l)}')
    
    if (len(l) <= 2000):
        for i in range(len(l)):
            for j in range(i+1, len(l)):
                if (l[i] == 1 or l[j]%l[i] == 0):
                    for k in range(j+1, len(l)):
                        calcs += 1
                        if (l[k]%l[j] == 0):
                            count += 1
    else:
        print ('LENGTH > 2000')
    print (f'TOTAL CALCS: {calcs}')
    return count

# LENGTH OF L <= 2000
# VALUES OF Li <= 999999

start = datetime.datetime.now()
l = list(range(1, 2001))
print (f'FINAL ANSWER: {answer(l)}')
end = datetime.datetime.now()
print (f'TIME DELTA: {end - start}')