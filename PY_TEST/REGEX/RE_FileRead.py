import re

with open('C:/Users/RRDD/Desktop/Automation/Python/GIT/PY_TEST/Data.txt', 'r+', encoding='UTF-8') as f:
    content = f.read()
    
    p2 = re.compile(r'\d{3}.\d{3}.\d{4}')
    phones =p2.finditer(content)
    
    pattern = re.compile(r'([a-zA-Z0-9 .]+),\s([a-zA-Z]+)\s([A-Z]+)\s(\d{5})')
    matches = pattern.finditer(content)
    
with open('City State Zip.txt', 'w') as fw:
    for match in matches:
        fw.write('{}, {} {}\n'.format(match[2], match[3], match[4]))
        
with open('phone.txt', 'w') as fp:
    for phone in phones:
        fp.write('{}\n'.format(phone[0]))