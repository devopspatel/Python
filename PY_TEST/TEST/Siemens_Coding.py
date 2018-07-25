# 
# 
# data structure
# 1, 4, 6, 3, 12
# 1, 4, 6, 3, 12
# 
# a1, a4, r=1, a6, r=6, r=4
# 
# a1, a4 - 1, 4
# r=1 - 4
# a6 - 4, 6
# r=4, 6
# r=6
# 
# 

l = []

def fifo_user(i):
    ret_val = ''
    
    if (i[0] == 'a'):
        l.append(i[1:])
        ret_val = f'{i[1:]} added'
    elif (i == 'r' and len(l) > 0):
        ret_val = l.pop(0)
    else:
        ret_val = 'Invalid'
    
    return ret_val

print(fifo_user('a1'))
print(fifo_user('a2'))
print(fifo_user('r'))
print(fifo_user('a3345'))
print(fifo_user('aabc'))
print(fifo_user('a5b'))
print(fifo_user('r'))
print(fifo_user('r'))
     
# l = list()
# q = queue()
# q.put(1)    
# print(q.get())
 
 
####################################################################################################################
class Queue:
    def __init__(self):
        self.l = list()
    
    def put(self, x):
        self.l.append(x)
        print(f'PUT: {self.l}')
     
    def get(self):
        return (self.l.pop(0))

q = Queue()
q.put(1)
print(q.get())
q.put(2)
q.put(3)
q.put(4)
print(q.get())

####################################################################################################################

# bubble sort
# merge sort
# insertion sort

def my_sort(l):
#     sorted_l=[]
#     for i in range(len(l)):
#         if (i == 0):
#             sorted_l.append(l[i])
#         else:
#             for j in range(len(sorted_l)):
#                 if (l[i] < sorted_l[j]):
#                     sorted_l.insert(j, l[i])
#                     break
#                 elif (j == len(sorted_l) - 1):
#                     sorted_l.append(l[i])
#                     break
#     return sorted_l

    for i in range(len(l)):
        l.append(l.pop(l.index(min(l[:len(l)-i]))))
    return l
 
unordered_list = [3,5,9,77,11,2]
print(my_sort(unordered_list)) # => 2, 3, 5, 9, 11, 77