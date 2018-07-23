import re

def create_bin(str):
    bin_fin = ''
    up_count = 0
    bin_dict = {}
    
    # CREATE SAMPLE DICTIONARY
    sample_str = "The quick brown fox jumps over the lazy dog"
    sample_bin = "000001011110110010100010000000111110101001010100100100101000000000110000111010101010010111101110000000110100101010101101000000010110101001101100111100011100000000101010111001100010111010000000011110110010100010000000111000100000101011101111000000100110101010110110"
    
    grps = re.findall(r'(\d{6})', sample_bin)
    for j, i in enumerate(sample_str):
        if i.isupper():
            bin_dict['UP'] = grps[j+up_count]
            up_count += 1
        bin_dict[i.lower()] = grps[j+up_count]
    
    # GENERATE BINARY
    for j, i in enumerate(str):
        if i.isupper():
            bin_fin += bin_dict['UP']
        bin_fin += bin_dict[i.lower()]
    
    return bin_fin

str = 'The quick brown fox jumps over the lazy dog'
print (f'STR: {str} > BIN: {create_bin(str)}')

str = 'a'
print (f'STR: {str} > BIN: {create_bin(str)}')

str = 'A'
print (f'STR: {str} > BIN: {create_bin(str)}')

str = '   '
print (f'STR: {str} > BIN: {create_bin(str)}')

str = 'Braille'
print (f'STR: {str} > BIN: {create_bin(str)}')

str = 'code'
print (f'STR: {str} > BIN: {create_bin(str)}')

str = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'
print (f'STR: {str} > BIN: {create_bin(str)}')