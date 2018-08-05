# import datetime as dt
# 
# CONVENTIONAL METHOD OF CALCULATING TIME DELTA
# def calc_square(nums):
#     start = dt.datetime.now()
#     result = []
#     for num in nums:
#         result.append(num * num)
#     end = dt.datetime.now()
#     print (f'2 TIME DELTA: {end - start}')
#     return result
# 
# def calc_cube(nums):
#     start = dt.datetime.now()
#     result = []
#     for num in nums:
#         result.append(num * num * num)
#     end = dt.datetime.now()
#     print (f'3 TIME DELTA: {end - start}')
#     return result


# PASS FUNCTION AS AN ARGUMENT IN WRAPPER AND USE THE FUNCTIONALITY OF WRAPPER

def time_it(func):
    import time
    
    def wrapper(*args, **kwargs):
        start = time.time()
        result = func(*args, **kwargs)
        end = time.time()
        print(f'{func.__name__} >> TIME DELTA: {str((end - start)*1000)}')
        return result
    return wrapper


# METHOD 1 >> USING @ SYMBOL TO DECORATE A FUNCTION BEFORE START
@time_it
def calc_square(nums):
    result = []
    for num in nums:
        result.append(num * num)
    return result

@time_it
def calc_cube(nums):
    result = []
    for num in nums:
        result.append(num * num * num)
    return result


# # METHOD 2 >> REASSIGN FUNCTION NAME AFTER DECORATION
# calc_square = time_it(calc_square)
# calc_cube = time_it(calc_cube)

nums = range(5000000)
out_square = calc_square(nums)
out_square = calc_cube(nums)

# # METHOD 3 >> ASSIGN TO A VARIABLE (SIMILAR TO # 2; EXCEPT DIFFERENT VARIABLE NAME)
# sq = time_it(calc_square)
# cu = time_it(calc_cube)
# 
# nums = range(5000000)
# sq(nums)
# cu(nums)




