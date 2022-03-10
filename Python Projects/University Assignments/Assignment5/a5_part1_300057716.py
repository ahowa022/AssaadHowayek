import string

def open_file():
    '''None->file object
    Keeps asking the user to enter the name of a file until he enters
    a valid name. Returns file object'''
    flag = True
    while flag:
        try:
            fn=input("Enter the name of the file: ").strip()
            x=open(fn)
            #.read().lower().splitlines()
            return x
        except FileNotFoundError:
            print("There is no file with that name. Try again.")
            

def processLines(file): #helper function 1
    '''(file)---> 2Dlist
    Reads the file and returns a 2D list of each line (line seperated
    by each word it contains.) '
    '''
    listt=[]
    file = file.read().lower().splitlines()
    #print(file)
    for element in file:
        listt.append(element.split())
    #print(listt)
    return listt

def remove_punctuation(element): #helper function 2
    '''(str)---> str
    Returns the word inserted without any punctuation
    '!"#$%&\'()*+,-./:;<=>?@[\\]^_`{|}~' '''
    word=''
    for char in element:
        if char not in string.punctuation and char not in "’":
            word+=char
    return word 

def is_a_word(item): #helper function 3
    '''(str)--->bool
    Returns True if word inserted only contains letters
    and False otherwise
    '''
    for letter in item:
        if letter.isalpha()==False:
            return False
    return True



def read_file(fp):
    '''(file object)->dict
    Returns a dictionnary of a section of the words in the file inserted.
    Words in dictionnary only contains the words of the file that are all letters
    and that are longer or equal than two letters. These words are proccessed to
    not contain punctuation and to not have any upper case letters. The words are then
    a key in the dictionnary to a set of integers that represents the lines that
    the word is present in.
    Preconditon: file has to exist in the directory'''
    # YOUR CODE GOES HERE
    data = processLines(fp)
    #print(data)
    #print('\n\n\n',lst)

    set1=set()
    word=''
    alpha=True
    dictionnary={}
    for i in range(len(data)):
        for element in data[i]:
            word = remove_punctuation(element)
            alpha=is_a_word(word)
            if len(word)>=2 and alpha==True:
                set1.add(word)
            word=''
        #print(set1)
        
                 
        for member in set1:
            if member not in dictionnary:
                dictionnary[member]={i+1}
            else:
                dictionnary[member].add(i+1)
            
        set1=set()
        
    #print(dictionnary)
    return dictionnary
            


                    
        
        

    
    
def isValid(D,query): #helper function 4
    '''(dict,str)-->bool
    Returns True if the words in the query are present in the dictionnary.
    However if it is not True, the query inserted will be present in an
    error message printed from the function and the function will return False
    Precondition: words in query have to be seperated by a space'
    '''
    lst=query.lower().split()
    if query=='':
        print("Word '"+"' not in file")
        return False
    for i in lst:
        word = remove_punctuation(i)
        alpha = is_a_word(word)
        if alpha==False:
            print("Word '"+ word + "' not in file")
            return False
        if len(word)<2:
            print("Word '"+word+"' not in file")
            return False
        if word not in D:
            print("Word '"+word+"' not in file")
            return False

    return True

            
            
        
    
    
    
    
def find_coexistance(D, query):
    '''(dict,str)->list
    (dict,str)-> None(if the word in the query does not exist)
    
    Returns a list of integers that represents the lines that each word
    in the query are present in. If the word(s) in the query are not present
    in the dictonnary (D), function will return None.
    Returns an empty list if words of the query are in the dictionnary but
    are not coexisting
    Precondition: words in query have to be seperated by a space'''
    queryy=query.lower().split()
    new_words=[]
    variable = isValid(D,query)
    if variable==False:
        return None
    else:
        for i in queryy:
            word=remove_punctuation(i)
            new_words.append(word)
            word=''
        setnum=set()
        #sets are mutable cannot make em aliases    
        for item in D[new_words[0]]:
            setnum.add(item)
        
        for item in range(1,len(new_words)):
            setnum=setnum.intersection(d[new_words[item]])
            
        final_list= list(setnum)
        final_list.sort()
        return final_list
        

  

       
          
##############################
# main
##############################
file=open_file()
d=read_file(file)
query=input("Enter one or more words separated by spaces, or 'q' to quit: ").strip().lower()
indictor=True
# YOUR CODE GOES HERE
while query!='Q' and query!='q':
    b=find_coexistance(d, query)
    if b!=None:
        if len(b)>0:
            print("The one or more words you entered coexisted in the following lines of the file:")
            print(*b)
        else:
            print("The one or more words you entered does not coexist in a same line of the file.")
    query=input("Enter one or more words separated by spaces, or 'q' to quit: ").strip().lower()
    
    

    
    
