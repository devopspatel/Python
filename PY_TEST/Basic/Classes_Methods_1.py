class Employee:
	def __init__(self, first, last, pay):
		self.first = first
		self.last = last
		self.email = first + '.' + last + '@gmail.com'
		self.pay = pay
	def fullname(self):
		return (f'{self.first} {self.last}')

emp_1 = Employee('Dipen','Desai',60000)
emp_2 = Employee('Dhara', 'Shah', 50000)

print(emp_1.email)
print(emp_2.email)

print(emp_1.fullname())
print(emp_2.fullname())

print(Employee.fullname(emp_1))