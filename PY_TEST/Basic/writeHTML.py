x = "Dipen Desai"
print (x)

y = "<html><head><title>Look at this</title></head><body><h1>" + x + "</h1><a href='http://www.google.com'>CLICK</a></body></html>"
print(y)

with open("C:/Users/RRDD/Desktop/myhtml.html", "w+") as my_html_file:
    my_html_file.write(y)

print("done")