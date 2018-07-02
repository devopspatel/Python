def fib(n):
    return n if n==0 or n==1 else fib(n-1) + fib(n-2)    

for i in range(0,30):
    print(fib(i))