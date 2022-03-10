#Family name: Assaad Howayek
# Student number: 300057716
# Course Number: ITI 1120
# Asssignment Number 1

import math
import turtle

#########################
#Question 1
#########################

def pythagorean_pair(a,b):
    """ (Number,Number) ---> Bool
    Returns True if both numbers are pyhtagorian pair
    Returns false if these numbers are not pythogorian pair
    Precondition: numbers inserted (a,b) must be integers
    """
    c = a**2 + b**2
    ##code makes sure that c is an integer
    c = math.sqrt(c)%1
    c= 0==c
    return c


###########################
#Question 2
###########################

def mh2kh(s):
    """(number) --> number
    Returns the speed in km/h with a given speed of m/h
    Precondition: speed is a non-negative number
    """
    # 1 mile is equivalent to 1.60934 km
    s = s * 1.60934
    return s

###########################
#Question 3
###########################

def in_out(xs,ys,side):
    """(number, number,number) ---> bool
    Returns boolean depending on if the coordinates inputed by the user is in the rectangle
    based on the bottom left corner
    Precondition: side cannot be negatif
    """
    x = float(input("Enter a x coordinate of the query point: "))
    y = float(input("Enter a y coordinate of the query point: "))
    x = (x<=xs+side and x>=xs and y>=ys and y<=ys+side)
    return x

############################
#Question 4
############################

def safe(n):
    """(int) ---> bool
    Returns false if the number is not a safe number meaning it contains a 9 or can be divided by 9.
    Returns True otherwise.
    Precondition: the number should contain at most 2 digits and be non negative
    """
    #line of code that verifies if number is safe
    verification = (n%10==9 or n%9==0 or n>90)
    verification = not(verification)
    return verification

##########################
#Question 5
##########################

def quote_maker(quote, name, year):
    """(string, string, int) ---> message
    Returns message that includes all variables in a specific arrangement
    Precondition: year has to be a number
    """
    year = str(year)
    message = ('In ' +year+ ", a person called " +name+ ' said: "'+quote+ '"')
    return message


##########################
#Question 6
##########################

def quote_displayer():
    """Returns message that includes all variables prompted from the user"""
    quote =input("Insert a quote someone said: ")
    name = input("What was his name? ")
    year = int(input("What year did he say that: "))
    print(quote_maker(quote,name,year))
    


#########################
#Question 7
#########################

def rps_winner():
    """Simulates a game of rock paper scissors. Each user inputs their own answer and program indicates the winner
    or if it is a tie
    """
    print("What choicice did player 1 make?")
    player1_answer= input("Type one of the following options: rock, paper, scissors: ")
    print("What choicice did player 2 make?")
    player2_answer= input("Type one of the following options: rock, paper, scissors: ")
    ##inputs all the possibilities that makes player one win
    win_player1= ((player1_answer=="rock" and player2_answer=="scissors") or (player1_answer=="scissors" and
                  player2_answer=="paper") or (player1_answer=="paper" and player2_answer=="rock"))
    ##inputs all the possibilites that makes the game a tie game
    tie_game = ((player1_answer=="rock" and player2_answer=="rock") or (player1_answer=="scissors" and
                  player2_answer=="scissors") or (player1_answer=="paper" and player2_answer=="paper"))
    ##not fct converts it because in message it says "its a tie that is not ____" 
    tie_game = not(tie_game)
    ##convert variables to string to insert them in the message
    win_player1 = str(win_player1)
    tie_game = str(tie_game)
    print("Player one wins. That is "+win_player1)
    print("It's a tie, that is not "+ tie_game)
    
##########################
#Question 8
##########################

def fun(x):
    """(number) ---> number
    Returns the y value of 10**4y=x+3 from the given x
    Precondition: x cannot be negatif
    """
    unknown_value = (math.log10(x+3))/4
    return unknown_value

###########################
#Question 9
###########################


