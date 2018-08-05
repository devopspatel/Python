# OUTER FUNCTION: HTML_TAG >>> OR HIGHER ORDER FUNCTION
# INNER FUNCTION: WRAP_TEXT
def html_tag(tag):
    def wrap_text(msg):
        print ('<{0}>{1}</{0}>'.format(tag, msg))
    return wrap_text

# RETURN OF "WRAP TEXT" FUNCTION AND ASSIGN THAT TO A VARIABLE
print_h1 = html_tag('h1')
print (print_h1)
print_h1('Test Headline')
print_h1('Another Headline')

print_p = html_tag('p')
print (print_p)
print_p('Dipen')
print_p('Desai')

##################################################################################################################################

print ('\n\n')
# OUTER FUNCTION: LOGGER
# INNTER FUNCTION: LOG_MSG
def logger(msg):
     
    def log_msg():
        print ('Log:{}'.format(msg))
    return log_msg
 
# OUTER FUNCTION RETURNS INNTER FUNCTION WHICH IS ASSIGNED TO VARIABLE 'LOG_HI'
log_hi = logger('hi')
log_hi()

##################################################################################################################################

print ('\n\n')
def square(x):
    return x*x
 
def cube(x):
    return x*x*x
 
def my_map(func, args_list):
    result = []
     
    for i in args_list:
        result.append(func(i))
         
    return result
 
# FUNCTION + 1 ARGUMENT IS PASSED TO MY_MAP FUNCTION
squares = my_map(square, list(range(10)))
cubes = my_map(cube, list(range(10)))
 
print(f'SQUARES: {squares}')
print(f'CUBES: {cubes}')
 
f = square
print (f'FUNCTION: {f}')
print (f'FUNCTION VALUE: {f(5)}')
 
# STEP BY STEP MAP + LAMBDA FUNCTION
r = range(10)
l = lambda x: x*x
m = map(l, r)
n = list(m)
 
# ONE LINER MAP + LAMBDA FUNCTION
o = list(map(lambda x: x*x, range(10)))

print (f'RANGE: {r}')
print (f'LAMBDA FUNCTION: {l}')
print (f'MAP OBJECT: {m}')
print (f'FINAL RESULT: {n}')
print (f'ONE LINE MAP LAMBDA RESULT: {o}')