from prompt_toolkit import enums
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
	return sum(nums)


def make_pi():
    pi = round(22.0 / 7.0, 2)
    return ([int(pi % 10), int(pi * 10 % 10), int(pi * 100 % 10)])


def common_end(a, b):
    return ((a[0] == b[0]) or (a[-1] == b[-1]))


def sum3(nums):
    return sum(nums)


def rotate_left3(nums):
	nums.append(nums[0])
	nums.remove(nums[0])
	return(nums)


def reverse3(nums):
	return nums[::-1]


def max_end3(nums):
	x = nums[0] if nums[0] > nums[-1] else nums[-1]
	return(nums)


def sum2(nums):
	sum = 0
	if len(nums) > 1:
		sum = nums[0] + nums[1]
	elif len(nums) > 0:
		sum = nums[0]
	return sum
	
	
def middle_way(a, b):
	return([a[1],b[1]])


def make_ends(nums):
	return ([nums[0], nums[-1]])


def has23(nums):
	return (2 in nums or 3 in nums)