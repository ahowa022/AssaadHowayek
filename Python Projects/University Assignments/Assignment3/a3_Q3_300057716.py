# Family name: Assaad Howayek
# Student number: 300057716
# Course Number: ITI 1120
# Assignment Number 3

def longest_run(N):
    '''(list)--->int
    Returns length of the longest run of elements in the list.
    Condition: elements of the list has to be numbers in the form of a
    string, integer, float
    '''
    num_max=1
    x=1
    if len(N)>0:
        for element in range(len(N)-1):
            if float(N[element])==float(N[element+1]):
                x+=1
            else:
                x=1
            if x>num_max:
                num_max=x
        return num_max
    
    elif len(N)==0:
        return 0


value = longest_run(input("Please input a list of numbers seperated by space: ").strip().split())

print(value)
