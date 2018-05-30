import numpy as np
import pandas as pd

d = {'A':[1,2,np.nan],'B':[5,np.nan,np.nan],'C':[1,2,3]}
df = pd.DataFrame(d)

print(df)

# Drop Null values from AXIS = 0 (ROWS)
print(df.dropna())

# Drop Null with minimum available data (If minimum 2 columns has data then show in result)
print(df.dropna(thresh=2))

# Drop Null values from AXIS = 1 (COLUMNS)
print(df.dropna(axis=1))

# Fill Values
print(df.fillna('FILL UP'))

# Fill Mean
print(df['A'].fillna(df['A'].mean()))