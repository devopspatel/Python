import time

class dec_class(object):
    def __init__(self, func):
        self.func = func
        
    def __call__(self, *args, **kwargs):
        start = time.time()
        result = self.func(*args, **kwargs)
        end = time.time()
        print(f'{self.func.__name__} >> TIME DELTA: {str((end - start)*1000)}')
        return result
        
@dec_class
def calc_square(nums):
    result = []
    for num in nums:
        result.append(num * num)
    return result

@dec_class
def calc_cube(nums):
    result = []
    for num in nums:
        result.append(num * num * num)
    return result

nums = range(5000000)
out_square = calc_square(nums)
out_square = calc_cube(nums)