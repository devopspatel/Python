def sq(x):
	return x*x
	
l = range(40)
m = l

l = list(map(sq,l))
print(l)

m = list(map(lambda x:x**3, m))
print(m)

m = list(filter(lambda x: x%13 == 0, l))
print(m)