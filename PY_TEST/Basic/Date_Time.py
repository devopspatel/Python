import datetime

# DATE EXAMPLES
tday = datetime.date.today()
print(f'TODAY: {tday}')

print(f'YEAR: {tday.year}')
print(f'MONTH: {tday.month}')
print(f'DATE: {tday.day}')

print(f'DAY: {tday.weekday()}')
print(f'ISO DAY: {tday.isoweekday()}')

tdel = datetime.timedelta(days = 7)
print(f'+7 DAYS: {tday + tdel}')
print(f'-7 DAYS: {tday - tdel}')

# Number
print(f'DAYS TILL BDAY: {(datetime.date(2018,9,14) - datetime.date.today()).days}')


# TIME EXAMPLES
t = datetime.time(9,17,0)
print(f'TIME: {t}')

# DATETIME
dt = datetime.datetime(1981, 9, 14, 11, 55, 55, 200)
print(f'DATE TIME: {dt}')


# START AND END TIME - 2 HOUR DIFFERENCE
# current_time = datetime.datetime.today()
current_time = datetime.datetime.utcnow()
tdel = datetime.timedelta(hours = 2)
start_time = current_time - tdel

print(f'START: {start_time.time()} >>> END: {current_time.time()}')

# FIX TIMES:
e_time = datetime.datetime(2018,7,2,10,0,0,0)
s_time = e_time - tdel
print(f'START: {s_time} >>> END: {e_time}')