def ascii_name_plaque(name):
    """(string) ---> message
    Returns a name plaque including the name in the middle
    Precondition: must enter a string for name
    """
    name_length = len(name)
    print("*"*(name_length+6))## need room for spaces and _ before and after name(explains the +6)
    print("*"+" "*name_length+"    *")#four spaces to insert a space and an underscore at the beginning and en of the name
    print("* _"+name+"_ *")
    print("*"+" "*name_length+"    *")
    print("*"*(name_length+6))


###########################
#Question 10
###########################
def draw_car():
    import turtle
    s=turtle.Screen()
    t=turtle.Turtle()
    s.exitonclick
    #draw the base of the car
    #front bottom
    t.goto(0,0)
    t.forward(40)
    t.setheading(270)
    t.circle(20)
    t.setheading(0)
    t.penup()
    t.forward(40)
    t.pendown()
    t.forward(30)
    t.penup()
    t.goto(40,0)
    t.pendown()
    
    #back bottom
    t.goto(-40,0)
    t.setheading(90)
    t.circle(20)
    t.penup()
    t.goto(-80,0)
    t.pendown()
    t.setheading(180)
    t.forward(30)

    #all the top
    t.forward(12.5)
    t.setheading(90)
    t.forward(12.5)
    t.setheading(0)
    t.forward(12.5)
    t.setheading(90)
    t.forward(50)
    t.setheading(0)
    t.forward(30)
    t.goto(-60,115)
    t.forward(110)
    t.goto(70, 62.5)
    t.forward(40)
    t.setheading(270)
    t.forward(50)
    t.setheading(0)
    t.forward(12.5)
    t.setheading(270)
    t.forward(12.5)
    t.setheading(180)
    t.forward(13.5)


    #draw second wheel and inside of car
    #vertical side of door
    t.penup()
    t.goto(-20,0)
    t.setheading(90)
    t.pendown()
    t.forward(115)
    #horizontal side of door
    t.penup()
    t.goto(-80,62.5)
    t.pendown()
    t.setheading(0)
    t.forward(150)
    #doorhandle
    t.penup()
    t.goto(-5.5,42.5)
    t.pendown()
    t.setheading(90)
    t.forward(5)
    t.setheading(180)
    t.forward(10)
    t.setheading(270)
    t.forward(5.1)
    t.setheading(0)
    t.forward(10)
    #details to the side bottom railing
    t.penup()
    t.goto(-110,12.52)
    t.setheading(0)
    t.pendown()
    t.forward(32.7)
    t.penup()
    t.goto(110,12.5)
    t.pendown()
    t.setheading(180)
    t.forward(37.5)


    #add second circle to the wheels
    t.penup()
    t.goto(70,0)
    t.setheading(90)
    t.pendown()
    t.circle(10)
    t.penup()
    t.goto(-50,0)
    t.setheading(90)
    t.pendown()
    t.circle(10)

    #adding the rims of the wheels
    #wheel one
    t.penup()
    t.goto(-58,0)
    t.pendown()
    t.circle(2)
    t.penup()
    t.goto(-60,2)
    t.setheading(90)
    t.pendown()
    t.forward(8)
    t.penup()
    t.goto(-62,0)
    t.setheading(180)
    t.pendown()
    t.forward(8)
    t.penup()
    t.goto(-60,-2)
    t.setheading(270)
    t.pendown()
    t.forward(8)
    t.penup()
    t.goto(-58,0)
    t.setheading(0)
    t.pendown()
    t.forward(8)

    #Wheel 2
    t.penup()
    t.goto(62,0)
    t.setheading(90)
    t.pendown()
    t.circle(2)
    t.penup()
    t.goto(60,2)
    t.setheading(90)
    t.pendown()
    t.forward(8)
    t.penup()
    t.goto(62,0)
    t.setheading(0)
    t.pendown()
    t.forward(8)
    t.penup()
    t.goto(60,-2)
    t.setheading(270)
    t.pendown()
    t.forward(8)
    t.penup()
    t.goto(58,0)
    t.setheading(180)
    t.pendown()
    t.forward(8)


    #headlights
    t.penup()
    t.goto(105,42.5)
    t.pendown()
    t.setheading(90)
    t.forward(10)
    t.setheading(180)
    t.forward(3)
    t.setheading(270)
    t.forward(10)
    t.setheading(0)
    t.forward(3)

    #rearlights
    t.penup()
    t.goto(-110,42.5)
    t.pendown()
    t.setheading(0)
    t.forward(15)
    t.setheading(270)
    t.forward(5)
    t.setheading(180)
    t.forward(15)

    #road
    t.penup()
    t.goto(-200,-20)
    t.pendown()
    t.setheading(0)
    t.forward(400)







