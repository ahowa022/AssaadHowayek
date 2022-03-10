# Family name: Assaad Howayek
# Student number: 300057716
# Course Number: ITI 1120
# Assignment Number 3

import random

# Read and understand the docstrings of all of the functions in detail.
#TOP OF THE CARD IS CONSIDERED LAST CARD IN YOUR LIST

def make_deck(num):
    '''(int)->list of int
        Returns a list of integers representing the strange deck with num ranks.

    >>> deck=make_deck(13)
    >>> deck
    [101, 201, 301, 401, 102, 202, 302, 402, 103, 203, 303, 403, 104, 204, 304, 404, 105, 205, 305, 405, 106, 206, 306, 406, 107, 207, 307, 407, 108, 208, 308, 408, 109, 209, 309, 409, 110, 210, 310, 410, 111, 211, 311, 411, 112, 212, 312, 412, 113, 213, 313, 413]

    >>> deck=make_deck(4)
    >>> deck
    [101, 201, 301, 401, 102, 202, 302, 402, 103, 203, 303, 403, 104, 204, 304, 404]
    
    '''
    deck=[]
    for element in range(1,num+1):
        deck+=[100+element,200+element, 300+element, 400+element]
    return deck


def shuffle_deck(deck):
    '''(list of int)->None
       Shuffles the given list of strings representing the playing deck

    Here you should use random.shuffle function from random module.
    
    Since shufflling is random, exceptionally in this function
    your output does not need to match that show in examples below:

    >>> deck=[101, 201, 301, 401, 102, 202, 302, 402, 103, 203, 303, 403, 104, 204, 304, 404]
    >>> shuffle_deck(deck)
    >>> deck
    [102, 101, 302, 104, 304, 103, 301, 403, 401, 404, 203, 204, 303, 202, 402, 201]
    >>> shuffle_deck(deck)
    >>> deck
    [402, 302, 303, 102, 104, 103, 203, 301, 401, 403, 204, 101, 304, 201, 404, 202]
    '''
    random.shuffle(deck)

def deal_cards_start(deck):
    '''(list of int)-> list of int
    Returns a list representing the player's starting hand.
    It is  obtained by dealing the first 7 cards from the top of the deck.
    Precondition: len(dec)>=7'''
    deck_start=[]
    for num in range(7):
        deck_start+=[deck[len(deck)-1-num]]
    for num in range(7):
        deck.pop()
    return deck_start
    

    
                      
         
         

def deal_new_cards(deck, player, num):
    '''(list of int, list of int, int)-> None
    Given the remaining deck, current player's hand and an integer num,
    the function deals num cards to the player from the top of the deck.
    If len(deck)<num then len(deck) cards is dealt, in particular
    all the remaining cards from the deck are dealt.

    Precondition: 1<=num<=6 deck and player are disjoint subsets of the strange deck. 
    
    >>> deck=[201, 303, 210, 407, 213, 313]
    >>> player=[302, 304, 404]
    >>> deal_new_cards(deck, player, 4)
    >>> player
    [302, 304, 404, 313, 213, 407, 210]
    >>> deck
    [201, 303]
    >>>

    >>> deck=[201, 303]
    >>> player=[302, 304, 404]
    >>> deal_new_cards(deck, player, 4)
    >>> player
    [302, 304, 404, 303, 201]
    >>> deck
    []
    '''
    if len(deck)<num:
        player+=deck[::-1]
        for i in range(len(deck)):
            deck.pop()
    else:
        for k in range(num):
            player+=[deck[len(deck)-1-k]]
        for i in range(num):
            deck.pop()
            


def print_deck_twice(hand):
    '''(list)->None
    Prints elements of a given list deck in two useful ways.
    First way: sorted by suit and then rank.
    Second way: sorted by rank.
    Precondition: hand is a subset of the strange deck.
    
    Your function should not change the order of elements in list hand.
    You should first make a copy of the list and then call sorting functions/methods.

    Example run:
    >>> a=[311, 409, 305, 104, 301, 204, 101, 306, 313, 202, 303, 410, 401, 105, 407, 408]
    >>> print_deck_twice(a)

    101 104 105 202 204 301 303 305 306 311 313 401 407 408 409 410 

    101 301 401 202 303 104 204 105 305 306 407 408 409 410 311 313 
    >>> a
    [311, 409, 305, 104, 301, 204, 101, 306, 313, 202, 303, 410, 401, 105, 407, 408]

    '''
    new_list=hand[::]
    new_list.sort()
    for element in new_list:
        print(element, end=' ')
        
    print("\n")
    for element in range(1,100):
        for i in range(len(new_list)):
            if new_list[i]%100==element:
                print(new_list[i], end=' ')
    print('\n',end='')

        
    
        


