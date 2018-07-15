import requests
import json

r = requests.get("https://api.github.com/rate_limit")
d = r.json()

print(f'JSON: {d}')
print(f"LIMIT: {d['resources']['core']['limit']}")