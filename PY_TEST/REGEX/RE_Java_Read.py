import re
import json

errDict = {}

with open('PS9_Helper.java', 'r') as f:
    content = f.read()
    
    pattern = re.compile(r'public\s([a-zA-Z0-9\[\]]+)\s.+')
    matches = pattern.finditer(content)
    
    for match in matches:
        print(match[1])
            
        if (match[1] in errDict.keys()):
            errDict[match[1]] = errDict[match[1]] + 1
        else:
            errDict[match[1]] = 1
            
    print(errDict)