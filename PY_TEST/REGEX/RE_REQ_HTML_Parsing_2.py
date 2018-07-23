import re
import requests as req

# Get HTML Text
r = req.get("https://docs.python.org/2/library/textwrap.html")
y = r.text.encode(encoding='utf_8', errors='strict')
print (y)


# Remove HTML Tags
ns = re.sub(r'<.*?>', '\n', y)
print (ns)

# 
# # Extract Texts
# ns = re.findall(r'(\w.+)\n+?', ns)
# for n in ns:
#     print (n)