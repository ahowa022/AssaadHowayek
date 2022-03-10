# Family name: Assaad Howayek
# Student number: 300057716
# Course Number: ITI 1120
# Assignment Number 2

def split_tester(N, d):
    # Your code for split_tester function goes here (instead of keyword pass)
    # Your code should include  dosctrings and the body of the function
    '''(str,str)---> bool
    Returns True if the sequence of N is split into pieces or a piece
    that has the length of d is increasing. Returns False otherwise. Also prints the sequence
    of numbers of length d seperated by commas that came from N in a sequence.
    Precondition: string of divider d and string of number N has to be positive integers in a string format

    >>>split_tester("134567","2")
    13, 45, 67
    True
    '''
    
    s1=''
    s2='-1000000'
    sequence_string=''
    message=''
    for char in N:
        message+=char
        if len(message)==int(d):
            sequence_string+=message+', '
            message=''

    # this loop just to take off comma at the end of sequence when printed
    message=''
    increment=0
    for char in sequence_string:
        if increment<(len(sequence_string)-2): #-2 eliminates space and comma
            message+=char
            increment+=1

    print(message)
    
    if len(N)==int(d):
        return True
    else:
        for number in N:
            s1+=number
            if (len(s1))==int(d):
                if (int(s1)<=int(s2)):
                    return False
                s2=s1
                s1=''
        return True
        

# you can add more function definitions here if you like       


            
# main
# Your code for the welcome message goes here, instead of name="Vida"
name= input("What is ur name? ").strip()
name_intro = name +", welcome to my increasing-split tester."
name_length = len(name_intro)
print("*"*(name_length+6))# need room for spaces and _ before and after name(explains the +6)
print("*"+" "*name_length+"    *")#four spaces to insert a space and an underscore at the beginning and en of the name
print("* _"+name_intro+"_ *")
print("*"+" "*name_length+"    *")
print("*"*(name_length+6))
 
flag=True
while flag:
    question=input(name+", would you like to test if a number admits an increasing-split of give size? ")
    question=(question.strip()).lower()
    if question=='no':
        flag=False
    elif question =='yes':
        print("Good choice!!")
        num_admitted = (input("Enter a positive integer: ").strip())
        if num_admitted!="":
            accumulator=''
            for char in num_admitted:
                if char not in "1234567890-":
                    print("The input can only contain digits. Try again.")
                    break
                else:
                    accumulator+=char
            if (accumulator==num_admitted and accumulator!='-' and num_admitted.count("-")==1 and num_admitted[0]=='-') or accumulator=='0':
                print("The input has to be a positive integer. Try again.")
            elif accumulator == '-' or (num_admitted.count('-')>1 and num_admitted==accumulator) or (num_admitted.count('-')==1 and num_admitted==accumulator and num_admitted[0]!='-'):
                print("The input can only contain digits. Try again.")
            elif accumulator == num_admitted:
                num_admitted_len= len(num_admitted)
                split_num=(input("Input the split. The split has to divide the length of "+num_admitted+' i.e. '+str(num_admitted_len)+'\n').strip())
                if split_num!='':
                    accumulatorv2=''
                    for char in split_num:
                        if char not in "1234567890-":
                            print("The divider can only contain digits. Try again.")
                            break
                        else:
                            accumulatorv2+=char

                    if (accumulatorv2==split_num and accumulatorv2!='-' and split_num.count("-")==1 and split_num[0]=='-')or accumulatorv2=='0':
                        print("The divider has to be a positive integer. Try again!")
                    elif accumulatorv2 == '-' or (split_num.count('-')>1 and split_num==accumulatorv2) or (split_num.count('-')==1 and split_num==accumulatorv2 and split_num[0]!="-"):
                        print("The divider can only contain digits. Try again.")
                    elif accumulatorv2==split_num and num_admitted_len%int(split_num)!=0:
                        print(split_num+' does not divide '+str(num_admitted_len)+'. Try again!')
                    elif accumulatorv2==split_num and num_admitted_len%int(split_num)==0:
                        bool_increase= split_tester(num_admitted, split_num)
                        
                        if bool_increase==True:
                            print("The sequence is increasing")
                        else:
                            print("The sqeuence is not increasing")
                else:
                    print("There must be something entered that something being a positive integer for a divider. Try again.")
        else:
            print("There must be something entered that something being a positive integer. Try again.")
    else:
        print("Please enter yes or no. Try again.")
        
#YOUR CODE GOES HERE. The next line should be elif or else.


print()
name_end = "Good bye "+name
name_length = len(name_end)
print("*"*(name_length+6))# need room for spaces and _ before and after name(explains the +6)
print("*"+" "*name_length+"    *")#four spaces to insert a space and an underscore at the beginning and en of the name
print("* _"+name_end+"_ *")
print("*"+" "*name_length+"    *")
print("*"*(name_length+6))       

        
#finally your code goes here too.
