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



