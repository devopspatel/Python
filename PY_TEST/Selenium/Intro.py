# -*- coding: utf-8 -*-
"""
Created on Fri May 11 16:45:55 2018

@author: DesaiDN
"""

name = "Dipen Desai"
skills = "IT Test Automation Framework Designer"
lan = ["Java", "Python", "VB.NET", "Office VB"]
exp = {"Java":6, "Python":1, "VB.NET":2, "Office VB":11}

hobby = {1:"traveling", 2:"photography", 3:"kids education"}

print("Name: {}".format(name))
print("Skills: {}".format(skills))
print("Known Programing Languages: {}".format(lan))

for i in range(10):
    if (i > 8):
        print("Spend many years with Corning IT Quality group")
    elif (i > 7):
        print("If not on Computer... then...", end="")
        print("{a}, {b} and {c}".format(a=hobby.get(1), b=hobby.get(2), c=hobby.get(3)))
    elif(i > 5):
        print()
    elif (i > 1):
        print("{} years of experience with {}".format(exp.get(lan[i-2]), lan[i-2]))
    elif(i > 0):
        print("Developing VB Macros for Excel data analysis")
    if (i == 0):
        print("Working on projects and developing scripts... in Java and Python")

str = "I would like to try something different"
ar = str.split(" ")

l = list(range(10))
print(l)
for i in range(25,0,-1):
    if(i==1):
        print("^")
    else:
        print(".",end="")

for i in range(7):
    for j in range(i):
        print(" ", end="")
    print(ar[i])

friend = "Greg Drake"
print("Recommended by {f}".format(f=friend))