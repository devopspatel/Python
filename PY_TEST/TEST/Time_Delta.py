import datetime
from datetime import datetime

start = {}
end = {}
count = 0
average = 0
dt_format = '%Y-%m-%dT%H:%M'

with open('Time_Delta.txt', 'r') as rf:
    line = rf.readline()
    while (len(line) > 0):
        grps = line.split()
        
        if (grps[3].lower() == 'start'):
            start[grps[2]] = datetime.strptime(grps[0], dt_format)
#             print (f'PROCESS: {grps[2]} >> START: {start[grps[2]]}')
        if (grps[3].lower() == 'end'):
            end[grps[2]] = datetime.strptime(grps[0], dt_format)
            average += (end[grps[2]] - start[grps[2]]).total_seconds()
            count += 1
#             print (f'PROCESS: {grps[2]} >> END: {end[grps[2]]}')
#             print (f'\t\tPROCESS: {grps[2]} >> DIFF: {end[grps[2]] - start[grps[2]]} >> COUNT: {count} >> NEW AVG: {average}')
        line = rf.readline()
        
print (f'FINAL COUNT: {count} >> FINAL AVG: {average/count}')