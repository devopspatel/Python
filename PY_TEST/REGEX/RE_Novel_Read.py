#!/usr/bin/python

import re

class reduce_func():
    def red_v1 (self):
        sums = {}
        with open('Novel.txt', 'r') as fr:
            txt = fr.readline()
            while (len(txt) > 0):
                txt = re.sub(r'^\W+|\W+$', '', txt)
                words = re.split(r'\W+', txt)
            
#                 print (f'LINE: {txt} >> WORDS: {words}')
            
                for word in words:
                    word = word.lower()
                    sums[word] = sums.get(word, 0) + 1
                txt = fr.readline()
        
        return sums

    def red_v2(self):
        with open('Novel.txt', 'r') as fr:
            txt = fr.readline()
            
            while (len(txt) > 0):
                txt = re.sub(r'^\W+|\W+$', '', txt)
                words = re.split(r'\W+', txt)
            
                for word in words:
                    print (word.lower() + '\t1')
                txt = fr.readline()
        
        return ''

ob = reduce_func()

print (ob.red_v1())
print (ob.red_v2())