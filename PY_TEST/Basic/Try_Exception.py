#f = open('Sample1.txt')

try:
	f = open('Sample9.txt')
	if (f.name == 'Sample.txt'):
		raise Exception
	#var = bad_var
except FileNotFoundError as e:
	print(e)
except Exception as e:
	print(e, 'Manually Raised')
else:
	print(f.read())
	f.close()
finally:
	print('FINALLY')