def cigar_party(cigars, is_weekend):
	status = False
	if (cigars >= 40 and cigars <= 60):
		status = True
	elif (cigars >= 40 and is_weekend):
		status = True
	return status


def date_fashion(you, date):
	result = 0
	if (you <= 2 or date <= 2):
		result = 0
	elif (you >= 8 or date >= 8):
		result = 2
	else:
		result = 1
	return result


def squirrel_play(temp, is_summer):
	play = False
	if (60 <= temp <= 90):
		play = True
	elif (is_summer and 60 <= temp <= 100):
		play = True

	return play


def caught_speeding(speeding, is_birthday):
	ticket = 0
	if is_birthday:
		speeding -= 5
	if (speeding >= 81):
		ticket = 2
	elif(speeding >= 61):
		ticket = 1
	return ticket


def sorta_sum(a, b):
	return (20 if 10 <= a+b <= 19 else a+b)


def alarm_clock(day, vacation):
	alarm = '7:00'

	if vacation:
		if (day == 0 or day == 6):
			alarm = 'off'
		else:
			alarm = '10:00'
	else:
		if (day == 0 or day == 6):
			alarm = '10:00'
	return alarm


def love6(a, b):
	return (a == 6 or b == 6 or a+b == 6 or abs(a-b) == 6)


def in1to10(n, outside_mode):
	result = False
	if (not outside_mode):
		result = n in range(1, 11)
	elif(outside_mode):
		result = n not in range(2, 10)

	return result


def near_ten(num):
	result = True
	if ((num % 10) in range (3, 8)):
		result = False
	return result


