import os
import re

# folder = '/home/DD/Videos/TBBT_S01/'
folder = '/video/TV Shows/The Big Bang Theory/The Big Bang Theory Season 06/'

files = os.listdir(folder)
pattern = re.compile (r'(S\d*)(E\d*)')

for file in files:
    se = pattern.findall(file)
    new_name = 'TBBT ' + se[0][0] + ' ' + se[0][1] + '.mkv'
    os.rename(folder + file, folder + new_name)
