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


# Strip > Remove white spaces
# Map + Lambda + Sort > Sort names using last name after using split
l = ['Dipen Shah  ', '  Raj Mukhi  ', ' Dhara Dipen Desai', 'Chintan Patel', 'Disha Patani', 'Priyanka Nick Chopra']

l = list(map(lambda x: x.strip(), l))
print (l)

l.sort(key=lambda x: x.split(' ')[-1].lower())
print (l)
