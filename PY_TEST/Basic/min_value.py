import random
import datetime

# rl = list(random.randint(1, 30) for _ in range(10))
rl = list(random.randint(1, 30000) for _ in range(10000))
print (rl)

start = datetime.datetime.now()
min_val = rl[0]
for i in range(len(rl)):
    for j in range(len(rl)):
        if (rl[i] < rl [j] and rl[i] < min_val):
            min_val = rl[i]
end = datetime.datetime.now()

print (f'1 --- FINAL MIN VAL: {min_val} >> TIME DELTA: {end - start}')


start = datetime.datetime.now()
min_val = rl[0]

for i in range(1, len(rl) - 1):
    if (rl[i] < rl[i + 1] and rl[i] < min_val):
        min_val = rl[i]
end = datetime.datetime.now()
print (f'2 --- FINAL MIN VAL: {min_val} >> TIME DELTA: {end - start}')