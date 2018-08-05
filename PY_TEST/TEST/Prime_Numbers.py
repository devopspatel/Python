import datetime

def time_dt(func):
    def wrapper(*args, **kwargs):
        start = datetime.datetime.now()
        l = func(*args, **kwargs)
        end = datetime.datetime.now()
        print (f'FUNCTION: {func.__name__} >> TIME DELTA: {end - start}')
        return l
    return wrapper

@ time_dt
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

@ time_dt
def prime_v2(n):
    l = []
    i = 2
    ex = [0,2,4,5,6,8]
    
    while (len(l) < n):
        if (i == 2 or i == 3 or i == 5):
            l.append(i)
        elif (i%10 not in ex):
            for a in l:
                if (i%a == 0):
                    break
                elif (a == l[-1]):
                    l.append(i)
        i += 1
        
    return (l)

@ time_dt
def prime_v3(n):
    l = []
    i = 2
    
    while (len(l) < n):
        for a in l:
            if (i%a == 0):
                break
        else:
            l.append(i)
        i += 1
    
    return (l)

#############################################################################################################

n = 100
prime_v1(n)
prime_v2(n)
prime_v3(n)

# print (f'V1 PRIMEs: {n} >> {prime_v1(n)}')
# print (f'V2 PRIMEs: {n} >> {prime_v2(n)}')
# print (f'V3 PRIMEs: {n} >> {prime_v3(n)}')