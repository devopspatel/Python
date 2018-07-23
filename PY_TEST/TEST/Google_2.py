# GENEROUS PAY DISTRIBUTION
def generous(lambs):
    jr, jr2, total_pay, h_man = 0, 0, 0, 0
    
    while (total_pay < lambs):
        if (total_pay + 2**h_man <= lambs):
            jr, jr2 = jr2, 2**h_man
            total_pay += jr2
            h_man += 1
        else:
            if (jr + jr2 <= (lambs - total_pay) <= 2*jr2):
                h_man += 1
            break
    print(f'V2 GENEROUS TOTAL PAY: {total_pay} vs AVAILABLE LAMBS: {lambs}')
    print(f'V2 GENEROUS HENCHMEN COUNT: {h_man}')
    return h_man

# STINGY PAY DISTRIBUTION
def stingy(lambs):
    jr, jr2, total_pay, h_man = 1, 1, 1, 1
    
    while (total_pay < lambs):
        new_pay = h_man if (h_man <= 1) else (jr + jr2)
        if (total_pay + new_pay <= lambs):
            jr, jr2 = jr2, new_pay
            total_pay += new_pay
            h_man += 1
        else:
            break
    
    print(f'V2 STINGY TOTAL PAY: {total_pay} vs AVAILABLE LAMBS: {lambs}')
    print(f'V2 STINGY HENCHMEN COUNT: {h_man}')
    return h_man


def find_diff(lambs):
    return abs(stingy(lambs) - generous(lambs))


for i in range(0, 101):
    print (f'I: {i} --- DIFF: {find_diff(i)}')