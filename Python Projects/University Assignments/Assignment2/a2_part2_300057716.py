# Family name: Assaad Howayek
# Student number:300057716
# Course Number: ITI 1120
# Assignment Number 2

import math


#####################
#Question 2.1
#####################
def min_enclosing_rectangle(radius,x,y):
    '''(number, number, number) ---> tuple (if radius>0)
    (number, number, number)---> None (if radius<0)
    Returns bottom left corner's coordinates of the smallest square that can contain the circle
    with the radius and the center of the circle's coordinates (x,y) that come from the input parameters.
    Returns None if the radius is negative
    Precondition: Radius cannot be 0
    '''    
    if radius>=0:
        x_corner = x - radius
        y_corner = y - radius
        return (x_corner, y_corner)
    else:
        return None 


######################
#Question 2.2
######################
def series_sum():
    '''None ---> number (if the integer inputed by user is >= 0)
    None----> None (if the integer inputed by user is smaller than 0)
    Returns the sum of 1000 + 1/1**2 +1/2**2+...+1/n**2, n being the integer inputed
    by the user. However, function Returns None if the integer imputed is smaller than 0. 
    Condition: number imputed by user has to be an integer'''
    n = int(input("Please enter a non-negative integer: ").strip())
    if n>=0:
        total = 1000
        for num in range(1,n+1):
            total+= 1/num**2
        return total
    else:
        return None 


######################
#Question 2.3
######################

def pell(n) :
    '''(int)--->int (if integer a non-negative number)
    (int)---> None (if integer a negative number)
    Returns the pell number at the specific index of n
    Precondition: function paramter n has to be an integer
    '''
    if n == 0:
        return 0
    elif n == 1:
        return 1
    elif n < 0:
        return None
    return round(((2**0.5+1)**n-(-2**.05+1)**n)/(2*2**.5))

    
#####################
#Question 2.4
#####################
def countMembers(s):
    '''(str)---> int
    Returns the amount of extraordinary characters in the string s. Extraordinary
    caracters are lower case letters between e and j(inclusive), upper case letters between F and X(inclusive),
    numbers between 2 and 6(inclusive) as well as (!) (,) (\)'''
    counter_extraordinary=0
    for extraordinary in s:
        if extraordinary in "efghijFGHIJKLMNOPQRSTUVWX23456!,\\":
            counter_extraordinary+=1
    return counter_extraordinary
        
        
####################
#Question 2.5
####################

def casual_number(s):
    '''(str)---->int (if s ressembles a whole number)
    (str)----> None (if s does not ressemble whole number.)
    Returns the integer of a whole number written in the string s. Returns None if the string
    does not ressemble a whole number.
    Precondition: comma has to be placed properly for it to make a number 
    more readable. e.g 1,000,000 is proper use of the comma. However, 1,1,33
    is not proper use of a comma. Furthermore, commas cannot be used to write decimal
    numbers in s.
    >>>casual_number("1,000,999")
    1000999
    '''
    add_num=''
    final_num=''
    for char in s:
        if char in "1234567890,-":
            add_num+=char
        else:
            return None
    if (",," in add_num) or ("-," in add_num) or (",-" in add_num) or("-" ==add_num) or (","==add_num):
        return None
    elif add_num.count("-")>1 or (add_num.count('-')==1 and add_num[0]!='-'):
        return None
    else:
        for char in add_num:
            if char!=",":
                final_num+=char
        return int(final_num)
            
            
#####################
#Question 2.6
#####################
    
def alienNumbers(s):
    '''(str)---> (int)
    Returns a sum of the character since each character represents a value.
    It is called an alien message. (T)=1024, (y)=598 (!)=121 (a)=42 (N)=6 (U)=1
    Precondition: strings only consist of characters in "Ty!aNU" '''
    final_message= 1024*s.count("T") + 598*s.count("y") +121*s.count("!")
    final_message+= 42 *s.count("a") + 6*s.count("N")+ s.count("U")
    return final_message

######################
#Question 2.7
######################
    
def alienNumbersAgain(s):
    '''(str)---> (int)
    Returns a sum of the character since each character represents a value.
    It is called an alien message. (T)=1024, (y)=598 (!)=121 (a)=42 (N)=6 (U)=1
    Precondition: s only consist of characters in "Ty!aNU" '''
    accumulator = 0
    for char in s:
        if char == "T":
            accumulator+=1024
        elif char == "y":
            accumulator+=598
        elif char == "!":
            accumulator+= 121
        elif char == "a":
            accumulator+= 42
        elif char == "N":
            accumulator+=6
        elif char == "U":
            accumulator+=1
    return accumulator


######################
#Question 2.8
######################
        
def encrypt(s):
    '''(str)--->str
    Returns incripted message. To encrypt s, it is written backwards.
    Then the first and the last chracter become the first and second character.
    Then the second the before last character become the third and fourth character.
    This rythm continues until the there are no more characters to move
    
    >>>encrypt("Hello")
    'oHlel'
    '''

    s=s[::-1]
    new_message=''
    length = len(s)
    x = 1
    y = 0
    for i in range(length):
        if i%2==0:
            new_message+= s[y]
            y+=1
        if i%2==1:
            new_message+= s[length-x]
            x+=1
    return new_message
        
    
###################
#Question 2.9
###################
def oPify(s):
    '''(str)--->str
    Returns the same string with an op in between each pair of alphabets.
    The "O" would be capital if the first letter in the pair is captilized and
    the "P" would be capital if the second letter in the pair is capitilized. "op"
    are not present in between pairs that are not pairs of alphabets.

    >>>oPify("abBc$")
    'aopboPBOpc$'
    '''
    previous_letter=''
    current_letter=''
    accumulator=''
    for char in s:
        current_letter=char
        if previous_letter.isalpha() and current_letter.isalpha():
            if previous_letter.isupper() and current_letter.isupper():
                accumulator+="OP" +current_letter
            elif previous_letter.isupper() and not current_letter.isupper():
                accumulator+="Op"+current_letter
            elif not previous_letter.isupper() and not current_letter.isupper():
                accumulator+="op"+current_letter
            else:
                accumulator+="oP"+current_letter
        else:
            accumulator+= current_letter
        previous_letter= char
    return accumulator
                

####################
#Question 10
####################
def nonrepetitive(s):
    '''(str)--->bool
    Returns True if the word is a non repetitive word meaning it does not
    contain any subword more than twice in a row. Returns false otherwise. 'Pool' is
    not a nonrepetitive word since subword "o" is found twice in a row. However
    boy is a non repetitive word since there are no subwords repeated more
    than twice in a row'''
    s1=''
    s2=''
    for x in range(1,len(s)+1):
        for n in range(len(s)):
            s1=s[n:x+n]
            s2=s[x+n:x+n+x]
            if s1==s2:
                return False
    return True
    
        
        

