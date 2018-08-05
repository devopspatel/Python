# class Solution(object):
#     def reverse(self, x):
#         """
#         :type x: int
#         :rtype: int
#         """
#         default_insert = 0
#         ret_str = ''
#         
#         if (x < 0):
#             ret_str = '-'
#             default_insert = 1            
#         s = str(x)
#         for c in range(len(s) - 1, default_insert - 1, -1):
#             ret_str += s[c]
#             
#         if ((abs(x) > (2**31)) or (abs(int(ret_str)) >= (2**31))):
#             ret_str = 0
#         
#         return int(ret_str)
    
    
class Solution(object):
    def reverse(self, x):
        """
        :type x: int
        :rtype: int
        """
        mul = -1 if x < 0 else 1
        x = x * mul
        ret = 0
        
        while (x > 0):
            p = x%10
            x = x//10
            ret = ret*10 + p
            
        if ((abs(x) > (2**31)) or (abs(int(ret)) >= (2**31))):
            ret = 0
            
        return ret * mul
            
s = Solution()
n = -321
print (s.reverse(n))