import re
import requests as req

# Get HTML Text
r = req.get("http://dev-env.ps2bkca3sj.us-east-1.elasticbeanstalk.com/")
y = r.text

# Remove HTML Tags
ns = re.sub(r'<.*?>', '\n', y)
# print (ns)

# Remove Extra Line Breaks (Not Mandatory)
# ns = re.sub(r'\n+', '\n', ns)
# print (ns)

# Extract Texts
ns = re.findall(r'(\w.+)\n', ns)
for n in ns:
    print (n)