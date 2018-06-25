import json
from urllib.request import urlopen

with urlopen("https://finance.yahoo.com/webservice/v1/symbols/allcurrencies/quote?format=json") as response:
    data = json.loads(response.read())
# >>> ORIGINAL READ.  SOMEHOW IT HAD A BLANK VALUES IN BETWEEEN HENCE DATA GOT CONVERTED TO JSON FILE AND READ FROM IT AFTER MINOR FIX.

# CREATE A JSON FILE USING BELOW CODE
#with open('yahoo.json', 'w') as wf:
#    json.dump(data, wf, indent=2)

#with open ('yahoo.json', 'r') as rf:
#    data = json.loads(rf.read())
#print(json.dumps(data, indent=2))

usd_rates = dict()

for item in data['list']['resources']:
    try:
        name = item['resource']['fields']['name']
        price = item['resource']['fields']['price']
    except Exception as e:
        print("Exception: ", e)
        continue
    else:
        usd_rates[name] = price
        print(name, price)
    
print("EUR RATE: ", usd_rates['USD/EUR'])

print('50 USD = ',50*float(usd_rates['USD/INR']), 'INR')