def is_valid(cards, player):
    '''(list of int, list of int)->bool
    Function returns True if every card in cards is the player's hand.
    Otherwise it prints an error message and then returns False,
    as illustrated in the followinng example runs.

    Precondition: cards and player are subsets of the strange deck.
    
    >>> is_valid([210,310],[201, 201, 210, 302, 311])
    310 not in your hand. Invalid input
    False

    >>> is_valid([210,310],[201, 201, 210, 302, 310, 401])
    True
    '''

    for item in cards:
        if item not in player:
            print(item,"not in your hand. Invalid Input")
            return False
    return True 
            
    
        
                
                
            
            

def is_discardable_kind(cards):
    '''(list of int)->True
    Function returns True if cards form 2-, 3- or 4- of a kind of the strange deck.
    Otherwise it returns False. If there  is not enough cards for a meld it also prints a message about it,
    as illustrated in the followinng example runs.
    
    Precondition: cards is a subset of the strange deck.

    In this function you CANNOT use strings except in calls to print function. 
    In particular, you cannot conver elements of cards to strings.
    
    >>> is_discardable_kind([207, 107, 407])
    True
    >>> is_discardable_kind([207, 107, 405, 305])
    False
    >>> is_discardable_kind([207])
    Invalid input. Discardable set needs to have at least 2 cards.
    False
    '''
    n=cards[0]%100
    if len(cards)<2:
        print("Invalid input. Discardable set needs to have at least 2 cards.")
        return False
    else:
        for element in range(1, len(cards)):
            if cards[element]%100!=n:
                return False
        return True 
    


def is_discardable_seq(cards):
    '''(list of int)->True
    Function returns True if cards form progression of the strange deck.
    Otherwise it prints an error message and then returns False,
    as illustrated in the followinng example runs.
    Precondition: cards is a subset of the strange deck.

    In this function you CANNOT use strings except in calls to print function. 
    In particular, you cannot conver elements of cards to strings.

    >>> is_discardable_seq([313, 311, 312])
    True
    >>> is_discardable_seq([311, 312, 313, 414])
    Invalid sequence. Cards are not of same suit.
    False
    >>> is_discardable_seq([311,312,313,316])
    Invalid sequence. While the cards are of the same suit the ranks are not consecutive integers.
    False
    >>> is_discardable_seq([201, 202])
    Invalid sequence. Discardable sequence needs to have at least 3 cards.
    False
    >>> is_discardable_seq([])
    Invalid sequence. Discardable sequence needs to have at least 3 cards.
    False
    '''
    listnew=cards[::]
    listnew.sort()
    if len(cards)<3:
        print("Invalid sequence. Discardable sequence needs to have at least 3 cards.")
        return False
    else:
        n= listnew[0] //100
        for item in listnew:
            if item//100 !=n:
                print("Invalid sequence. Cards are not of the same suit")
                return False
        for element in range(len(cards)-1):
            if listnew[element]+1 != listnew[element+1]:
                print("Invalid sequence. While the cards are from the same suit, the ranks are not consecutive integers")
                return False
        return True
        

def rolled_one_round(player):
    '''(list of int)->None
    This function plays the part when the player rolls 1
    Precondition: player is a subset of the strange deck

    >>> #example 1:
    >>> rolled_one_round(player)
    Discard any card of your choosing.
    Which card would you like to discard? 103
    103
    No such card in your hand. Try again.
    Which card would you like to discard? 102

    Here is your new hand printed in two ways:

    201 212 311 

    201 311 212 

    '''
    game_play= True
    print("Discard any card of your choosing.")
    while game_play:
        x=input("Which card would you like to discard. ").strip()
        if int(x) in player:
            player.remove(int(x))
            print("\nHere is your hand printed in two ways:\n")
            print_deck_twice(player)
            game_play=False
            
        else:
            print("No such card in your hand. Try again.")
        


