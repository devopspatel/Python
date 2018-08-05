# CONVENTIONAL METHOD OF TWO FUNCTIONS CALLING EACH OTHER

def in_func(msg):
    print (msg)
    return 'DD'
 
def out_func():
    msg = 'Hi'
    return in_func(msg)

print (out_func())


# CLOSURE SCENARIO OUTER FUNCTION RETURNS INNER FUNCTION
def outer_func(msg):
    
    def inner_func():
        print (msg)
        return 'RR'
    
    return inner_func

var_1 = outer_func('DIPEN')
var_2 = outer_func('DHARA')

# EACH VARIABLE REMEMBERS IT'S LOCAL VARIABLE USED WITHIN OUTER FUNCTION EXECUTION >> HIGHER MEMORY CONSUMPTION

print(var_1())
print(var_2())