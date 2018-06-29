import os as os

print(os.getpid())


a = 700
try:
    #a = int(input("Enter Number ONLY: "))
    f = lambda b: "G" if b>100 else "S"
    print(f(a))
except:
    print("Exception")

print("User Input: ", a)


g = lambda b: 2 if b>100 else (1 if (b <=100 and b > 0) else 0)
print(g(1000))
print(g(20))
print(g(0))