############################
#Question 11
############################
    
def alogical(n):
    """(number)---> int
    Returns amount of times n has to be divided until it is smaller than one.
    Essentially finds x value thar corresponds to the equation n/2**x <=1 
    Precondition, n has to be bigger or equal to 1
    """
    ##code that isolates x in n/2**x <=1
    x = math.ceil(math.log(n, 2))
    return x
        

############################
#Question 12
############################


def time_format(h,m):
    """(int, int)---> str
    Returns time in a descriptiive string
    Precondition: h has to be 0<=h<24, n 0<=n<60
    """
    m = (round(m/5))*5
    #h2 defined as the next hour
    h2= str(h+1)
    h = str(h)
    
    if m>30 and h2!='24' and m!=60:
        m_to_str = str(60-m)
        message = (m_to_str+" minutes until " + h2 + " oclock")

    if m>30 and h2=="24" and m!=60:
        m_to_str = str(60-m)
        message = (m_to_str+" minutes until 0 oclock")

    if m<30 and m!=0:
        m_to_str = str(m)
        message = (m_to_str+ " minutes past " +h + " oclock")

    if m==0:
        m_to_str = str(m)
        message = (h + " oclock")

    if m==30:
        message = ("half past " +h+" oclock")

    if (m==60 and h2!="24"):
        message = (h2+" oclock")

    if (m==60 and h2=='24'):
        message = ("0 oclock")

    return message 
        
        
    
#########################
#Question 13
#########################

def cad_cashier(price,payment):
    """(number, number) ---> number
    Returns the change that the cashier owes the customer(rounded by 5 cents)
    Precondition: payment has to be bigger or equal to the price and each has to contain max 2 decimals
    """
    owed_money = payment - price
    #if more than half of the 5 cents owed itll round up by 5 cents if not itll round down to the nearest 5 cents 
    owed_money = round(owed_money/0.05)
    owed_money = owed_money*0.05
    #infinite amounr of binary numbers for 0.05 which is why i round again
    owed_money = round(owed_money, 2)
    return owed_money


##########################
#Question 14
##########################

def min_CAD_coins(price,payment):
    """(number, number, number)---> (int,int,int,int,int)
    Returns the change of the customer in the smallest number of canadian coins after recieving the
    payment and price (toonies, loonies, quarters, dimes, nickels)
    Precondition: price has to be lower or equal to payment and both inputs have to contain max two decimals
    """
    owed_money = round((payment-price)/0.05)*0.05
    #infinite amount of  binary numbers for most decimals which is why i round again
    owed_money = round(owed_money, 2)
    amount_toonies = round(owed_money//2)
    owed_money = (round(owed_money%2,2))
    amount_loonies = round(owed_money // 1)
    owed_money =(round(owed_money%1,2))
    amount_quarter = round(owed_money // 0.25)
    owed_money = (round(owed_money%0.25,2))
    amount_dime = round(owed_money // 0.1)
    owed_money = (round(owed_money%0.1,2))
    amount_nickel = round(owed_money // 0.05)
    return (amount_toonies, amount_loonies, amount_quarter, amount_dime, amount_nickel)
    
    
    
    


