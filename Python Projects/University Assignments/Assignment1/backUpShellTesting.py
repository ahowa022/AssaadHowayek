Python 3.7.0 (v3.7.0:1bf9cc5093, Jun 27 2018, 04:06:47) [MSC v.1914 32 bit (Intel)] on win32
Type "copyright", "credits" or "license()" for more information.
>>> 
== RESTART: C:\Users\assaa\Documents\Assignment 1 (ITI1120)\a1_300057716.py ==
>>> #testing Question 1
>>> pythagorean_pair(2,2)
False
>>> pythagorean_pair(6,2)
False
>>> pythagorean_pair(6,8)
True
>>> pythagorean_pair(300,-400)
True
>>> pythagorean_pair(3,4)
True
>>> 
>>> #Testing Question 2
>>> 
>>> mh2kh(5)
8.0467
>>> mh2kh(110.4)
177.67113600000002
>>> 
>>> #testing question 3
>>> 
>>> in_out(0,0,2.5)
Enter a x coordinate of the query point: 0
Enter a y coordinate of the query point: 1.2
True
>>> in_out(2.5,1,1)
Enter a x coordinate of the query point: -1
Enter a y coordinate of the query point: 1.5
False
>>> in_out(2,1,4)
Enter a x coordinate of the query point: 3
Enter a y coordinate of the query point: 3
True
>>> 
>>> 
>>> #Testing Question 4
>>> 
>>> safe(93)
False
>>> safe(82)
True
>>> safe(81)
False
>>> safe(29)
False
>>> safe(36)
False
>>> safe(9)
False
>>> safe(7)
True
>>> 
>>> 
>>> #Testing Question 5
>>> 
>>> quote_maker("I love ITI 1120.", "Assaad Howayek", 2018)
'In 2018, a person called Assaad Howayek said: "I love ITI 1120."'
>>> quote_maker("I would never die for my beliefs bcz i might be wrong.", "Bertrand Russel", 1951)
'In 1951, a person called Bertrand Russel said: "I would never die for my beliefs bcz i might be wrong."'
>>> 
>>> 
>>> #Testing Question 6
>>> 
>>> quote_displayer()
Insert a quote someone said: I love cereal
What was his name? Assaad Howayek
What year did he say that: 2018
In 2018, a person called Assaad Howayek said: "I love cereal"
>>> 
>>> 
>>> #Testing Question 7
>>> 
>>> rps_winner()
What choicice did player 1 make?
Type one of the following options: rock, paper, scissors: rock
What choicice did player 2 make?
Type one of the following options: rock, paper, scissors: paper
Player one wins. That is False
It's a tie, that is not True
>>> rps_winner()
What choicice did player 1 make?
Type one of the following options: rock, paper, scissors: paper
What choicice did player 2 make?
Type one of the following options: rock, paper, scissors: rock
Player one wins. That is True
It's a tie, that is not True
>>> rps_winner()
What choicice did player 1 make?
Type one of the following options: rock, paper, scissors: scissors
What choicice did player 2 make?
Type one of the following options: rock, paper, scissors: paper
Player one wins. That is True
It's a tie, that is not True
>>> rps_winner()
What choicice did player 1 make?
Type one of the following options: rock, paper, scissors: paper
What choicice did player 2 make?
Type one of the following options: rock, paper, scissors: paper
Player one wins. That is False
It's a tie, that is not False
>>> 
>>> 
>>> #Testing Question 8
>>> 
>>> fun(7)
0.25
>>> fun(20)
0.3404319590043982
>>> fun(0.1)
0.12284042345856817
>>> 
>>> 
>>> #Testing Question 9
>>> 
>>> ascii_name_plaque("Assaad Howayek")
********************
*                  *
* _Assaad Howayek_ *
*                  *
********************
>>> ascii_name_plaque("Captain Kara 'Starbuck' Thrace")
************************************
*                                  *
* _Captain Kara 'Starbuck' Thrace_ *
*                                  *
************************************
>>> 
>>> 
>>> #Testing Question 11
>>> 
>>> alogical(5.4)
3
>>> alogical(4)
2
>>> alogical(1000)
10
>>> alogical(4200231)
23
>>> 
>>> 
>>> 
>>> #Testing Question 12
>>> 
>>> time_format(8,0)
'8 oclock'
>>> time_format(9,2)
'9 oclock'
>>> time_format(10,58)
'11 oclock'
>>> time_format(9,9)
'10 minutes past 9 oclock'
>>> time_format(15,30)
'half past 15 oclock'
>>> time_format(7,28)
'half past 7 oclock'
>>> time_format(17,42)
'20 minutes until 18 oclock'
>>> time_format(23,13)
'15 minutes past 23 oclock'
>>> time_format(23,42)
'20 minutes until 0 oclock'
>>> time_format(0,29)
'half past 0 oclock'
>>> time_format(11,59)
'12 oclock'
>>> time_format(23,58)
'0 oclock'
>>> time_format(0,1)
'0 oclock'
>>> time_format(11,1)
'11 oclock'
>>> 
>>> 
>>> #Testing Question 13
>>> 
>>> cad_cashier(10.58,11)
0.4
>>> cad_cashier(98.87,100)
1.15
>>> cad_cashier(10.58,15)
4.4
>>> cad_cashier(10.54,15)
4.45
>>> cad_cashier(10.55,15)
4.45
>>> cad_cashier(10.50,15)
4.5
>>> cad_cashier(14, 15.10)
1.1
>>> 
>>> 
>>> #Testing Question 14
>>> 
>>> min_CAD_coins(10.58,11)
(0, 0, 1, 1, 1)
>>> min_CAD_coins(98.87,100)
(0, 1, 0, 1, 1)
>>> min_CAD_coins(10.58,15)
(2, 0, 1, 1, 1)
>>> min_CAD_coins(10.55, 15)
(2, 0, 1, 2, 0)
>>> min_CAD_coins(10.52, 15)
(2, 0, 2, 0, 0)
>>> min_CAD_coins(10.50,15)
(2, 0, 2, 0, 0)
>>> min_CAD_coins(3,20)
(8, 1, 0, 0, 0)
>>> 
