import random

# rl = list(random.randint(1, 30) for _ in range(10))
rl = list(random.randint(1, 30000) for _ in range(10000))

def time_it(func):
    import datetime
    def wrapper(*args, **kwargs):
        start = datetime.datetime.now()
        result = func(*args, **kwargs)
        end = datetime.datetime.now()
        print ('Functiona: {0} --- Time: {1}'.format(func.__name__,end-start))
        return result
    return wrapper

@time_it
def min_val1(rl):
    min_val = rl[0]
    for i in range(len(rl)):
        for j in range(len(rl)):
            if (rl[i] < rl [j] and rl[i] < min_val):
                min_val = rl[i]
    return min_val

@time_it
def min_val2(rl):
    min_val = rl[0]
    for i in range(1, len(rl) - 1):
        if (rl[i] < rl[i + 1] and rl[i] < min_val):
            min_val = rl[i]
    return min_val


print ('Min Value F1: {0}'.format(min_val1(rl)))
print ('Min Value F2: {0}'.format(min_val2(rl)))
