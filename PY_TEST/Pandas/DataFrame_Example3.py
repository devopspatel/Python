import numpy as np
import pandas as pd

from numpy.random import randn

np.random.seed(101)

# Index Levels
outside = ['G1','G1','G1','G2','G2','G2']
inside = [1,2,3,1,2,3]
hier_index = list(zip(outside,inside))
print(hier_index)
hier_index = pd.MultiIndex.from_tuples(hier_index)

# Generate multi-index
print(hier_index)

# Generate Multi-Index DataFrame
df = pd.DataFrame(randn(6,2),hier_index,['A','B'])

# Get Everything as per Outside Index
print(df.loc['G1'])

# Get Everything as per Outside+Inside Index
print(df.loc['G1'].loc[1])

# Get Everything as per Outside+Inside Index + Column A
print(df.loc['G1'].loc[1]['A'])

# Index Names [None, None]
print(df.index.names)
df.index.names = ['Group','Num']
print(df)

# Select a Specific Values
print(df.loc['G2'].loc[2]['B'])

# Cross Reference
print(df.xs(1,level='Num'))
