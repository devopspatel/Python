import re
import requests as req

x = "Dipen Desai"
y = f"""
<html>
  <head>
    <title>Look at this</title>
  </head>
  <body>
    <h1>{x}</h1>
    <a href='http://www.google.com'>CLICK</a>
  </body>
</html>
"""
ns = re.sub(r'<.*?>', '', y)
# print (f'WITHOUT HTML TAGS: {ns}')
 
ns = re.findall(r'(\w.+)\n', ns)
for n in ns:
    print (n)