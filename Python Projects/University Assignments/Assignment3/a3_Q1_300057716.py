# Family name: Assaad Howayek
# Student number: 300057716
# Course Number: ITI 1120
# Assignment Number 3

def count_pos(list_num):
    '''(list)--->int
    Returns amount of positive numbers in the list
    Condition: list inputed in the function has to be filled numbers under the form
    of a string, integer or float or finally the list must be empty
    '''
    amount_of_positives=0
    for num in list_num:
        if float(num)>0:
            amount_of_positives+=1
    return amount_of_positives
        

value= count_pos(input("Please input a list of numbers seperated by a space: ").strip().split())

print("There are",value, 'positive numbers in your list')


