import datetime
import random

def refactor_l():
#     l = [3,5,9,77,11,2]
#     l = list(range(2500, 0, -1))
    l = list(random.randint(1, 50000) for _ in range(250))
#     l = list(random.randint(1, 12) for _ in range(10))
    return l

def time_it(func):
    def wrapper(*args, **kwargs):
        start = datetime.datetime.now()
        print ('START: {0}'.format(start))
        result = func(*args, **kwargs)
        end = datetime.datetime.now()
        print ('END: {0}'.format(end))
        print ('{0} >> TIME DELTA: {1}'.format(func.__name__, end - start))
        return result
    return wrapper

####################################################################################################

# BASIC ASCENDING SORT - V1
@time_it
def ascending_sort_v1(l):
    loop_count = 0
    for i in range(len(l)):
        for j in range(i):
            l.insert(j, l.pop(i)) if l[i] < l[j] else ''
            loop_count += 1
#         print (f'\tAFTER J LOOP: {l}')
    print ('ASCENDING SORT V1 LOOP COUNT: {0}'.format(loop_count))
#     print (f'SORTED: {l}')
    return l

# BASIC ASCENDING SORT - V2
@time_it
def ascending_sort_v2(l):
    loop_count = 0
    for i in range(len(l)):
        l.insert(i, l.pop(l.index(min(l[i:len(l)]))))
        loop_count += 1
#         print (f'LOOP {i} > {l}')
    print ('ASCENDING SORT V2 LOOP COUNT: {0}'.format(loop_count))
#     print (f'SORTED: {l}')
    return l

####################################################################################################

# BUBBLE SORT > LOOP RUNS LEN(L) - 1 TIMES.  AT THE END OF EACH MAJOR LOOP, HIGHEST VALUES MOVES TO FAR RIGHT (END OF LIST)
# BUBBLE SORT COMPARE CURRENT AND NEXT ELEMENT IN THE LIST AND SWAPS THEM IF FIRST IS BIGGER THAND SECOND
@time_it
def bubble_sort(l):
    loop_count = 0
    for i in range(len(l) - 1):
        for j in range(len(l) - i - 1):
            l.insert(j, l.pop(j+1)) if (l[j] > l[j+1]) else ''
            loop_count += 1
#         print (f'\tAFTER J LOOP: {l}')
    print ('BUBBLE SORT V1 LOOP COUNT: {0}'.format(loop_count))
#     print (f'SORTED: {l}')
    return l

####################################################################################################

# # SELECTION SORT > LOOP RUNS LEN(L) - 1 TIMES.
# MOVE LARGER ELEMENT TO FAR RIGHT AFTER EACH LOOP
@time_it
def selection_sort(l):
    loop_count = 0
    for i in range(len(l) - 1):
        for j in range(len(l) - i - 1):
            l.insert(j + 1, l.pop(j)) if (l[j] > l[j + 1]) else ''
            loop_count += 1
    print ('SELECTION SORT V1 LOOP COUNT: {0}'.format(loop_count))
#     print (f'SORTED: {l}')
    return l

# NOT WORKING
@time_it
def selection_sort_v2(l):
    loop_count = 0
    for i in range(len(l), 0, -1):
        l.insert(i, l.pop(l.index(max(l[:i]))))
        loop_count += 1
    print ('SELECTION SORT V2 LOOP COUNT: {0}'.format(loop_count))
#     print (f'SORTED: {l}')
    return l

@time_it
def selection_sort_v3(l):
    loop_count = 0
    for i in range(len(l)):
        l.insert(0, l.pop(l.index(max(l[i:]), i)))
        loop_count += 1
    print ('SELECTION SORT V3 LOOP COUNT: {}'.format(loop_count))
#     print (f'SORTED: {l}')
    return l

@time_it
def selection_sort_v4(l):
    loop_count = 0
    for i in range(len(l):
        l.append(l.pop(l.index(min(l[:len(l)-i]))))
        loop_count += 1
    print ('SELECTION SORT V4 LOOP COUNT: {0}'.format(loop_count))
#     print (f'SORTED: {l}')
    return l

####################################################################################################

# l = refactor_l()
# ascending_sort_v1(l)
#
# l = refactor_l()
# ascending_sort_v2(l)
#
# l = refactor_l()
# bubble_sort(l)
#
# l = refactor_l()
# selection_sort(l)
#
# l = refactor_l()
# selection_sort_v2(l)
#
# l = refactor_l()
# selection_sort_v3(l)

l = refactor_l()
selection_sort_v4(l)

# l = refactor_l()
# l = sorted(l)
#
# l = refactor_l()
# l.sort()
