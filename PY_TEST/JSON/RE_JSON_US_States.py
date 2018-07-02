import json
import re

with open('states.json') as fr:
    data = fr.read()
    states = re.finditer(r'"name": "([a-zA-Z ]+)"', data)
    abb = re.finditer(r'"abbreviation": "([A-Z]+)"', data)
    
    fData = rf.read()
    pattern = re.compile(r'"name": "([a-zA-Z]+)"')
    states = pattern.finditer(fData)
    for state in states:
        print(state)
    
    
for state in states:
    print(state[1])
    
for ab in abb:
    print(ab[1])