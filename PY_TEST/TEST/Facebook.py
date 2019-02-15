# PROBLEM 1

"""
1. Not Vovel > Rotate
2. Vovel > Not Rotate
3. add 'ma' at the end
4. add 'a' depending upon number

E.g. > 'I Like Goat Style' > Imaa ikeLmaaa oatGmaaaa tyleSmaaaa
"""

def word_rotate(st):
    """ Word Rotate """
    words = st.split(' ')

    i = 1
    for word in words:
        if (word[0].lower() not in ['a', 'e', 'i', 'o', 'u']):
            word = word[1:len(word)+1] + word[0]
        words[i-1] = word + 'ma' + 'a'* i
        i += 1
    return (' ').join(words)

print (word_rotate('I Like Goat Style'))
print (word_rotate('My Name is Dipen Desai and yours Dhara Desai'))


# PROBLEM 2
"""
1. Find coordinates for tuples
2self.

E.g.
find_ship(grid) < hit_targer(x, y)

Return > ((2, 3), (3, 3), (4, 3))

. . . . . . . .
. . X . . . . .
. . X . . . . .
. . X . . . . .
. . . . . . . .
. . . . . . . .
. . . . . . . .
. . . . . . . .
"""

def hit_location(x, y):
    grid_setup = [['.', '.', '.', '.', '.', '.', '.', '.'],
    ['.', '.', '.', '.', '.', '.', '.', '.'],
    ['.', '.', '.', '.', '.', '.', '.', '.'],
    ['.', '.', '.', '.', '.', '.', '.', '.'],
    ['.', '.', 'X', 'X', 'X', '.', '.', '.'],
    ['.', '.', '.', '.', '.', '.', '.', '.'],
    ['.', '.', '.', '.', '.', '.', '.', '.'],
    ['.', '.', '.', '.', '.', '.', '.', '.']]

    return True if (grid_setup[x-1][y-1] == 'X') else False

def find_ship(grid_size):

    if (grid_size < 3):
        return 'Not Valid Grid'

    for i in range(1, grid_size + 1):
        for j in range(1, grid_size + 1):
            if (hit_location(i, j)):
                if (hit_location(i, j+1)):
                    return ((i, j), (i, j+1), (i, j+2))
                else:
                    return ((i, j), (i+1, j), (i+2, j))

print(find_ship(8))
