import turtle

def draw_car():
    """Returns a drawing of a car"""
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
    t.color("black")
    t.penup()
    t.goto(-80,62.5)
    t.pendown()
    t.goto(-60,115)
    t.setheading(0)
    t.forward(110)
    t.goto(70, 62.5)
    t.penup()
    t.forward(40)
    t.pendown()
    

    
    #draw the first wheel
    t.color("grey")
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
    #vertical side of door
    t.color("black")
    t.penup()
    t.goto(-20,0)
    t.setheading(90)
    t.pendown()
    t.forward(115)
    #doorhandle
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
    t.forward(10)
    t.setheading(180)
    t.forward(3)
    t.setheading(270)
    t.forward(10)
    t.setheading(0)
    t.forward(3)
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



