# Various Math Commands in Python

print(1+1)
print(4-2)
print(4*5)
print(8/3)
print(2**5)
print(8%3)

print('Single Quote String')
print("Double Quote String")

name = "Dipen Narendrakumar Desai"
age = 34

print("My name is {n} and number is {a}, more {a}, more {a}".format(a=age, n=name))

print('Element 0 > ' + name[0])
print('Element 4 > ' + name[4])

print("From 4 ON > " + name[4:])
print("First 7 > " + name[:7])
print("Between 4 to 10 > " + name[4:20])

list = ['a', 'b', 'c']
print(list)
list.append('d')
list.append('e')
list.append('f')
list.append('g')
print(list)
print("First > " + list[0])

list[1] = "LOL"
print(list)

nest = [1, 2, 3, [4, 5, 6, ['Dipen', 'Dhara', 'Rishan', 'Riha']]]
print(nest)
print(nest[3])
print(nest[3][3])
print(nest[3][3][3])