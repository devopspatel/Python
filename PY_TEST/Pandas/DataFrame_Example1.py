import numpy as np
import pandas as pd

from numpy.random import randn

np.random.seed(101)

# Setting up DataFrame 
df = pd.DataFrame(randn(5,4),['A','B','C','D','E'],['W','X','Y','Z'])
print(df)

# Selecting specific columns
print(df[['W','Z']])

# Adding a New Column
df['dd'] = df['W'] + df['Y']
print(df)

# Dropping a Column (INPLACE = True > Making a permanent Change)
print(df.drop('dd',axis=1,inplace=True))

# Identifying the shape of DataFrame > Returns a Tuple
print(df.shape)

# Dropping a Row (Inplace = False > Just temporary Change)
print(df.drop('A'))

# Selecting a specific Row
print(df.loc['A'])

# Selecting subset
print(df.loc['B','Y'])
print(df.loc[['A','B'],'Y'])
print(df.loc[['A','B'],['W','X','Y']])

# Selecting subset (iloc)
print(df.iloc[0,0])
print(df.iloc[0])