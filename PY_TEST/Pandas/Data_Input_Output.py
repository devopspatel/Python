import pandas as pd

df = pd.read_csv('example.csv')
print(df)

df['NEW'] = [500, 501, 502, 503]
df.to_csv('newEx.csv', index=False)

df1 = pd.read_excel('Excel_Sample.xlsx',sheet_name='Sheet1')
print(df1)

#df1['WOW'] = [16, 17, 18, 19]
#df1.to_excel('Excel_Sample.xlsx', sheet_name='NEWSHEET')

data = pd.read_html('http://www.fdic.gov/bank/individual/failed/banklist.html')
print("LENGTH: ",len(data))