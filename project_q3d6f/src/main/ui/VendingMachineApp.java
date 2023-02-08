package ui;

import model.*;
import persistence.JsonRead;
import persistence.JsonWrite;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

// EFFECTS: links with the vending machine made in model and make it run
public class VendingMachineApp extends Main {

    private VendingMachine vendingMachine1;
    private Scanner userKeyboard;
    public static final String storeLink = "./data/machineTest.json";
    private JsonWrite jsonWrite;
    private JsonRead jsonRead;


    // EFFECTS: runs the vending machine application
    public VendingMachineApp() {
        this.userKeyboard = new Scanner(System.in);

        jsonWrite = new JsonWrite(storeLink);
        jsonRead = new JsonRead(storeLink);

        vendingMachine1 = new VendingMachine("Karen's business", 1);
        loadItems();

        runMachine();

    }


    // MODIFIES: this
    // EFFECTS: process user's input
    private void runMachine() {
        String response = "";

        //init();

        while (true) {
            displayOptions1();
            response = userKeyboard.next();
            if (response.equals("exit")) {
                System.out.println("See you next time!");
                System.exit(0);
            } else if (response.equals("1")) {
                addNewItem();
            } else if (response.equals("2")) {
                displayOptions2();
            }
        }
    }


    /*// MODIFIES: this
    // EFFECTS: initializing the inventories
    private void init() {
        Item banana = new Item("Karen's business", 1);
        banana.itemInfo("banana", 1, 5, 3.5);
        vendingMachine1.addItem(banana);

        Item chocolate = new Item("Karen's business", 1);
        chocolate.itemInfo("chocolate", 2, 8, 4.9);
        vendingMachine1.addItem(chocolate);

    }*/


    // MODIFIES: this
    // EFFECTS: allows user to manually add a new item
    private void addNewItem() {
        Random rand = new Random();
        int itemID = rand.nextInt();

        System.out.println("Please input your product name: ");
        String itemName = userKeyboard.next();
        System.out.println("Please input your product quantity: ");
        int itemQuantity = userKeyboard.nextInt();
        System.out.println("Please input your product cost:$ ");
        double itemCost = userKeyboard.nextDouble();

        System.out.println("NEW ITEM DESCRIPTION");
        System.out.println("Product name: " + itemName);
        System.out.println("Product quantity: " + itemQuantity);
        System.out.println("Product cost:$ " + itemCost);

        Item myObj = new Item("Karen's business", 1);
        myObj.itemInfo(itemName, itemID, itemQuantity, itemCost);
        vendingMachine1.addItem(myObj);

        //jsonWrite.write(vendingMachine1);
        saveItems();

        displayOptions1();
        System.out.println(" ");
    }

    // REQUIRES: item in inventory is <= 5
    // MODIFIES: this
    // EFFECTS: displays a notification as a user reminder
    private void notification() {
        ArrayList<Item> allItems = vendingMachine1.getVendingMachineInventory();
        for (Item allItem : allItems) {
            String itemName = allItem.getItemName();
            int itemQuantity = allItem.getItemQuantity();
            if (itemQuantity <= 5) {
                System.out.println("Your " + itemName + " needs extra attention! "
                        + "You have " + itemQuantity + " left.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: displays user's options
    private void displayOptions1() {
        notification();
        System.out.println("Hello! What do you want to do today ?");
        System.out.println("1. Add a new item");
        System.out.println("2. Check inventories");
        System.out.println("exit -> Exit application");

        String userInput = userKeyboard.nextLine();
        switch (userInput) {
            case "1":
                addNewItem();
                break;
            case "2":
                displayOptions2();
                break;
            case "exit":
                System.out.println("Goodbye!");
                System.exit(0);
        }
    }

    // MODIFIES: this
    // EFFECTS: user choose which inventory do they want to know more about
    private void displayOptions2() {
        ArrayList<Item> allItems = vendingMachine1.getVendingMachineInventory();
        for (int count = 0; count < allItems.size(); count++) {
            String itemName = allItems.get(count).getItemName();
            int itemID = allItems.get(count).getItemID();
            int itemQuantity = allItems.get(count).getItemQuantity();
            double itemCost = allItems.get(count).getItemCost();
            System.out.println(count + "." + itemName + ", " + itemQuantity + " left, $" + itemCost + ", Item ID: "
                    + itemID);
        }

        System.out.println("Do you want to modify items or exit? (type m or e)");
        String userInput1 = userKeyboard.next();
        if (userInput1.equals("e")) {
            System.out.println("See you next time!");
            System.exit(0);
        } else if (userInput1.equals("m")) {
            System.out.println("Which item do you want to modify? (Please type in the number)");
            int userInput2 = userKeyboard.nextInt();
            Item pickedObj = allItems.get(userInput2);

            displayOptions3(pickedObj);
        } else {
            System.out.println("Oops! I sense a typo! Please redo your selection.");
            displayOptions2();
        }
    }

    // MODIFIES: this
    // EFFECTS: user choose what to do with the inventory they choose
    private void displayOptions3(Item pickedObj) {
        System.out.println("What do you want to do with this product?");
        System.out.println("a. Add quantity");
        System.out.println("b. Delete quantity");
        System.out.println("c. Set or change quantity");
        System.out.println("exit -> Exit application");

        processOption3(pickedObj);

    }

    // MODIFIES: this
    // EFFECTS: processes the user's input from displayOptions3 method
    private void processOption3(Item pickedObj) {
        String userInput = userKeyboard.next();
        if (userInput.equals("a")) {
            System.out.println("How much do you want to add?");
            int userInputA = userKeyboard.nextInt();
            pickedObj.addQuantity(userInputA);
        } else if (userInput.equals("b")) {
            System.out.println("How much do you want to delete?");
            int userInputB = userKeyboard.nextInt();
            pickedObj.deleteQuantity(userInputB);
            if (pickedObj.getItemQuantity() < userInputB) {
                System.out.println("Oops! This product does not have that much quantity!");
            }
        } else if (userInput.equals("c")) {
            System.out.println("What is your new quantity?");
            int userInputC = userKeyboard.nextInt();
            pickedObj.setQuantity(userInputC);
        } else if (userInput.equals("exit")) {
            System.out.println("See you next time!");
            System.exit(0);
        }
        System.out.println(pickedObj.getItemName() + "'s new quantity is: " + pickedObj.getItemQuantity());

        //jsonWrite.write(vendingMachine1);
        saveItems();

        displayOptions2();
    }

    // MODIFIES: this
    // EFFECTS: save the items into JSON file, throws exception if file is not found
    // note: all code within this method is inspired by the phase 2 example
    private void saveItems() {
        try {
            jsonWrite.path();
            jsonWrite.write(vendingMachine1);
            jsonWrite.close();
            System.out.println("File is successfully saved at " + storeLink);
        } catch (FileNotFoundException exception) {
            System.out.println("Unable to find or save file at " + storeLink);
        }
    }

    // MODIFIES: this
    // EFFECTS: load the item from json file, throws exception if error in loading
    // note: all code within this method is inspired by the phase 2 example
    private void loadItems() {
        try {
            vendingMachine1 = jsonRead.readVending();
            System.out.println("Successfully loaded file at " + storeLink);
        } catch (IOException exception) {
            System.out.println("Sorry! Unable to load file at " + storeLink);
        }
    }







}
