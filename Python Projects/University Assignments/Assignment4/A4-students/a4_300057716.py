import random

def create_network(file_name):
    '''(str)->list of tuples where each tuple has 2 elements the first is int and the second is list of int

    Precondition: file_name has data on social netowrk. In particular:
    The first line in the file contains the number of users in the social network
    Each line that follows has two numbers. The first is a user ID (int) in the social network,
    the second is the ID of his/her friend.
    The friendship is only listed once with the user ID always being smaller than friend ID.
    For example, if 7 and 50 are friends there is a line in the file with 7 50 entry, but there is line 50 7.
    There is no user without a friend
    Users sorted by ID, friends of each user are sorted by ID
    Returns the 2D list representing the frendship nework as described above
    where the network is sorted by the ID and each list of int (in a tuple) is sorted (i.e. each list of friens is sorted).
    '''
    friends = open(file_name).read().splitlines()
    #print(friends)
    #print("\n\n\n")
    network=[]
    lst=[]

    ##splits the original list in a form where both elements in each row
    ##are readable
    for i in range(len(friends)):
        lst.append(friends[i].split())
    
    #print(lst)
    #print('\n\n\n')
    
    #create a list that takes in consideration all of friends and puts in
    #in assignment format
    i=1
    tmp2=[]
    friend_list2=[]
    while i<len(lst):
        for j in range(len(friend_list2)):
            if int(lst[i][0]) in friend_list2[j][1]:
                tmp2+=[int(friend_list2[j][0])]
        tmp2+= [int(lst[i][1])]
        while i<len(lst)-1 and lst[i][0] == lst[i+1][0]:
            tmp2+= [int(lst[i+1][1])]
            i+=1
        
        friend_list2.append((int(lst[i][0]),tmp2))
        tmp2=[]
        i+=1
    #print("\n\n\n")
    #print(friend_list2)


    #create list with every user from the list that has chance to be left out
    elements_list=[]

    #print("\n\n\n")
    for i in range(1,len(lst)):
        if int(lst[i][1]) not in elements_list:
            elements_list+=[int(lst[i][1])]
    #print(elements_list)
    
    #create list of missing elements
    lst_missing_numbers=[]
    flag=False
    for item in elements_list:
        for i in range(len(friend_list2)):
            if item == friend_list2[i][0]:
                flag= True
        if flag == False:
            lst_missing_numbers+=[item]
        flag=False
    #print('\n\n\n')
    #print(lst_missing_numbers)



    #add missing elements to the list
    temp=[]
    for item in lst_missing_numbers:
        for i in range(len(friend_list2)):
            if item in friend_list2[i][1]:
                temp+=[friend_list2[i][0]]
        friend_list2.append((item,temp))
        temp=[]

    #print('\n\n\n')
    friend_list2.sort()
    print(friend_list2)
        
    return friend_list2

def getCommonFriends(user1, user2, network):
    '''(int, int, 2D list) -> list
    Precondition: user1 and user2 IDs in the network. 2D list sorted by the IDs, 
    and friends of user 1 and user 2 sorted 
    Given a 2D-list for friendship network, returns the sorted list of common friends of user1 and user2
    '''

    common=[]
    b=0
    e = len(network) - 1
    flag=True
    
    while b<=e and flag:
        mid = (b+e)//2
        if user1 < network[mid][0] :
            e = mid-1
        elif user1 > network[mid][0]:
            b = mid + 1
        else:
            user_1_index = mid
            flag=False


    b=0
    e = len(network) - 1
    flag=True
    while b<=e and flag:
        mid = (b+e)//2
        if user2 <network[mid][0]:
            e = mid-1
        elif user2>network[mid][0]:
            b= mid + 1
        else:
            user_2_index = mid
            flag=False
    

    #print(user_1_index, user_2_index)
    
    #create a list of common friends
    for item in network[user_1_index][1]:
        if item in network[user_2_index][1]:
            common.append(item)
    
    #print(common)

    return common

    
def recommend(user, network):
    '''(int, 2Dlist)->int or None
    Given a 2D-list for friendship network, returns None if there is no other person
    who has at least one neighbour in common with the given user and who the user does
    not know already.
    
    Otherwise it returns the ID of the recommended friend. A recommended friend is a person
    you are not already friends with and with whom you have the most friends in common in the whole network.
    If there is more than one person with whom you have the maximum number of friends in common
    return the one with the smallest ID. '''

    # YOUR CODE GOES HERE
    user_index = 0
    b=0
    e=len(network)-1
    user_index =  0
    flag=True
    
    #function that finds index of user
    while b<=e and flag:
        mid = (b+e)//2
        if user < network[mid][0] :
            e = mid-1
        elif user > network[mid][0]:
            b = mid + 1
        else:
            user_index = mid
            flag=False
            
    
    temp = 0
    max_common = 0
    u_index = 0
    #function that finds someone w max friends in common
    for i in range(len(network)):
        if user not in network[i][1] and network[i][0]!=user:
            for item in network[user_index][1]:
                if item in network[i][1]:
                    temp+=1
        if temp>max_common:
            max_common = temp
            u_index = i
        temp = 0
        
    if max_common == 0:
        return None
    else:
        return network[u_index][0]
    


