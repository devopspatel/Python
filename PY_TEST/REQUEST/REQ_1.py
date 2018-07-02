import re
import urllib.request
import urllib.parse

url = 'http://pythonprogramming.net'
values = {'s': 'basics',
          'submit': 'search'}

data = urllib.parse.urlencode(values)
data = data.encode('utf-8')
req = urllib.request.Request(url,data)
resp = urllib.request.urlopen(req)
respData = resp.read()

# print(respData)

paras = re.finditer(r'<p>(.*?)</p>', str(respData))

for para in paras:
    print(para[1])