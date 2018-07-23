import re

x = "Dipen Desai"
y = f'''
<html>
  <head>
    <title>Look at this</title>
  </head>
  <body>
    <h1>{x}</h1>
    <a href='http://www.google.com'>CLICK</a>
  </body>
</html>
'''

with open("C:/Users/RRDD/Desktop/myhtml.html", "w+") as my_html_file:
    my_html_file.write(y)
    
print ('HTML File Created')