import itertools as it
import datetime

def time_it(func):
    def wrapper(*args, **kwargs):
        start = datetime.datetime.now()
        result = func(*args, **kwargs)
        end = datetime.datetime.now()
        print ('Functiona: {0} --- Time: {1}'.format(func.__name__,end-start))
        return result
    return wrapper

# HIGH COMPUTE POWER
def fib_v1(n):
    return n if n==0 or n==1 else fib_v1(n-1) + fib_v1(n-2)

lst = []
start = datetime.datetime.now()
for i in range(30):
    lst.append(fib_v1(i))
end = datetime.datetime.now()
print('V1 --- {0} > {1} >>> Time: {2}'.format(i, lst, end-start))

# HIGH COMPUTE POWER WITH CACHE (Check out this video > https://www.youtube.com/watch?v=Qk0zUZW-U_M)
fib_cache = {}
def fib_v1_cache(n):
    if (n in fib_cache):
        return fib_cache[n]

    if (n == 0 or n == 1):
        val = n
    else:
        val = fib_v1_cache(n-1) + fib_v1_cache(n-2)

    # Caching the most recently computed values
    fib_cache[n] = val

    return val

lst = []
start = datetime.datetime.now()
for i in range(35):
    lst.append(fib_v1_cache(i))
end = datetime.datetime.now()
print('V1_CACHE --- {0} > {1} >>> Time: {2}'.format(i, lst, end-start))

###########################################################################################################

# HIGH MEMORY CONSUMPTION
def fib_v2(n, lst):
#     return n if (n==0 or n==1) else (lst[n-2] + lst[n-1])
    return n if (n==0 or n==1) else (lst[0] + lst[1])

lst = []
for i in range(30):
    lst.append(fib_v2(i, lst[-2:]))

print (f'V2 --- {i} > {lst}')

###########################################################################################################

# NUMBER OF STEPS
def fib_v3(n):
    lst = []
    a, b = 0, 1
    for _ in range(n):
        lst.append(a)
        a, b = b, a + b
    return lst

i = 30
print (f'V3 --- {i} > {fib_v3(i)}')

###########################################################################################################

def second_order(p, q, r, initial_values):
    """Return sequence defined by s(n) = p * s(n-1) + q * s(n-2) + r."""
    intermediate = it.accumulate(
        it.repeat(initial_values),
        lambda s, _: (s[1], p*s[1] + q*s[0] + r)
    )
    return map(lambda x: x[0], intermediate)

def fib_v4(n):
    fibs = second_order(p=1, q=1, r=0, initial_values=(0, 1))
    return list(next(fibs) for _ in range(n))

i = 30
print (f'V4 --- {i} > {fib_v4(i)}')
