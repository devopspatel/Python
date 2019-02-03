import random
# rl = list(random.randint(1, 30) for _ in range(10))
rl = list(random.randint(1, 3000000) for _ in range(10000))
# print (rl)

def time_it(func):
    import datetime

    def wrapper(*args, **kwargs):
        start = datetime.datetime.now()
        result = func(*args, **kwargs)
        end = datetime.datetime.now()
        print ('{0} >> MIN VALUE: {1} > TIME DELTA: {2}'.format(func.__name__, result, (end - start)))
        return result
    return wrapper

@time_it
def min_val_1(rl):
    min_val = rl[0]
    for i in range(len(rl)):
        for j in range(len(rl)):
            if (rl[i] < rl [j] and rl[i] < min_val):
                min_val = rl[i]
    return min_val

@time_it
def min_val_2(rl):
    min_val = rl[0]

    for i in range(1, len(rl) - 1):
        if (rl[i] < rl[i + 1] and rl[i] < min_val):
            min_val = rl[i]
    return min_val

min_val_1(rl)
min_val_2(rl)
