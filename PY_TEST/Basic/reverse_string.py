st = "One Two Three Four Five Six"
print ('Original:\n\t{0}'.format(st))

""" Reversing String by Words | Dipen Desai > Desai Dipen """
# r = st.split(' ')
# r = r[::-1]
# r = ' '.join(r)
# # OR
r = ' '.join(st.split(' ')[::-1])

print ('Rerser Words:\n\t{0}'.format(r))


""" Reversing Words and Then by Characters | Dipen Desai > iaseD nepiD """
# rr = r.split(' ')
# rr = list(map(lambda x:x[::-1].title(), rr))
# rr = ' '.join(rr)
# # OR
rr = ' '.join(list(map(lambda x:x[::-1].title(), st.split(' ')))[::-1])

print ('Reverse Words and then Chars with Title case:\n\t{0}'.format(rr))

""" Reversing Entire String by Character """
rrr = st[::-1]
print('Reverse Entire String\n\t{0}'.format(rrr))
