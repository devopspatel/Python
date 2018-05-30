import pandas as pd

df = pd.DataFrame({'col1':[1,2,3,4],'col2':[444,555,666,444],'col3':['abc','def','ghi','xyz']})
print("DF:\n", df)

print("Unique Values in COL 2: \n", df['col2'].unique())
print("Total No of Unique Val: \n", df['col2'].nunique())

print("Count of Unique Val:\n", df['col2'].value_counts())