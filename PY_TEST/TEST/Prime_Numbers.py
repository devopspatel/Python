def prime(n):
    l = []
    i = 2
    while (len(l) <= n):
        if (i == 2 or i == 3):
            l.append(i)
        else:
            for a in range(2, i):
                if (i%a == 0):
                    break
                elif (a == i - 1):
                    l.append(i)
        i += 1
    return (l)

n = 20
print (f'PRIMEs: {n} >> {prime(n)}')
