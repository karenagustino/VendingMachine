## 🥫 AN INVENTORY VENDING MACHINE FOR BUSINESSES 🥫

_What will the application do?_ 🍿
- keep track of store inventories (how many stocks of items left to be purchased)
- links with the store cashier database (if an item is being purchased, the vending machine items will decrease)
- sends notifications if the item stock is less than 5 

_Who will use it?_ 👩‍💼
- Business owners who want to keep track of inventory

_Why is this project of interest to you?_ 🌅

As a young business owner, I find it very challenging to always keep track of how many stock items I have left. 
It is very difficult for me to receive a customer's offer, only to know that the item they asked for is out of stock. Last summer, I went to a convenience store in the US and found a really cool digitalized vending machine. It is a touch-screen vending machine with motion sensors, where whenever a customer passes by the machine, lights and 2D pictures of the itmes will be displayed. That's when I thought of integrating this problem and idea into a small interactive project. 

User Stories:
- As a user, I want to be able to **input** the quantities for each item
- As a user, I want to be able to **add** new items into my vending machine
- As a user, I want to be able to manually **remove** the quantities for each item
- As a user, I want to be able to see a **notification** stating that the item must be restocked.
- As a user, I want to be able to **check** what items are in my inventory.
- As a user, I want to be able to **save** my vending machine's inventory into file.
- As a user, I want to be able to **load** my old and new inventories from file.


_**Phase 4: Task 2**_

Log examples:
"New item is now saved at ./data/machineTest.json
Thu Aug 11 00:56:55 PDT 2022
A new item was successfully added to your inventory
Thu Aug 11 00:57:07 PDT 2022
A new item was successfully added to your inventory"

The above example shows what the console prints after user adds two new items and exits the application.

There are no log events for add, delete, or set quantity as those methods produce an integer value, whereas the EventLog
always prints out a type String. Also, as these methods in the Item class are not used in the GUI (but rather in the 
CLI), they will never be printed in the console. Hence, there will always be an added Event log as users can add items.


**_Phase 4: Task 3_**

Looking back to my UML diagram, I think one improvement I could make is to refactor the MachineGUI and 
VendingMachineApp classes in a way that users can choose whether to use this program as a command-line interface or 
graphical user interface. One way to do this is to make Main as an abstract class where either MachineGUI or 
VendingMachineApp can extend, and also create another button that gives the option for user to choose the type interface. I noticed this 
because I realized how these two classes take up the majority of the UML diagram.

Additionally, if I had more time to do this project, I would have refactored most of the JPanels and JButtons 
that have similar code to each other. Although this is not necessarily portrayed in my UML diagram, I feel that this 
change would improve my code readability as it shortens the amount of methods I used. To do this, perhaps I could 
refactor by extracting the method (particularly the lines that styles the JPanel or JButton) and calling it whenever is 
needed.






