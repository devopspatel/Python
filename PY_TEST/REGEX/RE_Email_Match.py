import re

emails = '''
1ddesai2@gmail.com
2desai.dipen@yahoo.com
3desaidn@corning.com
4ddesai@fisa.nyc.gov
5ddesai2@new.net
6dipen_desai@yahoo.com
7dipen_desai@my-company.net
'''

pattern = re.compile(r'[a-zA-Z0-9._]+@[a-zA-Z-]+\.[a-zA-Z0-9.-]+')

matches = pattern.finditer(emails)

for match in matches:
    print(match)