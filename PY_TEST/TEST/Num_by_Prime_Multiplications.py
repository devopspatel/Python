class npm():
    def get_next_prime (self, start, num):
        ret_val = 2
        
        for i in range(start, num + 1):
            if (num%i == 0):
                ret_val = i
                break
        
        return ret_val
    
    def prime_mul (self, num):
        no = npm()
        str = [1]
        i = 2
        
        while (num%i == 0 or num > 1):
            if (num%i == 0):
#                 if i not in str:
#                     str.append(i)
                str.append(i)
                num = num//i
            else:
                i = no.get_next_prime(i + 1, num)
        return str
    
b = npm()
print (f'20 >> {b.prime_mul(20)}')
print (f'9 >> {b.prime_mul(9)}')
print (f'256 >> {b.prime_mul(256)}')
print (f'8192 >> {b.prime_mul(8192)}')
print (f'30030 >> {b.prime_mul(30030)}')
print (f'121 >> {b.prime_mul(121)}')
print (f'45930 >> {b.prime_mul(45930)}')
print (f'2 >> {b.prime_mul(2)}')
print (f'1 >> {b.prime_mul(1)}')
print (f'300 >> {b.prime_mul(300)}')
print (f'2100 >> {b.prime_mul(2100)}')
print (f'23100 >> {b.prime_mul(23100)}')