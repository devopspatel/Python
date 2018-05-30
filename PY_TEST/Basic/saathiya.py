def fun(x):
	print(x, end="")
	
o = range(1, 21)

for i in o:
	for j in o:
		if ((i == 1) and (j > 10)) or ((j == 1) and (i < 10)) or (i == 10) or (j == 10) or ((i == 20) and (j < 10)) or ((j == 20) and (i > 10)) or (((i == 5) or (i == 15)) and ((j == 5) or (j == 15))):
			fun('*')
		else:
			fun(' ')
	fun('\n')