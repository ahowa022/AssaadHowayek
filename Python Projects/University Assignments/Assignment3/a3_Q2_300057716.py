# Family name: Assaad Howayek
# Student number: 300057716
# Course Number: ITI 1120
# Assignment Number 3



def two_length_run(N):
    '''(list)---->bool
    Returns True if there is at least one run (of length 2 or greater)in the
    list. Returns false otherwise.
    Condition: elements of the list has to be numbers in the form of a
    string, integer or float'''
    for element in range(len(N)-1):
        if float(N[element])==float(N[element+1]):
            return True
    return False


value= two_length_run(input("Please input a list of numbers seperated by space: ").strip().split())


print(value)


        
    
