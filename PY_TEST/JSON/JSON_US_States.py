import json
import re

with open('states.json', 'r') as rf:
    data = json.loads(rf.read())
    for state in data['states']:
        print(state['name'], state['abbreviation'])
        del state['abbreviation']
     
    with open('new_states.json', 'w') as wf:
            json.dump(data, wf, indent=2)