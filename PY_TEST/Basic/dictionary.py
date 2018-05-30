# Dictionary
d = {'key1':'abc', 'key2':123, 'key3':'Rishan'}
print(d['key1'])

d = {'k':[1, 2, 3]}
print(d['k'][2])

d1 = {'k1':1, 'k2':2}
print(d)

print("KEYS > {}".format(d1.keys()))
print("ITEMS > {}".format(d.items()))

print(d1.get('k1'))

# List
l = [1, 2, 3]
print(l[0])

print("IN {}".format(1 in l))

l[0] = 'RRDD'
print(l)
print(l[0])

# Tuple
t = (1, 2, 3)
print(t[0])

# Set
s = {1, 2, 3, 4}
print(s)
s.add(5)
print(s)
s.add(5)
print(s)


#xs = [(1, 'A', 'a'), (2, "B", 'b'), (3, "C", ''), (4, 'D', 'd')]
xs = [[1, 'A', 'a'], [2, "B", 'b'], [3, "C", ''], [4, 'D', 'd']]

for a,b,c in xs:
    print(c)