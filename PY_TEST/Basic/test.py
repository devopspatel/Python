# import datetime
# 
# start = datetime.datetime.now()
# nl = []
# for i in range(1, 3000001):
#     if (i%2 == 0):
#         nl.append(i)
# 
# end = datetime.datetime.now()
# print (f'1 TIME DELTA: {end - start}')
# 
# 
# start = datetime.datetime.now()
# nl = list(i for i in range(1,3000001) if i%2 == 0)
# end = datetime.datetime.now()
# print (f'2 TIME DELTA: {end - start}')
# 
# start = datetime.datetime.now()
# nl = [i for i in range(1,3000001) if i%2 == 0]
# end = datetime.datetime.now()
# print (f'3 TIME DELTA: {end - start}')


import random

l = list(range(10, 0 , -1))
print (f' LIST: {l} >> MIN INDEX: {l.index(min(l))}')


l = list(random.randint(1, 10000) for _ in range(2500))
print (l)