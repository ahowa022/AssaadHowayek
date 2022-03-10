class Point:
    'class that represents a point in the plane'

    def __init__(self, xcoord=0, ycoord=0):
        ''' (Point,number, number) -> None
        initialize point coordinates to (xcoord, ycoord)'''
        self.x = xcoord
        self.y = ycoord

    def setx(self, xcoord):
        ''' (Point,number)->None
        Sets x coordinate of point to xcoord'''
        self.x = xcoord

    def sety(self, ycoord):
        ''' (Point,number)->None
        Sets y coordinate of point to ycoord'''
        self.y = ycoord

    def get(self):
        '''(Point)->tuple
        Returns a tuple with x and y coordinates of the point'''
        return (self.x, self.y)

    def move(self, dx, dy):
        '''(Point,number,number)->None
        changes the x and y coordinates by dx and dy'''
        self.x += dx
        self.y += dy

    def __eq__(self, other):
        '''(Point,Point)->bool
        Returns True if self and other have the same coordinates'''
        return self.x == other.x and self.y == other.y
    
    def __repr__(self):
        '''(Point)->str
        Returns canonical string representation Point(x, y)'''
        return 'Point('+str(self.x)+','+str(self.y)+')'
    def __str__(self):
        '''(Point)->str
        Returns nice string representation Point(x, y).
        In this case we chose the same representation as in __repr__'''
        return 'Point('+str(self.x)+','+str(self.y)+')'

class Rectangle(Point):
    def __init__(self, l_corner, r_corner, color):
        ''' (Rectangle,Point, Point, str) -> None
        Initialize rectangle to (bottom_l_corner, top_r_corner,color)
        Precondition: color inserted has to be an actual color'''
        self.l_corner= l_corner
        self.r_corner = r_corner
        self.color = color.lower().strip()

    def __repr__(self):
        '''(Rectangle)->str
        Returns canonical string representation Rectangle(bottom_l_corner, top_r_corner, color)'''
        return 'Rectangle('+str(self.l_corner)+','+str(self.r_corner)+',\''+self.color+'\')'

    def __str__(self):
        '''(Rectangle)->str
        Returns nice string representation Rectangle(bottom_l_corner, top_r_corner, color).
        '''
        return 'I am a '+self.color+' rectangle with bottom left corner at (' +str(self.l_corner.x)+','+str(self.l_corner.y)+') and top right corner at ('+str(self.r_corner.x)+','+str(self.r_corner.y)+')'

    def __eq__(self, other):
        '''(Rectangle,Rectangle)->bool
        Returns True if self and other have the same bottom left corner,
        top right corner and same color and False otherwise'''
        return self.l_corner==other.l_corner and self.r_corner==other.r_corner and self.color==other.color
        
    def get_perimeter(self):
        '''(Rectangle)--> number
        Returns value of the perimiter of rectangle self'''
        #impossible to get negative number, just to make sure w profs test cases
        return (2*(self.r_corner.x-self.l_corner.x))+(2*(self.r_corner.y-self.l_corner.y))

    def get_area(self):
        '''(Rectangle)--> number
        Returns value of the area of rectangle self'''
        #impossible to get negative number just to make sure w profs test cases
        return ((self.r_corner.x-self.l_corner.x)*(self.r_corner.y-self.l_corner.y))
    
    def get_top_right(self):
        '''(Rectangle)---> Point
        Return top right corner of rectangle self'''
        return self.r_corner
    
    def get_color(self):
        '''(Rectangle)---> str
        Return color of rectangle self'''
        return self.color

    def get_bottom_left(self):
        '''(Rectangle)---> Point
        Return bottom left corner of rectangle self'''
        return self.l_corner

    def reset_color(self,new_color):
        '''(Rectangle,str)---> None
        Resets color of rectangle self'''
        self.color = new_color.lower().strip()

    def move(self,dx,dy):
        '''(Rectangle, number, number)--->None
        Modifies value of top right corner and bottom left corner
        of the rectangle when moved by dx(distance x axis) and by
        dy(distance y axis)'''
        self.l_corner.move(dx,dy)
        self.r_corner.move(dx,dy)

    def intersects(self,other):
        '''(Rectangle, Rectangle)-->bool
        Returns True if both rectangles intersect and returns False otherwise'''
        if self.l_corner.y > other.r_corner.y or self.r_corner.y<other.l_corner.y:
            return False

        if self.l_corner.x>other.r_corner.x or self.r_corner.x<other.l_corner.x:
            return False

        return True
    
    def contains(self, number1, number2):
        '''(Rectangle, Rectangle)---> bool
        Returns True if x and y coordinates present in rectangle self'''
        if self.l_corner.x<=number1<=self.r_corner.x:
            if self.l_corner.y<=number2<=self.r_corner.y:
                return True
        return False
    
    
        
