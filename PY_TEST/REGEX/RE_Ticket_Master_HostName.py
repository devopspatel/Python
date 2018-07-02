import re

log3 = '''app1.cms.phx.tmcs
app1.cms.phx.tmcs
app2.cmd.dhx.tmcs
app3.cmt.phx.tmcs
dev1.cms.ddx.tmcs
txt1.cmR.phx.tmcs
prd1.cmx.dhx.tmcs
app1.cms.phx.tmcs
app1.cms.xxx.tmcs'''

# hsts = re.finditer(r'[a-zA-Z0-9]+\.[a-zA-Z0-9]+\.[a-zA-Z0-9]+\.[a-zA-Z0-9]+', log3)
hsts = re.finditer(r'[a-zA-Z0-9]{4}\.[a-zA-Z0-9]{3}\.[a-zA-Z0-9]{3}\.[a-zA-Z0-9]{4}', log3)

for hst in hsts:
    print(hst[0])