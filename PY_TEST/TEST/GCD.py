# GREATEST COMMON DENOMINATOR

class gcd():
    def gcd_func(self, m, n):
#         print (f'0 --- M: {m} >> N: {n}')
        while m % n != 0:
            m, n = n, m%n
#             print (f'M: {m} >> N: {n}')
        return n


gcd_obj = gcd()
print(gcd_obj.gcd_func(60, 12))