def make_bricks(small, big, goal):
	final = False

	if (goal / 5 <= big and goal % 5 <= small):
		final = True
	elif (goal / 5 > big and (goal - 5*big) <= small):
		final = True

	return final


def lone_sum(a, b, c):
	sum = 0

	if (a == b == c):
		sum = 0
	else:
		if (a == b):
			sum = c
		elif (a == c):
			sum = b
		elif (b == c):
			sum = a
		else:
			sum = a + b + c

	return sum


def lucky_sum(a, b, c):
	  sum = a + b + c
  
	if (a == 13):
		sum = 0
  	elif (b == 13):
      	sum = a
  	elif (c == 13):
	    sum = a + b
  
  	return sum


def no_teen_sum(a, b, c):
 	return fix_teen(a) + fix_teen(b) + fix_teen(c)
def fix_teen(n):
	if(n in range(13,20) and (n not in range(15, 17))):
		return 0
	else:
		return n


def round_sum(a, b, c):
	return round10(a) + round10(b) + round10(c)
def round10(num):
	if (num%10 < 5):
		return num - num%10
	else:
		return num - num%10 + 10


def close_far(a, b, c):
  	final = False
  	x = abs(a-b) <= 1 and abs(a-c) >= 2
  	y = abs(a-c) <= 1 and abs(a-b) >= 2
  	z = abs(b-c) >= 2
  	if (z and ((not x and y) or (x and not y))):
	    final = True
  	return final


def make_chocolate(small, big, goal):
	final = -1

	if (goal / 5 <= big and goal % 5 <= small):
		final = goal%5
	elif (goal / 5 > big and (goal - 5*big) <= small):
		final = goal - 5*big

	return final