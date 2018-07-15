d = [1, 2, 3, 4, 5, 6, 7, 8]

d_square = list(map(lambda x: x*x, d))
print (f'D_SQUARE: {d_square}')

d_add = list(map(lambda x: x+x, d))
print (f'D_ADD: {d_add}')

d_power = list(map(lambda x: x**x, d))
print (f'POWER: {d_power}')

str = '0123456789'
print (f'EVEN: {str[::2]}')
print (f'ODD: {str[1::2]}')


num = [n for n in d if n==7]
print(num)