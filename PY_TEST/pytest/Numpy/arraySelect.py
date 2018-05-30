import numpy as np

arr = np.arange(0, 11)
print(arr)

print(arr[1])
print(arr[1:4])
print(arr[5:])
print(arr[:6])

arr[0:5]=100
print(arr)

arr = np.arange(0,11)
arC = arr.copy()

sl = arr[0:5]
print("Original Slice > ", sl)
sl[:] = 99
print("Updated Slice > ", sl)
print("Affected Array > ", arr)
print("Copy Not Affected > ", arC)

arr = arC
boolean_arr = arr > 5
print("Boolean Array > ", boolean_arr)
print("Elements Greater Than 5", arr[boolean_arr])

print("Array Elements Less Than 3 > ", arr[arr<3])