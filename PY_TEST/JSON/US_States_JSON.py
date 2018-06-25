import json
from setuptools._vendor.packaging import _structures

with open('states.json', 'r') as rf:
    data = json.load(rf)
    
for state in data['states']:
    print(state['name'], state['abbreviation'])
    del state['abbreviation']

with open('new_states.json', 'w') as wf:
    json.dump(data, wf, indent=2)