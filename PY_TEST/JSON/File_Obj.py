#import json


#f = open('states.json', 'r')
#print('Name: ', f.name)
#print("Open Mode: ", f.mode)
#f.close()

# with open('Sample.txt', 'r') as f:
#     print('Name: ', f.name)
#     print('Mode: ', f.mode)
#     
#     size_to_read = 10
#     f_data = f.read(size_to_read)
#     
#     print(f.tell())
#     
#     f.seek(30)
#     
#     while len(f_data) > 0:
#         print(f_data, end='*')
#         f_data = f.read(size_to_read)


#with open('Sample2.txt', 'w') as f:
#     f.write('Dipen Desai')
#     f.write("Dhara Desai")
     
# Open / Close file Using standard method
fr = open('Sample.txt', 'r')
print(fr.read())

fr.close()