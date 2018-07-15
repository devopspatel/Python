# 1. 
# Write a short program that prints each number from 1 to 100 on a new line. 
# For each multiple of 3, print "Fizz" instead of the number. 
# For each multiple of 5, print "Buzz" instead of the number. a
# For numbers which are multiples of both 3 and 5, print "FizzBuzz" instead of the number.
# 2. 
# Count character occurrence in a string.
# i.e. book -> { b -> 1, o -> 2, k -> 1 }
# 3.
# Find longest consecutive subsequence in an integer array.
# Given an array of integers, find the length of the longest sub-sequence such that elements in the subsequence are consecutive integers, the consecutive numbers can be in any order.
# i.e.
# Input: arr[] = {1, 9, 3, 10, 4, 20, 2};
# Output: 4
# The subsequence 1, 3, 4, 20 is the longest subsequence of consecutive elements
# Input: [10,9,2,5,3,7,101,18]
# Output: 4 
# Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.

# Problem 1
for i in range(1,101):
    if (i%3 == 0):
        if (i%5 == 0):
            print ('FizzBuzz')
        else: 
            print ('Fizz')
    elif (i%5 == 0):
        print ('Buzz')
    else:
        print(i)


for x in range(1,31):
    val = ''
    if x%3==0:
        val = val + 'Fizz'
    if x%5==0:
        val = val + 'Buzz'
    if (len(val) == 0):
        val = x
    print (val)

# Problem 2
str = 'Peter'

str_dict = {}

for i in range(len(str)):
    if (str[i] in str_dict.keys()):
        str_dict[str[i]] = str_dict[str[i]] + 1
    else:
        str_dict[str[i]] = 1
    
print(str_dict)


# Problem 3
lst = [10,9,2,5,3,7,101,18] # V1: COUNT GOOD >> SEQ BAD --- V2: COUNT GOOD >> SEQ GOOD
# lst = [1, 20, 2, 19, 3, 18, 4, 17, 5, 16, 6, 15, 7] # V1: COUNT GOOD >> SEQ BAD --- V2: COUNT BAD >> SEQ BAD
# lst = [500, 1, 3, 5, 7, 28, 26, 24, 22] # V1: COUNT GOOD >> SEQ BAD --- V2: COUNT GOOD >> SEQ GOOD
# lst = [500, 1, 3, 5, 7, 3, 20, 21, 22, 23, 24] # COUNT GOOD >> SEQ BAD --- V2: COUNT GOOD >> SEQ GOOD
# lst = [1, 15, 2, 16, 3, 17, 4, 18, 5, 19, 6, 20, 7] # V1: COUNT GOOD >> SEQ GOOD --- V2: COUNT GOOD >> SEQ GOOD
# lst = [500, 1, 15, 2, 16, 3, 17, 400, 4, 18, 5, 19, 6, 20, 7] # V1: COUNT BAD >> SEQ BAD --- V2: COUNT BAD >> SEQ BAD
# lst = [501, 1, 3, 5, 7, 502, 11, 2, 4, 6, 499, 498, 503, 8, 10] # V1: COUNT BAD >> SEQ BAD --- V2: COUNT GOOD >> SEQ GOOD

def find_seq_v1(lst):
    final_ans = 0
    final_list = []
    
    for i in range(len(lst)):
        temp_ans = 1
        temp_list = [lst[i]]
        for j in range((i+1), len(lst)):
            if (lst[j-1] < lst[j]):
                temp_ans += 1
                temp_list.append(lst[j])
        if (temp_ans > final_ans):
            final_ans = temp_ans
            final_list = temp_list
#         print (f'TEMP LENGTH: {temp_ans} > TEMP LIST: {temp_list}')
    return (f'FINAL LENGTH: {final_ans} > FINAL LIST: {final_list}')
    


def find_seq_v2(lst):
    final_ans = 0
    final_list = []
    
    for i in range(len(lst)):
        temp_ans = 1
        temp_list = [lst[i]]
        for j in range((i+1), len(lst)):
            bool = True
            for t in range(len(temp_list)):
                if (temp_list[t] > lst[j]):
                    bool = False    
            if bool:
                temp_ans += 1
                temp_list.append(lst[j])
        if (temp_ans > final_ans):
            final_ans = temp_ans
            final_list = temp_list
#         print (f'TEMP LENGTH: {temp_ans} > TEMP LIST: {temp_list}')
    return (f'FINAL LENGTH: {final_ans} > FINAL LIST: {final_list}')

print(find_seq_v2(lst))

