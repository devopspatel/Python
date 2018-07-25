import datetime
import random
from lib2to3 import refactor

# BASIC ASCENDING SORT - V1
def ascending_sort(l):
    loop_count = 0
    for i in range(len(l)):
        for j in range(i):
            l.insert(j, l.pop(i)) if l[i] < l[j] else ''
            loop_count += 1
#         print (f'\tAFTER J LOOP: {l}')
    print (f'ASCENDING SORT V1 LOOP COUNT: {loop_count} >> {l}')
    return l
 
# BASIC ASCENDING SORT - V2
def ascending_sort_v2(l):
    loop_count = 0
    for i in range(len(l)):
        l.insert(i, l.pop(l.index(min(l[i:len(l)]))))
        loop_count += 1
#         print (f'LOOP {i} > {l}')
    print (f'ASCENDING SORT V2 LOOP COUNT: {loop_count} >> {l}')
    return l

####################################################################################################

# BUBBLE SORT > LOOP RUNS LEN(L) - 1 TIMES.  AT THE END OF EACH MAJOR LOOP, HIGHEST VALUES MOVES TO FAR RIGHT (END OF LIST)
# BUBBLE SORT COMPARE CURRENT AND NEXT ELEMENT IN THE LIST AND SWAPS THEM IF FIRST IS BIGGER THAND SECOND
def bubble_sort(l):
    loop_count = 0
    for i in range(len(l) - 1):
        for j in range(len(l) - i - 1):
            l.insert(j, l.pop(j+1)) if (l[j] > l[j+1]) else ''
            loop_count += 1
#         print (f'\tAFTER J LOOP: {l}')
    print (f'BUBBLE SORT V1 LOOP COUNT: {loop_count} >> {l}')
    return l

####################################################################################################

# # SELECTION SORT > LOOP RUNS LEN(L) - 1 TIMES.
# MOVE LARGER ELEMENT TO FAR RIGHT AFTER EACH LOOP
def selection_sort(l):
    loop_count = 0
    for i in range(len(l) - 1):
        for j in range(len(l) - i - 1):
            l.insert(j + 1, l.pop(j)) if (l[j] > l[j + 1]) else ''
            loop_count += 1
    print (f'SELECTION SORT V1 LOOP COUNT: {loop_count} >> {l}')
    return l

# NOT WORKING
def selection_sort_v2(l):
    loop_count = 0
    for i in range(len(l), 0, -1):
        l.insert(i, l.pop(l.index(max(l[:i]))))
        loop_count += 1
    print (f'SELECTION SORT V2 LOOP COUNT: {loop_count} >> {l}')
    return l

def selection_sort_v3(l):
    loop_count = 0
    for i in range(len(l)):
        l.insert(0, l.pop(l.index(max(l[i:]), i)))
        loop_count += 1
    print (f'SELECTION SORT V3 LOOP COUNT: {loop_count} >> {l}')
    return l

def selection_sort_v4(l):
    loop_count = 0
    for i in range(len(l)):
        l.append(l.pop(l.index(min(l[:len(l)-i]))))
        loop_count += 1
    print (f'SELECTION SORT V4 LOOP COUNT: {loop_count} >> {l}')
    return l

####################################################################################################
def refactor_l():
#     l = [3,5,9,77,11,2]
#     l = list(range(2500, 0, -1))
    l = list(random.randint(1, 50000) for _ in range(2500))
#     l = list(random.randint(1, 12) for _ in range(10))
    return l

####################################################################################################

l = refactor_l()
start = datetime.datetime.now()
ascending_sort(l)
end = datetime.datetime.now()
print (f'ASCENDING SORT V1 TIME DELTA: {end - start}')
  
l = refactor_l()
start = datetime.datetime.now()
ascending_sort_v2(l)
end = datetime.datetime.now()
print (f'ASCENDING SORT V2 TIME DELTA: {end - start}')
 
l = refactor_l()
start = datetime.datetime.now()
bubble_sort(l)
end = datetime.datetime.now()
print (f'BUBBLE SORT V2 TIME DELTA: {end - start}')
 
l = refactor_l()
start = datetime.datetime.now()
selection_sort(l)
end = datetime.datetime.now()
print (f'SELECTION SORT V1 TIME DELTA: {end - start}')
 
l = refactor_l()
start = datetime.datetime.now()
selection_sort_v2(l)
end = datetime.datetime.now()
print (f'SELECTION SORT V2 TIME DELTA: {end - start}')
 
l = refactor_l()
start = datetime.datetime.now()
selection_sort_v3(l)
end = datetime.datetime.now()
print (f'SELECTION SORT V3 TIME DELTA: {end - start}')

l = refactor_l()
start = datetime.datetime.now()
selection_sort_v4(l)
end = datetime.datetime.now()
print (f'SELECTION SORT V4 TIME DELTA: {end - start}')

l = refactor_l()
start = datetime.datetime.now()
l = sorted(l)
end = datetime.datetime.now()
print (f'SORTED FUNCTION TIME DELTA: {end - start} >> {l}')

l = refactor_l()
start = datetime.datetime.now()
l.sort()
end = datetime.datetime.now()
print (f'SORTED METHOD TIME DELTA: {end - start} >> {l}')
