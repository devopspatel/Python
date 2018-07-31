class Solution(object):
    def individuals(self, num, c1, c5, c10):
        
        ret_str = ''
        if (num == 9):
            ret_str = c1 + c10
        elif (num == 4):
            ret_str = c1 + c5
        else:
            if (num >= 5):
                ret_str = c5
                num = num - 5
            for i in range(0, num):
                ret_str += c1
        
        return ret_str
    
    def int_to_roman(self, num):
        roman = ''
        no = Solution()
        
        for i in range(3, -1, -1):
            reminder = (int(num/(10**i)))%10
            
            if (reminder > 0 and i == 3):
                roman += no.individuals(reminder, 'M', '', '')
            elif (reminder > 0 and i == 2):
                roman += no.individuals(reminder, 'C', 'D', 'M')
            elif (reminder > 0 and i == 1):
                roman += no.individuals(reminder, 'X', 'L', 'C')
            elif (reminder > 0 and i == 0):
                roman += no.individuals(reminder, 'I', 'V', 'X')
            
        return roman

num = 3999
s = Solution()
r = s.int_to_roman(num)
print (f'INT: {num} >> ROMAN: {r}')