import re

ageDict = {}

exampleString = '''
Jessica is 15 years old, and Daniel is 27 years old.
Edward is 97, and his grandfather, Oscar, is 102.
'''

ages = re.findall(r'\d{1,3}', exampleString)
names = re.findall(r'[A-Z][a-z]*', exampleString)

x = 0
for name in names:
    ageDict[name] = ages[0]
    x += 1 
    
print(ageDict)