class Canvas(Rectangle):
    def __init__(self):
        ''' (Canvas, list) -> None
        Initialize canvas to a list of objects of class Rectangle'''
        #did this to avoid an error done in the lab 
        self.canvas=[]
        
    def __repr__(self):
        '''(Rectangle)->str
        Returns canonical string representation Canvas(list of objects of class Rectangle)'''
        return 'Canvas('+str(self.canvas)+')'

    def __len__(self):
        '''
        (Canvas)--> int
        Returns the amount of the rectangles that are present on the canvas
        '''
        return len(self.canvas)

    def add_one_rectangle(self, element):
        '''(Canvas, Rectangle) ---> None
        Adds rectangle to the given Canvas.
        Precondition: element added has to be of class Rectangle'''
        #lists and objects are mutable meaning this is valid
        self.canvas.append(element)

    def count_same_color(self, color_str):
        '''(Canvas)---->int
        Returns the amount of times where the color of the rectangle
        is the same color as the color_str.'''
        counter=0
        for item in self.canvas:
            if item.color==color_str.lower().strip():
                counter+=1
        return counter

    def common_point(self):
        '''(Canvas)--->bool
        Returns True if there is a common point in all the rectangles in the
        canvas and False otherwise'''
        
        for n in range(len(self.canvas)-1):
            for i in range(n,len(self.canvas)):
                if self.canvas[n].intersects(self.canvas[i])== False:
                    return False
        return True 

    def total_perimeter(self):
        '''(Canvas)--> int
        Returns the sum of the perimter of each rectangle present in the
        canvas'''
        #get_perimeter
        perimeter_sum=0
        for element in self.canvas:
            perimeter_sum+= Rectangle.get_perimeter(element)
            
        return perimeter_sum
    

    def min_enclosing_rectangle(self):
        '''(Canvas)---> Rectangle
        Returns the min enclosing rectangle of the canvas that contains all
        the rectangles of the canvas.
        Precondition: length of the canvas is bigger or equal to 1'''
        if len(self.canvas)==1:
            return self.canvas[0]
        else:
            max_x= self.canvas[0].l_corner.x
            max_y= self.canvas[0].l_corner.y
            min_x= self.canvas[0].l_corner.x
            min_y= self.canvas[0].l_corner.y
            for item in self.canvas:
                if item.l_corner.x>max_x:
                    max_x = item.l_corner.x
                    
                if item.l_corner.y>max_y:
                    max_y = item.l_corner.y

                if item.r_corner.x>max_x:
                    max_x = item.r_corner.x
                    
                if item.r_corner.y>max_y:
                    max_y = item.r_corner.y

                ################
                if item.l_corner.x<min_x:
                    min_x = item.l_corner.x
                    
                if item.l_corner.y<min_y:
                    min_y = item.l_corner.y

                if item.r_corner.x<min_x:
                    min_x = item.r_corner.x
                    
                if item.r_corner.y<min_y:
                    min_y = item.r_corner.y
            return Rectangle(Point(min_x,min_y),Point(max_x, max_y),'magenta')
        


                
                    
                
            
        
        
        
        
        
                
                
            
        
                

    

    
    
        
        
    
    

    
