import numpy as np
l = [1, 2, 3]

ar = np.array(l)
print(ar[1])

# Converting a list in an array
l = [[1,2,3],[4,5,6],[7,8,9]]
ar = np.array(l)
print(ar)

# Create an array between 0 to 10 with step size = 1
a = np.arange(0,11,1)
print(a)

# Create a 2 dimentional array of ZEROS (rows x columns)
b = np.zeros([2,3])
print(b)

# Create a 2 dimentional array of ONES (rows x columns)
c = np.ones((5,5))
print(c)

# Create evenly spaced points between (start, stop, number of points)
d = np.linspace(0,5,9)
print(d)

# Create a singular array of size (A x A)
e = np.eye(6)
print(e)

# Create a random array between 0 and 1 (Single or 2 dimentional)
f = np.random.rand(5,5)
print(f)

# Create a random array for integers
g = np.random.randint(1,100,50)
print(g)

# Exercise
h = np.arange(25)
print(h.reshape(5,5))
print(h)

i = np.random.randint(0,50,10)[0]
print(i)