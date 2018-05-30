import numpy as np
import pandas as pd

data = {'Company':['GOOG','GOOG','MSFT','MSFT','FB','FB'],
        'Person':['Sam','Charlie','Amy','Vanessa','Carl','Sarah'],
        'Sales':[200,120,340,124,243,350]}
df = pd.DataFrame(data)
print(df)

# Group by Company
byComp = df.groupby('Company')
print("MEAN: ",byComp.mean())

# Other Functions
print('MIN: ', byComp.min())
print('MAX: ', byComp.max())
print('STDDEV: ', byComp.std())
print('DESCRIBE: ', byComp.describe())
print('TRANSPOSE: ', byComp.describe().transpose())