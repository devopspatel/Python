# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")

def solution(S, C):
    emails = []
    temp_e = ''
    names = S.split(', ')
    
    for name in names:
        temp = name.replace('-', '').split(' ')
        
        if (len(temp) == 2):
            temp_e = temp[1].lower() + '_' + temp[0][0].lower()
        else:
            temp_e = temp[2].lower() + '_' + temp[0][0].lower() + '_' + temp[1][0].lower()
        
        emails.append(temp_e + '@' + C.lower() + '.com')
    return ', '.join(emails)
    
#     for i in range(emails)
        
S = 'John Doe, Peter Parker, Marry Jane Watson-Parker, James Doe, John Elvis Doe, Jane Doe, Penny Parker'
C = 'example'

bb = solution(S, C)
print (bb)


###############################################################################################

# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")

# def solution(A, K, L):
#     apples = -1
#     # write your code in Python 3.6
#     temp_apple = 0
#     ka, la = 0, 0
#     
#     if ((K + L) < len(A)):
#         for i in range(len(A)-K+1):
#             ka = sum(A[i:i+K])
#             for j in range(len(A) - L+1):
#                 if (i <= j <= i+K and i <= j+L <= i+K):
#                     pass
#                 else:
#                     la = sum(A[j:j+L])
#                     print (f'J: {j} >> LA: {la}')
#                     if (ka + la > apples):
#                         apples = ka + la
#                         print ()
#     elif ((K + L) == len(A)):
#         apples = sum(A)
#         
#     return apples
# 
# 
# A = [6,1,4,6,3,2,7,4]
# print(solution(A, 3, 2))