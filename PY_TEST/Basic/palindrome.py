class Solution(object):
    def isPalindrome(self, x):
        """
        :type x: int
        :rtype: bool
        """
        # b = str(i)
        # br = b[::-1]
        # return True if (b == br) else False
        return True if (str(x) == str(x)[::-1]) else False

i = 15544551
x = Solution()
print (x.isPalindrome(i))
