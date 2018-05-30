import numpy as np
import pandas as pd

from numpy.random import randn

np.random.seed(101)

# Setting up DataFrame 
df = pd.DataFrame(randn(5,4),['A','B','C','D','E'],['W','X','Y','Z'])
print(df)

# Conditional Selection
booldf = df > 0
print(df[booldf])

# Return entire dataframe where value of Z < 0 = TRUE
print(df[df['Z'] < 0])

# Check for conditions in Column 'W' and returns on 'X'
print(df[df['W'] > 0]['X'])

# Same as above > Returns two columns X, Y
print(df[df['W'] > 0][['X','Y']])
### BREAK DOWN
boolser = df['W'] > 0
my_list = ['X', 'Y']
print(df[boolser][my_list])  # Gives same output as above

# Use multiple conditions
print(df[(df['W'] > 0) & (df['Y'] > 0)])
print(df[(df['W'] > 0) | (df['Y'] > 0)])

# Reset Index (Current Index Column becomes a Column of DataFrame)
print(df.reset_index())

# Add a new Columns 'States'
newind = 'CA NY WY OR CO'.split()
print(newind)
df['States'] = newind
print(df)

# Set Index > 'State" column becomes the new Index for DataFrame
df.set_index('States', inplace=True)
print(df)