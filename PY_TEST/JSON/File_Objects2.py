# Making a copy of a file
with open('Sample.txt', 'r') as rf:
    with open('Sample2.txt', 'w') as wf:
        for line in rf:
            wf.write(line)
            
with open('DND_5323.jpg', 'rb') as rpf:
    with open('RR.jpg', 'wb') as wpf:
        chunk_size = 4096
        rpf_chunk = rpf.read(chunk_size)
        
        while len(rpf_chunk) > 0:
            wpf.write(rpf_chunk)
            rpf_chunk = rpf.read(chunk_size)
        
        #for line in rpf:
            #wpf.write(line)