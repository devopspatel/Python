import random

# for i in range(20):
# 	value = random.random()	# Selects between 0 and 1
# 	value = random.uniform(0, 10)	# Generates floats between 0 and 10
# 	value = random.randint(1, 20)	# Generates integers between 0 and 20
# 	print(value)
	
greetings = ['Hey', 'Hi', 'Hello' , 'Hola', 'YooHoo']
value = random.choice(greetings)	# Generates 1 random selection from list
print (value + ', Dipen!')

#Dice
value = random.randint(1, 6)
print(value)

# Roulette
# Choices creates a list as output + # of instances = K + Probability = WEIGHT
colors = ['red', 'black', 'green']
# Choices
result = random.choices(colors, weights=[18, 18, 2], k=20)
print (result)

# Deck
# Create a list of 52 cards > Shuffle it > Pick 5 unique cards
deck = list(range(1,53))
print(deck)
random.shuffle(deck)
print(deck)

hand = random.sample(deck, k=5)	# Random selection of 5 cards (NO REPEATS)
print(hand)

# Phone Number
for i in range(10):
	print (f'{random.randint(100,999)}-{random.randint(100,999)}-{random.randint(1000,9999)}')
	

# Generate Random Person Data Set
first_names = ['John', 'Jane', 'Corey', 'Travis', 'Dave', 'Kurt', 'Neil', 'Sam', 'Steve', 'Tom', 'James', 'Robert', 'Michael', 'Charles', 'Joe', 'Mary', 'Maggie', 'Nicole', 'Patricia', 'Linda', 'Barbara', 'Elizabeth', 'Laura', 'Jennifer', 'Maria']
last_names = ['Smith', 'Doe', 'Jenkins', 'Robinson', 'Davis', 'Stuart', 'Jefferson', 'Jacobs', 'Wright', 'Patterson', 'Wilks', 'Arnold', 'Johnson', 'Williams', 'Jones', 'Brown', 'Davis', 'Miller', 'Wilson', 'Moore', 'Taylor', 'Anderson', 'Thomas', 'Jackson', 'White', 'Harris', 'Martin']
street_names = ['Main', 'High', 'Pearl', 'Maple', 'Park', 'Oak', 'Pine', 'Cedar', 'Elm', 'Washington', 'Lake', 'Hill']
fake_cities = ['Metropolis', 'Eerie', "King's Landing", 'Sunnydale', 'Bedrock', 'South Park', 'Atlantis', 'Mordor', 'Olympus', 'Dawnstar', 'Balmora', 'Gotham', 'Springfield', 'Quahog', 'Smalltown', 'Epicburg', 'Pythonville', 'Faketown', 'Westworld', 'Thundera', 'Vice City', 'Blackwater', 'Oldtown', 'Valyria', 'Winterfell', 'Braavos‎', 'Lakeview']
states = ['AL', 'AK', 'AZ', 'AR', 'CA', 'CO', 'CT', 'DC', 'DE', 'FL', 'GA', 'HI', 'ID', 'IL', 'IN', 'IA', 'KS', 'KY', 'LA', 'ME', 'MD', 'MA', 'MI', 'MN', 'MS', 'MO', 'MT', 'NE', 'NV', 'NH', 'NJ', 'NM', 'NY', 'NC', 'ND', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VT', 'VA', 'WA', 'WV', 'WI', 'WY']

for i in range(10):
	first = random.choice(first_names)
	last = random.choice(last_names)
	phone = f'{random.randint(100,999)}-{random.randint(100,999)}-{random.randint(1000,9999)}'
	street_no = f'{random.randint(100,999)}'
	street_name = random.choice(street_names)
	city = random.choice(fake_cities)
	state = random.choice(states)
	zip = f'{random.randint(10000,99999)}'
	address = f'{street_no} {street_name} St., {city} {state} {zip}'
	email = f'{last}.{first[0]}@justemail.com'
	
	print (f'{first} {last}\n{phone}\n{address}\n{email}\n')