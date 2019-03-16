## NOT SOLVED

cmd = ['CREATE fruits',
'CREATE vegetables',
'CREATE grains',
'CREATE fruits/apples',
'CREATE fruits/apples/fuji',
# 'LIST',
'CREATE grains/squash',
'MOVE grains/squash vegetables',
'CREATE foods',
# 'MOVE grains foods',
# 'MOVE fruits foods',
# 'MOVE vegetables foods',
# 'LIST',
'DELETE fruits/apples',
# 'DELETE foods/fruits/apples',
'LIST']

fd = {}

for c in cmd:
    ll = c.split(' ')

    if (ll[0].lower() == 'create'):
        if (ll[1].__contains__('/')):
            l2 = ll[1].split('/')
            if (len(l2) == 2):
                fd[l2[0]][l2[1]] = {}
            else:
                fd[l2[0]][l2[1]][l2[2]] = {}
        else:
            fd[ll[1]] = {}
    elif (ll[0].lower() == 'move'):
        if (ll[1].__contains__('/')):
            l2 = ll[1].split('/')
            if (len(l2) == 2):
                print (ll[2])
                fd[ll[2]][fd[l2[1]]] = {}
            else:
                fd[ll[2]][l2[2]]] = {}
                print (ll[1])
        else:
            fd[ll[2]][ll[1]]
    elif (ll[0].lower() == 'delete'):
        if (ll[1].__contains__('/')):
            l2 = ll[1].split('/')
            if (len(l2) == 2):
                del fd[l2[0]][l2[1]]
            else:
                del fd[l2[0]][l2[1]][l2[2]]
        else:
            del fd[ll[1]]
    elif (ll[0].lower() == 'list'):
        l2 = ['list']
    else:
        print ("Command not found")
    print ("FD: {0}".format(fd))
