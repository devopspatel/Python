import json
import re

with open('states.json') as fr:
    data = fr.read()
    states = re.finditer(r'"name": "([a-zA-Z ]+)"', data)
    abb = re.finditer(r'"abbreviation": "([A-Z]+)"', data)
    
    fData = fr.read()
    print(f'FDATA: {fData}')
    pattern = re.compile(r'"name": "([a-zA-Z]+)"')
    stts = pattern.finditer(fData)
    for state in stts:
        print('A')
        print(state)
    
for state in states:
    print('B')
    print(state[1])
    
for ab in abb:
    print('C')
    print(ab[1])