import numpy as np
import pandas as pd

labels = ['a', 'b', 'c']
my_data = [10, 20, 30]
arr = np.array(my_data)
dict = {'a':10, 'b':20, 'c':30}

# Making Series from List
print(pd.Series(data = my_data))
print(pd.Series(data = my_data, index=my_data))
print(pd.Series(data = my_data, index = labels))
print(pd.Series(my_data,labels))

# Making Series from Array
print(pd.Series(data=arr))
print(pd.Series(arr,labels))

# Making Series from Dictionary
print(pd.Series(dict))

# Additional Examples
ser1 = pd.Series([1,2,3,4],['USA','Germany','USSR','Japan'])
print(ser1)

ser2 = pd.Series([1,2,5,4],['USA','Germany','Italy','Japan'])
print(ser2)

print(ser2['USA'])

ser3 = pd.Series(labels)
print(ser3[0])

ser4 = ser1 + ser2
print(ser4)
print(ser4['Italy'])