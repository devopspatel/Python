import time
def deco_func(func):
    def wrapper_func(n):
        print (f'WITHIN WRAPPER')
        start = time.time()
        ret_val = func(n)
        end = time.time()
        return f'{func.__name__} > Looped: {ret_val} >> Timed: {(end - start)}'
    return wrapper_func

# @deco_func
def basic_looper(n):
    l = 0
    print (f'BASIC LOOPER STARTED: {l}')
    for i in range(n):
        for j in range(n):
            l += 1
    print (f'BASIC LOOPER ENDED: {l}')
    return l

# @deco_func
def log_looper(n):
    l = 0
    print (f'LOG LOOPER STARTED: {l}')
    for i in range(n):
        for j in range(i):
            l += 1
    print (f'LOG LOOPER ENDED: {l}')
    return l


basic_looper = deco_func(basic_looper)
log_looper = deco_func(log_looper)
 
print (basic_looper(2000))
print (log_looper(2000))

# print (basic_looper(2500))
# print (log_looper(2500))

# bl = deco_func(basic_looper)
# ll = deco_func(log_looper)
#  
# print (bl(2500))
# print (ll(3537))

# def deco_func(orig_func):
#     def wrapper_func():
#         return orig_func()
#     return wrapper_func
# 
# def disp():
#     print ('Display function ran')
#     
# decorated_display = deco_func(disp)
# decorated_display()

# def out_func(o_msg):
#     def in_func(i_msg):
#         return f'{o_msg} {i_msg}'
#     return in_func
#  
# hi = out_func('Hi')
# hello = out_func('Hello')
#  
# print (hi('Dipen'))
# print (hello('Dhara'))
# 
# 
# 
# def math(f_name):
#     ret_var = ''
#     if (f_name.lower() == 'add'):
#         def add (x, y):
#             return x + y        
#         ret_var = add
#     elif (f_name.lower() == 'sub'):
#         def sub (x, y):
#             return x - y
#         ret_var = sub
#     
#     return ret_var
# 
# add = math('add')
# sub = math('sub')
# 
# print (add(5, 6))
# print (sub(8, 4))