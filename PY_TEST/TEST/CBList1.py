def first_last6(nums):
	return (nums[0] == 6 or nums[-1] == 6)


def same_first_last(nums):
	return (len(nums) >= 1 and (nums[0] == nums[-1]))


def make_pi():
	pi = round(22.00/7.00, 2)
	return([int(pi%10), int((pi*10)%10), int((pi*100)%10)])


def common_end(a, b):
	final = False
	if((a[0] == b[0]) or (a[-1] == b[-1])):
		final = True
	return final


def sum3(nums):
	#f = lambda x: x += x in nums
	#return (f(nums))
	return sum(nums)

print(make_pi())