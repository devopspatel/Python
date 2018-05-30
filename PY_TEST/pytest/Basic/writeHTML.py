x = "Dipen"
y = "Desai"
x = x + " " + y
print (x)

y = "<html><head><title>Look at this</title></head><body><h1>" + x + "</h1><a href='http://www.google.com'>CLICK</a></body></html>"
print(y)

my_html_file = open("/Users/desaidn/Desktop/Automation/Python/myhtml.html", "w")
my_html_file.write(y)

print("done")