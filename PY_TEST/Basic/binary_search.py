def time_it(func):
    """ Time it Function """
    import datetime
    def wrapper(*args, **kwargs):
        start = datetime.datetime.now()
        result = func(*args, **kwargs)
        end = datetime.datetime.now()
        print ('Function: {0} > Time Taken: {1}'.format(func.__name__, end - start))
        return result
    return wrapper

@time_it
def lin_search(val, l):
    """ Linear Search > Check each value of the list and compares that with val > O(n)=n) """
    for i in range(len(l)):
        if (val == i):
            print ('Value Found: {0}'.format(i))

@time_it
def bin_search(val, l):
    """ Binary Search > Narrow down search in half list after each loop >  > O(n)=log n """
    ff = True
    n = len(l)/2

    while (ff):
        if (val == l[n]):
            ff = False
        if (val > l[n]):
            l = l[n:]
        else:
            l = l[:n]

        n = len(l)/2

v = 10000
r = 30000

lin_search(v, list(range(1, r)))
bin_search(v, list(range(1, r)))
