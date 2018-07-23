def remove(l):
    nl = []
    print (f'START: {l}')
    
    for i in range(len(l)):
        if (l[i] == '<'):
            if (i > 0 and l[i-1] != '<'):
                nl = nl[:-1]
        else:
            nl.append(l[i])
    print (f'RETURN: {nl}')
    return nl

def answer (ar1, ar2):
    ar1 = remove(ar1)
    ar2 = remove(ar2)
    
    ret = False
    
    if ar1 == ar2:
        ret = True
        
    return ret


charA = ['1','2','3','<','4','<']
charB = ['1','2']

# charA = ['1','2','3','<','<','<','4','<','5']
# charB = ['1','2','<','8','<','<','5','5','<']


print(answer(charA, charB))