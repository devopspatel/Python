import numpy as np

arr = np.arange(0, 11)
print(arr)

print("ADD > ", arr + arr)
print("SUBSTRACT > ", arr - arr)
print("DIVIDE > ", arr[1:] / arr[1:])
print("MULTIPLY > ", arr * arr)
print("SUB FROM EACH ELEMENT > ", arr - 100)
print("ADD TO EACH ELEMENT > ", arr + 10)
print("MULTIPLY EACH ELEMENT > ", arr * 10)
print("DIVIDE EACH ELEMENT > ", arr / 10)

print("SQUARE > ", arr ** 3)

print("MAX > ", arr.max())
print("MIN > ", arr.min())