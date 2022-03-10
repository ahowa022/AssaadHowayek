def time_format(h,m):
    
    n = ((m//10)*10)
    h2= str(h+1)
    h= str(h)
    
    if (m>30 and h2!="24"):
        #prints any possibility when minutes round to 5
        if(m%10<=7 and m%10>=3):
            n= str(60-(n+5))
            print(n+" minutes until " + h2 + " oclock")
        #prints any possibility when minutes round to 0 but not at 30 minute mark
        if (m%10<=2 and n!=30):
            n=str(60-n)
            print(n+" minutes until " +h2 + " oclock")
        #prints when minutes round to 0 at 30 minute mark
        if (m%10<=2 and n==30):
            print("half past " + h + " oclock")
        #prints any possibility when minutes round to 0 during h
        if (m%10>=8 and n!=50):
            n=str(60-(n+10))
            print(n+ " minutes until " + h2 + " oclock")
        #prints when minutes round to 0 during h2 
        if (m%10>=8 and n==50):
            n=str(n+10)
            print(h2 +" oclock")

    #same block of code from the previous one but it is made for when h+1=24
    #converts it to 0 clock
    if (m>30 and h2=="24"):
        if(m%10<=7 and m%10>=3):
            n= str(60-(n+5))
            print(n+" minutes until 0 oclock")
        if (m%10<=2 and n!=30):
            n=str(60-n)
            print(n+" minutes until 0 oclock")
        if (m%10<=2 and n==30):
            print("half past " + h + " oclock")
        if (m%10>=8 and n!=50):
            n=str(60-(n+10))
            print(n+ " minutes until 0 oclock")
        if (m%10>=8 and n==50):
            n=str(n+10)
            print("0 oclock")
            
    if (m<=30):
        # prints any possibility where minutes round to 5
        if(m%10<=7 and m%10>=3):
            n= str(n+5)
            print(n+" minutes past " + h + " oclock")
        #prints any possibility where minutes round to 0 when it is not at the 0
        #minute mark nor 30 minute mark. It is also when the remainder division of minutes by 10
        #is smaller than 0
        if (m%10<=2 and n>=10 and n!=30):
            n=str(n)
            print(n+" minutes past " +h + " oclock")
        #prints when minutes round to 0 at the 0 minute mark
        if (m%10<=2 and n==0):
            print(h+ " oclock")
        #prints when minutes round to 0 at the 30 minute mark
        if ((m%10>=8 and n==20) or n==30):
            print("half past " + h + " oclock")
        #prints when minutes round to 0 when it is not at the 30 minute mark nor
        #at the 0 minute mark. It is also when the remainder division of minutes by 10
        #is bigger than 8
        if (m%10>=8 and n!=20 and n!=30):
            n=str(n+10)
            print(n+ " minutes past " + h +" oclock")
