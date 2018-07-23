import itertools as it
 
# HIGH COMPUTE POWER
def fib_v1(n):
    return n if n==0 or n==1 else fib_v1(n-1) + fib_v1(n-2)

lst = []
for i in range(30):
    lst.append(fib_v1(i))
print(f'V1 --- {i} > {lst}')

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