import datetime

start = datetime.datetime.now()

n = 310
nl = []

for i in range(1, 3000001):
    if (i%2 == 0):
        nl.append(i)

# print (f'EVENs >> {nl}')
end = datetime.datetime.now()
print (f'TIME DELTA: {end - start}')


start = datetime.datetime.now()
# nl = list(i for i in range(1,1000001) if i%2 == 0)
nl = [i for i in range(1,3000001) if i%2 == 0]
# print (f'2 EVENs >> {nl}')
end = datetime.datetime.now()
print (f'2 TIME DELTA: {end - start}')