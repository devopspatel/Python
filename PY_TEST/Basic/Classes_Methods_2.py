class Employee:
	
	raise_amount = 1.04
	num_of_emp = 0
	
	def __init__(self, first, last, pay):
		self.first = first
		self.last = last
		self.email = first + '.' + last + '@gmail.com'
		self.pay = pay
		
		Employee.num_of_emp += 1
	
	def fullname(self):
		return '{} {}'.format(self.first, self.last)

	def apply_raise(self):
		self.pay = int(self.pay * self.raise_amount)
		
# Generating Two instances of a Class
emp_1 = Employee('Dipen','Desai',60000)
emp_2 = Employee('Dhara', 'Shah', 50000)

# Using Instance Variable
print(emp_1.email)
print(emp_2.email)

# Using Class Method
print(emp_1.fullname())
print(emp_2.fullname())
print(Employee.fullname(emp_1))

# Using Class Method using Class variable
print(emp_1.pay)
emp_1.apply_raise()
print(emp_1.pay)

# Printing Class Variables
print(emp_1.raise_amount)
print(emp_2.raise_amount)
print(Employee.raise_amount)

# Printing Class Dictionaries and Changing Class Variables
print(Employee.__dict__)
Employee.raise_amount = 1.05
print(Employee.__dict__)

# Changing Instance Variable
emp_1.raise_amount = 1.05
print(emp_1.__dict__)
print(emp_2.__dict__)

print(emp_1.pay)
emp_1.apply_raise()
print(emp_1.pay)

print(Employee.num_of_emp)