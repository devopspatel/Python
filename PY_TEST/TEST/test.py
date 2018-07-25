class Solution(object):
    def longestPalindrome(self, s):
        """
        :type s: str
        :rtype: str
        """
        
        largest_size = ''
        
        for i in range(1, len(s) + 1):
            for j in range(len(s)-i + 1):
                temp_str = s[j:j+i]
                print (f'I: {i} >> J: {j} >> S[J:J + I]: {temp_str}')
                if (temp_str == temp_str[::-1]):
                    largest_size = temp_str
                    j = len(s) + 10
        
        return ''.join(largest_size)
    
    
s = Solution()
r = s.longestPalindrome('abcda') 
print (r)