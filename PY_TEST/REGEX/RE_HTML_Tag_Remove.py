import re

y = "<html><head><title>Look at this</title></head><body><h1>Dipen Desai</h1><a href='http://www.google.com'>CLICK</a></body></html>"

r = re.compile(r'<.*?>')
print(r.sub('', y))