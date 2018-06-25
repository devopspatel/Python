d = [1,2,3,4,5]

l1 = lambda x: x*x
for i in d:
	print(l1(i))

print('D',d)

d1 = list(map(lambda x: x*x, d))
print('D1', d1)

d2 = list(map(lambda x: x+x, d))
print('D2', d2)
print('<= 6: ', [x for x in d2 if x <= 6])
print('> 6: ', list(filter(lambda x:x>6, d2)))