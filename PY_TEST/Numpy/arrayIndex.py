import numpy as np

ar2 = np.array([[5, 10, 15], [20, 25,30], [35, 40, 45]])
print(ar2)

print("First Element > ", ar2[0][0])
print("First Row > ", ar2[0])

print("New Method > ", ar2[0,0])
print("Another > ", ar2[2, 2])

print(ar2[:2,1:])
print(ar2[1:,:2])
print(ar2[:2,:2])
print(ar2[1:,1:])

a2 = np.arange(0,50).reshape(5,10)
print(a2)

print(a2[1:3,3:5])