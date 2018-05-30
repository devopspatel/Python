# Conditions
if (1 == 2):
	print('Wow')
elif 3>4:
	print('Second')
else:
	print('final')
	
	
seq = [1,2,3,4,5]
for item in seq:
	print(item)
	
i = 1
while i<=5:
	print('i is {}'.format(i))
	i = i + 1
	
for x in range(0, 10):
	print('x is {}'.format(x))
	
l = range(7)
o = []
for n in l:
	o.append(n**2)
print(o)

o = [num+num**2 for num in l]
print(o)

