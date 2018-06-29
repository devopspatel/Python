def double_char(str):
	fin = ''
	for c in str:
		fin += c*2
	return fin


def count_hi(str):
	count = 0
	for i in range(len(str)-1):
		if (str[i] + str[i+1] == "hi"):
			count += 1
	return count


def cat_dog(str):
	cCount = 0
	dCount = 0

	for i in range(len(str)-2):
		if (str[i:i+3] == 'cat'):
			cCount += 1
		elif (str[i:i+3] == 'dog'):
			dCount += 1

	return (cCount == dCount)


def count_code(str):
	count = 0
	for i in range(len(str)-3):
		if (str[i:i+2] == 'co' and str[i+3] == 'e'):
			count += 1
	return count


def end_other(a, b):
	status = False
	a = a.lower()
	b = b.lower()
	if (len(a) < len(b)):
		if (a == b[-len(a):]):
			status = True
	else:
		if (b == a[-len(b):]):
			status = True
	return status


def xyz_there(str):
	status = False
	for i in range(len(str)-2):
		if (i > 0 and str[i:i+3] == 'xyz' and str[i-1] != '.'):
			status = True
		elif (i == 0 and str[i:i+3] == 'xyz'):
			status = True
	return status