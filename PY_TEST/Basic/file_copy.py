import shutil
# import tempfile
# 
# filename1 = tempfile.mktemp (".txt")
# open (filename1, "w").close ()
# filename2 = filename1 + ".copy"
# print filename1, "=>", filename2
# 
# shutil.copy (filename1, filename2)
# 
# if os.path.isfile (filename2): print "Success"
# 
# dirname1 = tempfile.mktemp (".dir")
# os.mkdir (dirname1)
# dirname2 = dirname1 + ".copy"
# print dirname1, "=>", dirname2
# 
# shutil.copytree (dirname1, dirname2)
# 
# if os.path.isdir (dirname2): print "Success"


# fr = open('C:/Users/RRDD/Desktop/Automation/Python/GIT/PY_TEST/JSON/Sample.txt', 'r')
# fw = open('C:/Users/RRDD/Desktop/Automation/Python/GIT/PY_TEST/JSON/New_Text.txt', 'w')
 
shutil.copy2('C:/Users/RRDD/Desktop/Automation/Python/GIT/PY_TEST/JSON/Sample.txt', 'C:/Users/RRDD/Desktop/Automation/Python/GIT/PY_TEST/JSON/NTXT.txt')