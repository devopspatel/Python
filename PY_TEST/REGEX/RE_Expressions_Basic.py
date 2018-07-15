import re

text_to_search = '''
abcdefghijklmnopqrstuvwxyz
ABCDEFGHIJKLMNOPQRSTUVWXYZ
1234567890

Ha HaHa

MetaCharacters (Need to be escaped):
. _ ^ $ * + ? { } [ ] \ | ( )

coreyms.com

321-555-4321
123.555.1234
440*999*8765
800-777-7777
900.888.8888


Mr. Schafer
Mr Smith
Ms Davis
Mrs. Robinson
Mr. T

cat
mat
sat
hat
bat
Lat
Yat

'''

sentense = 'Start a sentence and then bring it to an end'

# pattern = re.compile(r'\d\d\d[.-]\d\d\d[.-]\d\d\d\d')
# pattern = re.compile(r'[89]\d\d[.-]\d\d\d[.-]\d\d\d\d')
# pattern = re.compile(r'\s')
# pattern = re.compile(r'[^b]at')
# pattern = re.compile(r'\d{3}.\d{3}.\d{4}')
# pattern = re.compile(r'Mr\.?\s[A-Z]\w*')
pattern = re.compile(r'(Mr|Mrs|Ms)\.?\s[A-Z]\w*')

matches = pattern.finditer(text_to_search)
#matches = pattern.finditer(sentense)

for match in matches:
    print(match)