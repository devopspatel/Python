import os
import datetime
from datetime import datetime
import json


def chg_dttm(dttm):
    return datetime.fromtimestamp(dttm).strftime('%y-%m-%d %H:%M:%S')

files = os.listdir('C:/Users/RRDD/Desktop/Automation/Python/GIT/PY_TEST/JSON')

dict = {}

for file in files:
    c_time = chg_dttm(os.path.getctime(file))
    m_time = chg_dttm(os.path.getmtime(file))
    dict[file] = {'Creation Time': c_time, 'Modified Time': m_time}
    
final_list = ['Data from JSON Folder', dict]     
print(final_list)
with open('file_data.json', 'w') as fw:
    json.dump(final_list, fw, indent = 2)