def rolled_nonone_round(player):
    '''(list of int)->None
    This function plays the part when the player rolls 2, 3, 4, 5, or 6.
    Precondition: player is a subset of the strange deck

    >>> #example 1:
    >>> player=[401, 102, 403, 104, 203]
    >>> rolled_nonone_round(player)
    Yes or no, do you  have a sequences of three or more cards of the same suit or two or more of a kind? yes
    Which 3+ sequence or 2+ of a kind would you like to discard? Type in cards separated by space: 102 103 104
    103 not in your hand. Invalid input
    Yes or no, do you  have a sequences of three or more cards of the same suit or two or more of a kind? yes
    Which 3+ sequence or 2+ of a kind would you like to discard? Type in cards separated by space: 403 203

    Here is your new hand printed in two ways:

    102 104 401 

    401 102 104 
    Yes or no, do you  have a sequences of three or more cards of the same suit or two or more of a kind? no

    >>> #example 2:
    >>> player=[211, 412, 411, 103, 413]
    >>> rolled_nonone_round(player)
    Yes or no, do you  have a sequences of three or more cards of the same suit or two or more of a kind? yes
    Which 3+ sequence or 2+ of a kind would you like to discard? Type in cards separated by space: 411 412 413

    Here is your new hand printed in two ways:

    103 211 

    103 211 
    Yes or no, do you  have a sequences of three or more cards of the same suit or two or more of a kind? no

    >>> #example 3:
    >>> player=[211, 412, 411, 103, 413]
    >>> rolled_nonone_round(player)
    Yes or no, do you  have a sequences of three or more cards of the same suit or two or more of a kind? yes
    Which 3+ sequence or 2+ of a kind would you like to discard? Type in cards separated by space: 411 412
    Invalid meld: 11 is not equal to 12
    Invalid sequence. Discardable sequence needs to have at least 3 cards.

    >>> #example 4:
    >>> player=[401, 102, 403, 104, 203]
    >>> rolled_nonone_round(player)
    Yes or no, do you  have a sequences of three or more cards of the same suit or two or more of a kind? alsj
    Yes or no, do you  have a sequences of three or more cards of the same suit or two or more of a kind? hlakj
    Yes or no, do you  have a sequences of three or more cards of the same suit or two or more of a kind? 22 33
    Yes or no, do you  have a sequences of three or more cards of the same suit or two or more of a kind? no
    '''
    
    flag = True
    while flag:
        x=input("Yes or no, do you have a sequence of three or more cards of the same suit or two or more of a kind? ").strip()
        if x== 'yes':
            y=input("Which 3+ sequence or 2+ of a kind would you like to discard? Type in cards seperated by a space: ").strip().split()
            list_sequence=[]
            for element in y:
                list_sequence+=[int(element)]
            if is_valid(list_sequence, player)==True:
                if is_discardable_kind(list_sequence)==True or is_discardable_seq(list_sequence)==True:
                    for card in list_sequence:
                        player.remove(card)
                    print("\nHere is your hand printed in two ways:\n") 
                    print_deck_twice(player)
                    
        elif x=='no':
            flag=False
        if len(player)==0:
            flag = False
            
                
  


# main
print("Welcome to Single Player Rummy with Dice and strange deck.\n")
size_change=input("The standard deck  has 52 cards: 13 ranks times 4 suits.\nWould you like to change the number of cards by changing the number of ranks? ").strip().lower()
www=0
# YOUR CODE GOES HERE and in all of the above functions instead of keyword pass
if size_change=='yes':
            
    www= True
    while www:
        x=input("Enter a number between 3 and 99, for the number of ranks: ").strip()
        if int(x)>=3 and int(x)<=99:
            deck=make_deck(int(x))
            www=False
            print("You are playing with a deck of",4*int(x),"cards")
    

            
else:
    deck=make_deck(13)
    print("You are playing with a deck of 52 cards")

shuffle_deck(deck)
#print(deck)
players_deck = []
players_deck = deal_cards_start(deck)

run_game= True

round_increment=1
dice_value=0
print("Here is your new hand printed in two ways:\n")
print_deck_twice(players_deck)

while run_game:
    
    dice_value = random.randint(1,6)
    print("Welcome to round", round_increment)
    if len(deck)>0 :
        print("Please roll the dice")
        print("You rolled the dice and it shows:",dice_value)
        if dice_value!=1:
            print("Since you rolled,",dice_value,",",dice_value,
                  "cards from the top of the deck will be added to your hand unless the deck has less than",dice_value,"cards. In that case, less than",dice_value,"cards will be added to your hand")
            print("\nHere is your deck printed in two ways:\n")
            deal_new_cards(deck, players_deck, dice_value)
            print_deck_twice(players_deck)
            rolled_nonone_round(players_deck)
        if dice_value==1:
            rolled_one_round(players_deck)
    else:
        print("The game is in empty deck phase")
        rolled_one_round(players_deck)

    if len(players_deck)==0 :
        print("Round",round_increment,"completed!\nCongratulations you completed the game in",
              round_increment,"round(s)")
        run_game= False
    else:
        print("Round",round_increment,"completed.")
    round_increment+=1
