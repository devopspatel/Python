class Solution(object):
    def convert(self, s, numRows):
        """
        :type s: str
        :type numRows: int
        :rtype: str
        """
        if (len(s) > 0):
            l = [['0' for _ in range(len(s))] for __ in range(numRows)]
        
        i = 0
        rowInv = 0
        cc = 0
        
        while (cc < len(s) and len(s) > 0 and len(s) > numRows > 1):
            print (f'WHILE LOOP: CC: {cc} >> S[cc]: {s[cc]}')
             
            for j in range(numRows):
                if (cc < len(s)):
                    print (f'J: {j} >> FOR LOOP: CC: {cc} >> S[cc]: {s[cc]}')
                    if (i == 0 or (i%(numRows-1) == 0 and rowInv == 0)):
                        print (f'IF >>>>>> I: {i} >> J: {j} >> CC: {cc} >> S[cc]: {s[cc]}')
                        l[j][i] = s[cc]
                        cc += 1
                        if (j == (numRows - 1)):
                            rowInv = numRows - 2
                    elif (rowInv > 0):
                        print (f'ELSE >>>>>> I: {i} >> J: {j} >> CC: {cc} >> S[cc]: {s[cc]}')
                        l[rowInv][i] = s[cc]
                        cc += 1
                        rowInv -= 1
                        i += 1
                        break
            else:
                i += 1
        
        ret_str = ''
        if (len(s) > 1 and len(s) > numRows > 1):
            for j in range(numRows):
                for i in range(len(s)):
                    if (l[j][i] != '0'):
                        ret_str += l[j][i]
        elif (numRows == 1 or len(s) <= numRows):
            ret_str = s
        
        return ret_str
        
s = Solution()
# print(s.convert('ABCDEFGHIJKLMN', 4))
# print(s.convert('PAYPALISHIRING', 3))
print(s.convert('A', 2))
# print(s.convert('AB', 1))

