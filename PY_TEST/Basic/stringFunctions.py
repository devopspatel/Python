str = "Dipen Desai"

print(str.lower())
print(str.capitalize())
print(str.upper())
new_str = str.split()
print(f'{new_str[0]} {new_str[1]}')
print(str.casefold())

print ('Dipen\'str Python Program')
print('''This
is
a
multi-line
string.''')

print(f'Length is {len(str)}')

for i in range(len(str)-1):
    print(f'Char {i} is {str[i]}')
    
print ('First 5: ' + str[:5])
print ('Last 5: ' + str[-5:])

char = 'D'
findStr = 'Desai'
print (f'Number of {char} in {str} are {str.count(char)}')
print (f'Finding "{findStr}" in {str} is {str.find(findStr)}')

print(str.replace('Dipen', 'Riha')) # Replacement is NOT in place.
print(str)                          # Original variable still holds original value

first_name = str[:5]
last_name = str[-5:]

final_msg = f'{first_name} {last_name}, Welcome!'

print(dir(str))