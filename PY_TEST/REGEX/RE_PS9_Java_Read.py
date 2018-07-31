import re
import json

def ps9_reader ():
    errDict = {}
    
    with open('PS9_Helper.java', 'r') as f:
        content = f.read()

        pattern = re.compile(r'public\s([a-zA-Z0-9\[\]]+)\s.+')
        matches = pattern.finditer(content)
        
        for match in matches:
#             if (match[1] in errDict.keys()):
#                 errDict[match[1]] = errDict[match[1]] + 1
#             else:
#                 errDict[match[1]] = 1
            
            # BELOW LINE REPLACES IF-ELSE BLOCK LISTED ABOVE        
            errDict[match[1]] = errDict.get(match[1], 0) + 1
    return errDict
        

print (ps9_reader())