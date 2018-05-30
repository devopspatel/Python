# Problem 1
print(7**4)

# Problem 2
s = "Hi there Sam!"
l = s.split()
print(l)

# Problem 3
d = {'k1':[1,2,3,{'tricky':['oh', 'man', 'inception', {'target':[1,2,3,'hello']}]}]}
print(d['k1'][3]['tricky'][3]['target'][3])

# Problem 4
print("The diameter of {} is {} kilometers.".format("Earth", "12742"))
print("The diameter of {a} is {b} kilometers.".format(b='12742',a = 'Earth'))
print("The diameter of {planet} is {dia} kilometers.".format(planet="Earth", dia="12742"))

# Problem 5
lst = [1,2,[3,4],[5,[100,200,['hello']],23,11],1,7]
print(lst[3][1][2][0])

# Problem 6
def dom(x):
    return x.split('@')[1]

print(dom("dhara@usual.com"))

# Problem 7
def dog(x):
    l=x.lower().split()
    y = 0
    for x in l:
        if x == 'dog': y = y + 1
    return y
        
print(dog("This Dog runs faster than the other dog dude!"))

# Problem 8
seq = ['soup','dog','salad','cat','great']

s = list(filter(lambda x: x[0] == 's', seq))
print(s)

# Problem 9
def tic(speed, bday):
    ad = 0
    if bday == True: 
        ad = 5
    
    if (speed <= (60+ad)):
        print ("No Ticket")
    elif ((speed > (60+ad)) and (speed <= (80+ad))):
        print ("Small Ticket")
    elif (speed > (80+ad)):
        print("Big Ticket")
    
tic(55, False)
tic(80, False)
tic(67, True)
tic(65, True)
tic(85, False)
tic(61, False)
