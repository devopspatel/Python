count = 0
eL = []
eC = []

with open('PS9_Helper.java', 'r') as f:
    f_data = f.readline()
    
    while (len(f_data) > 0):
        if ('public' in f_data):
            ser = f_data.split()
            ind = ser.index('public')
            errorV = ser[ind+1]
            
            if errorV in eL:
                ind = eL.index(errorV)
                eC[ind] = eC[ind] + 1
            else:
                eL.append(errorV)
                eC.append(1)
        f_data = f.readline()
    
    print(eL)
    print(eC)
    
    print('MAX COUNT: {}'.format(eL[eC.index(max(eC))]))