def k_or_more_friends(network, k):
    '''(2Dlist,int)->int
    Given a 2D-list for friendship network and non-negative integer k,
    returns the number of users who have at least k friends in the network
    Precondition: k is non-negative'''
    # YOUR CODE GOES HERE
    amount_users=0
    for j in range(len(network)):
        if len(network[j][1])>=k:
            amount_users+=1
    return amount_users
        
 

def maximum_num_friends(network):
    '''(2Dlist)->int
    Given a 2D-list for friendship network,
    returns the maximum number of friends any user in the network has.
    '''
    # YOUR CODE GOES HERE

    max_friends = 0 
    for element in range(len(network)):
        if len(network[element][1])>max_friends:
            max_friends = len(network[element][1])
            
    return max_friends
    

def people_with_most_friends(network):
    '''(2Dlist)->1D list
    Given a 2D-list for friendship network, returns a list of people (IDs) who have the most friends in network.'''
    max_friends=[]
    # YOUR CODE GOES HERE
    maximum = maximum_num_friends(network)
    for i in range(len(network)):
        if len(network[i][1])== maximum:
            max_friends+=[network[i][0]]
    return max_friends


def average_num_friends(network):
    '''(2Dlist)->number
    Returns an average number of friends overs all users in the network'''

    # YOUR CODE GOES HERE
    average = 0
    for i in range(len(network)):
        average+=len(network[i][1])

    return average/len(network)
        
    
    

def knows_everyone(network):
    '''(2Dlist)->bool
    Given a 2D-list for friendship network,
    returns True if there is a user in the network who knows everyone
    and False otherwise'''
    
    # YOUR CODE GOES HERE

    for i in range(len(network)):
        if len(network[i][1]) == len(network)-1:
            return True
    return False 


####### CHATTING WITH USER CODE:

def is_valid_file_name():
    '''None->str or None'''
    file_name = None
    try:
        file_name=input("Enter the name of the file: ").strip()
        f=open(file_name)
        f.close()
    except FileNotFoundError:
        print("There is no file with that name. Try again.")
        file_name=None
    return file_name 

def get_file_name():
    '''()->str
    Keeps on asking for a file name that exists in the current folder,
    until it succeeds in getting a valid file name.
    Once it succeeds, it returns a string containing that file name'''
    file_name=None
    while file_name==None:
        file_name=is_valid_file_name()
    return file_name


def get_uid(network):
    '''(2Dlist)->int
    Keeps on asking for a user ID that exists in the network
    until it succeeds. Then it returns it'''
    
    # YOUR CODE GOES HERE
    flag = True
    while flag:
        try:
            ans = int(input("Enter an integer for user I.D: ").strip())
            for i in range(len(network)):
                if network[i][0] == ans:
                    return ans
            print("That user ID does not exist. Try again.")
            
        except ValueError:
            print("That was not an integer! Please try again.")
        
    

##############################
# main
##############################

# NOTHING FOLLOWING THIS LINE CAN BE REMOVED or MODIFIED

file_name=get_file_name()
    
net=create_network(file_name)

print("\nFirst general statistics about the social network:\n")

print("This social network has", len(net), "people/users.")
print("In this social network the maximum number of friends that any one person has is "+str(maximum_num_friends(net))+".")
print("The average number of friends is "+str(average_num_friends(net))+".")
mf=people_with_most_friends(net)
print("There are", len(mf), "people with "+str(maximum_num_friends(net))+" friends and here are their IDs:", end=" ")
for item in mf:
    print(item, end=" ")

print("\n\nI now pick a number at random.", end=" ")
k=random.randint(0,len(net)//4)
print("\nThat number is: "+str(k)+". Let's see how many people has that many friends.")
print("There is", k_or_more_friends(net,k), "people with", k, "or more friends")

if knows_everyone(net):
    print("\nThere at least one person that knows everyone.")
else:
    print("\nThere is nobody that knows everyone.")

print("\nWe are now ready to recommend a friend for a user you specify.")
uid=get_uid(net)
rec=recommend(uid, net)
if rec==None:
    print("We have nobody to recommend for user with ID", uid, "since he/she is dominating in their connected component")
else:
    print("For user with ID", uid,"we recommend the user with ID",rec)
    print("That is because users", uid, "and",rec, "have", len(getCommonFriends(uid,rec,net)), "common friends and")
    print("user", uid, "does not have more common friends with anyone else.")
        

print("\nFinally, you showed interest in knowing common friends of some pairs of users.")
print("About 1st user ...")
uid1=get_uid(net)
print("About 2st user ...")
uid2=get_uid(net)
print("Here is the list of common friends of", uid1, "and", uid2)
common=getCommonFriends(uid1,uid2,net)
for item in common:
    print(item, end=" ")

    
