# HIGH COMPUTE POWER
def fib_v1(n):
    return n if n==0 or n==1 else fib_v1(n-1) + fib_v1(n-2)

lst = []
for i in range(0, 15):
    lst.append(fib_v1(i))
print(f'V1 --- {i} > {lst}')

###########################################################################################################

# HIGH MEMORY CONSUMPTION
def fib_v2(n, lst):
#     return n if (n==0 or n==1) else (lst[n-2] + lst[n-1])
    return n if (n==0 or n==1) else (lst[0] + lst[1])
   
lst = []
for i in range(0,15):
    lst.append(fib_v2(i, lst[-2:]))
         
print (f'V2 --- {i} > {lst}')
    
###########################################################################################################