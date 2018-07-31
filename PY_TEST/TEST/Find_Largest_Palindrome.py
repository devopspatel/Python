import datetime

class Solution(object):
    def longestPalindrome(self, s):
        """
        :type s: str
        :rtype: str
        """
        
        largest_size = ''
        
        for i in range(len(s), 0, -1):
            for j in range(0, len(s)-i + 1):
                temp_str = s[j:j+i]
#                 print (f'I: {i} >> J: {j} >> S[J:J + I]: {temp_str} >> REV TEMP STR: {temp_str[::-1]}')
                if (temp_str == temp_str[::-1]):
                    largest_size = temp_str
#                     print (f'Within IF >> LARGEST STR: {largest_size} >> I: {i} >> J: {j}')
                    break
            else:
                continue
            break
        return ''.join(largest_size)
        

start = datetime.datetime.now()
s = Solution()
# r = s.longestPalindrome('abcba')
r = s.longestPalindrome('1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111')
end = datetime.datetime.now()
print (f'LONGEST PALINDROME: {r} >> TIME DELTA: {end - start}')