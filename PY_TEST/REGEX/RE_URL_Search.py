import re

urls = '''
http://www.google.com
https://gmail.com
https://www.nasa.gov
https://dipendesai.net
'''

# pattern = re.compile(r'https?://(www\.)?\.[a-zA-Z0-9]+')
pattern = re.compile(r'https?://(www\.)?\w+\.\w+')
matches = pattern.finditer(urls)

for match in matches:
    print(match)