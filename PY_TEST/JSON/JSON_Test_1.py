import json
import re

with open('Sample.json', 'r') as fr:
    data = json.loads(fr.read())
    
    for name in data['people']:
        print(name)
        
new_json = json.dumps(data, indent=2)
print(new_json)