# 1
import numpy as np

# 2
print(np.zeros(10))

# 3
print(np.ones(10))

# 4
print(np.ones(10)*5)

# 5
print(np.arange(10,51))

# 6
ar = np.arange(10,51)
print(ar[ar[:]%2 == 0])
print(np.arange(10, 51, 2))

# 7
print("3x3 Arrary")
print(np.arange(9).reshape(3,3))

# 8
print(np.eye(3))

# 9
a = np.random.rand(1)
print(a)

# 10
print(np.random.randn(25))

# 11
print(np.arange(1, 101).reshape(10, 10)/100)

# 12
print(np.linspace(0, 1, 20))

# 13
ar = np.arange(1, 26).reshape(5,5)
print(ar[2:,1:])

# 14
print(ar[3, 4])

# 15
print(ar[:3,1:2])

# 16
print(ar[4])

# 17
print(ar[3:])

# 18
print(ar.sum())
print(np.sum(ar))

# 19
print(np.std(ar))
print(ar.std())

# 20
print(ar.sum(axis = 0))