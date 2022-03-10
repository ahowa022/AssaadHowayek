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
    """ (int , int) ---> bool
    Returns True if both numbers are pyhtagorian pair
    Returns false if these numbers are not pythogorian pair
    Precondition: numbers inserted (a,b) must be integers
    """
    c = a**2 + b**2
    ##code makes sure that c is an integer
    c = math.sqrt(c)%1
    c= 0 ==c
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
    Returns true if the coordinates inputed by the user
    is in the square based on the bottom left corner coordinates(xs,ys). Returns false otherwise.
    Length of the side of the square determined by "side" input
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
    """(string, string, int) ---> string
    Returns message that includes all variables in a specific arrangement
    Precondition: year has to be an int and quote as well as name have to be str
    """
    year = str(year)
    message = ('In ' +year+ ", a person called " +name+ ' said: "'+quote+ '"')
    return message


##########################
#Question 6
##########################

def quote_displayer():
    """(None)===>none
    Prints message that includes all inputs prompted from the user"""
    quote =input("Insert a quote someone said: ")
    name = input("What was his name? ")
    year = int(input("What year did he say that: "))
    print(quote_maker(quote,name,year))
    


#########################
#Question 7
#########################

def rps_winner():
    """(none)--->None
    Simulates a game of rock paper scissors. Each user inputs their own answer and program indicates the winner
    or if it is a tie by printing two statements
    Condition: user must insert "rock", "paper" or "scissors"
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
    """(string) ---> print statement
    Prints a name plaque including the name in the middle
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
    """(None)---> None
    Returns a drawing of a car"""
    s=turtle.Screen()
    t=turtle.Turtle(shape='circle')
    t.color('red')

    
    #draw the body of the car
    #front bottom
    t.begin_fill()
    t.goto(0,0)
    t.forward(40)
    t.penup()
    t.goto(80,0)
    t.pendown()
    t.setheading(90)
    t.circle(20,180)
    t.penup()
    t.goto(80,0)
    t.pendown()
    t.setheading(0)
    t.forward(30)
    

    #back bottom
    t.penup()
    t.goto(0,0)
    t.pendown()
    t.setheading(180)
    t.forward(40)
    t.setheading(90)
    t.circle(20,180)
    t.setheading(180)
    t.forward(42.5)
    t.setheading(90)
    t.forward(12.5)
    t.setheading(0)
    t.forward(12.5)

    #all the way up to the windows
    t.setheading(90)
    t.forward(50)
    t.setheading(0)
    t.forward(220.0)
    t.setheading(270)
    t.forward(50)
    t.setheading(0)
    t.forward(12.5)
    t.setheading(270)
    t.forward(12.5)
    t.setheading(180)
    t.forward(13.5)
    t.end_fill()

    
    #drawing of the windows
    t.color("grey")
    t.begin_fill()
    t.penup()
    t.goto(-80,62.5)
    t.pendown()
    t.goto(-60,115)
    t.setheading(0)
    t.forward(110)
    t.goto(70, 62.5)
    t.setheading(180)
    t.forward(150)
    t.end_fill()
    

    
    #draw the first wheel
    t.color("black")
    t.begin_fill()
    t.penup()
    t.goto(40,0)
    t.setheading(270)
    t.pendown()
    t.circle(20)
    t.setheading(0)
    t.end_fill()

    #draw the second wheel
    t.penup()
    t.goto(-40,0)
    t.setheading(90)
    t.begin_fill()
    t.pendown()
    t.circle(20)
    t.setheading(0)
    t.end_fill()



    #draw second wheel and inside of car
    #vertical side of door and hood
    t.color("black")
    t.penup()
    t.goto(-20,0)
    t.setheading(90)
    t.pendown()
    t.forward(115)
    t.penup()
    t.goto(70, 62.5)
    t.setheading(270)
    t.pendown()
    t.forward(50)
    #doorhandle
    t.color("black")
    t.begin_fill()
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
    t.end_fill()
    
    #details to the side bottom railing
    t.penup()
    t.goto(-110,12.5)
    t.setheading(0)
    t.pendown()
    t.forward(32.7)
    t.penup()
    t.goto(110,12.5)
    t.pendown()
    t.setheading(180)
    t.forward(37.5)


    #add second circle to the wheels
    t.color("yellow")
    t.begin_fill()
    t.penup()
    t.goto(70,0)
    t.setheading(90)
    t.pendown()
    t.circle(10)
    t.end_fill()
    t.penup()
    t.goto(-50,0)
    t.begin_fill()
    t.setheading(90)
    t.pendown()
    t.circle(10)
    t.end_fill()

    #adding the rims of the wheels
    #wheel one
    t.color("black")
    t.penup()
    t.goto(-58,0)
    t.pendown()
    t.begin_fill()
    t.circle(2)
    t.end_fill()
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
    t.begin_fill()
    t.circle(2)
    t.end_fill()
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
    t.begin_fill()
    t.penup()
    t.goto(105,42.5)
    t.pendown()
    t.setheading(90)
    t.circle(5)
    t.end_fill()

    #rearlights
    t.penup()
    t.goto(-110,42.5)
    t.pendown()
    t.begin_fill()
    t.setheading(0)
    t.forward(15)
    t.setheading(270)
    t.forward(5)
    t.setheading(180)
    t.forward(15)
    t.end_fill()

    t.color('grey')

    #road
    t.penup()
    t.goto(-200,-20)
    t.pendown()
    t.setheading(0)
    t.forward(400)

    s.exitonclick






############################
#Question 11
############################
    
def alogical(n):
    """(number)---> int
    Returns amount of times n has to be divided by 2 until it is smaller than one.
    Essentially finds x value that corresponds to the equation n/2**x <=1 
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
    Precondition: h has to be 0<=h<24, n 0<=n<60, as well as an int
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
    Precondition: payment has to be bigger or equal to the price and input has to contain max 2 decimals and be bigger than 0
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
    """(number, number, number)---> tuple
    Returns the change of the customer in the smallest number of canadian coins after recieving the
    payment and price. format of change is(toonies, loonies, quarters, dimes, nickels)
    Precondition: price has to be lower or equal to payment and both inputs have to contain max two decimals and be bigger than 0
    """
    #determines price in CAD
    owed_money = round((payment-price)/0.05)*0.05
    #infinite amount of  binary numbers for most decimals which is why i round again
    #start at toonies then loonies then quarters them dimes then nickels
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
    
    
    
    



