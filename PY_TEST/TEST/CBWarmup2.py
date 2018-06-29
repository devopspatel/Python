def string_times(str, n):
	return str*n


def front_times(str, n):
	return str[:3]*n


def string_bits(str):
	result = ''
	for i in range(len(str)):
		if i%2 == 0:
			result += str[i]
	return result


def string_splosion(str):
	result = ''

	for i in range(len(str)):
		result += str[:i]
	result += str
	return result


def last2(str):
	count = 0
	ss = str[-2:]
	for i in range(len(str)-2):
		if(str[i]+str[i+1] == ss):
			count += 1
	return count


def array_count9(nums):
	count = 0
	for i in nums:
		if i == 9:
			count += 1
	return count


def array_front9(nums):
	count = 0
	c = 0
	for i in nums:
		if i == 9 and c < 4:
			count += 1
		c += 1
	return (count > 0)


def array123(nums):
	state = False
	for i in range(len(nums)-2):
		if nums[i] == 1 and nums[i+1] == 2 and nums[i+2] == 3:
			state = True
	return state


def string_match(a, b):
	l = len(a) if len(a)<len(b) else len(b)
	count = 0
	for i in range(l - 1):
		if (a[i]+a[i+1]) == (b[i]+b[i+1]): count += 1
	return count