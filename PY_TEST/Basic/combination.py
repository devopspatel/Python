def permu(str, size):	
	n_str = list(set(str.lower().replace()))
	n_str.sort()
	final_list = n_str
	
	return final_list

print(permu('Dipen Desai', 3))