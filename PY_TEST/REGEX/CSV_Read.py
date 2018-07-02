import csv

with open('names.csv', 'r') as fr:
    csv_reader = csv.DictReader(fr)
       
    fieldnames = ['first_name', 'last_name']
    with open('new_names.csv', 'w') as fw:
        csv_writer = csv.DictWriter(fw, fieldnames=fieldnames, delimiter='|')
        csv_writer.writeheader()

#    REGULAR WRITER
#     with open('new_names.csv', 'w') as fw:
#         csv_writer = csv.writer(fw, delimiter='\t')
#         for line in csv_reader:
#             csv_writer.writerow(line)

#    DICTIONARY WRITER
        for line in csv_reader:
            print(f'Reading >> {line}')
            del line['email']
            csv_writer.writerow(line)