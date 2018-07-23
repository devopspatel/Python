import re

with open('phone.txt', 'r') as f:
    f_data = f.readline()
    pp = re.compile(r'\D?(\d{3})?\D*?(\d{3})\D*?(\d{4})')
    p_clean = re.compile(r'[-]?(\d\D)[-]')
    
    while (len(f_data) > 0):
        if (f_data != '\n'):
            phone = pp.findall(f_data)
            f_data = f_data[:-1]
            
#             new_phone = ''
#             for i in range(len(phone[0])):
#                 new_phone += phone[0][i] + '-'
            
            if (len(phone) > 0):
                new_phone = phone[0][0] + '-' + phone[0][1] + '-' + phone[0][2]
            else:
                new_phone = 'ERROR'
            
            print (f'ORI F_DATA: {f_data} >> PHONE:{new_phone}')
        f_data = f.readline()