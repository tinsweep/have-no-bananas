have-no-bananas
===============

90.308 Group Project

Team 2 Project Proposal:

Title: Have No Bananas

Summary
Have No Bananas will help someone create and maintain a grocery list, or several grocery lists. The application design will allow for feature enhancements.

What is the GUI going to be
Swing.

Business Logic
What is your program to do?
This program will help the user create and store a grocery list, or grocery lists, if the user wants a separate list for different stores, such as a Trader Joe’s list and a Wal-Mart list.

What problem will be solved?
 Lost paper grocery lists
 Illegible handwriting
 Easy modification
 Can store common items

What features will have it?

Core Features:
 Allow user to keep lists for different stores
 Maintain a list of current inventory
 Product categories – produce, H&B, cleaning supplies, etc.
 Allow user to assign a price to items for calculating/estimating total grocery bill
 Maintain item history with prices so user doesn’t have to remember prices

Enhanced Features:
 Allow user to store recipe ingredients that the app can check against inventory
 Alert user to when perishable items need to be replaced
 Store frequently used items as staple items for auto-filling on item entry

Persistence
What data is saved?
Any grocery lists created or food items add to lists will be saved and available for retrieval or modification. There will also be storage of food items in stock.

How is it saved and accessed (read and written)?
The data will be saved in an embedded relational database. The data is accessed and manipulated with JDBC, table specific data access objects, and SQL.
