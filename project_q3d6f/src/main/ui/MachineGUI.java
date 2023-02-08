package ui;

import model.*;
import model.Event;
import persistence.JsonWrite;
import persistence.JsonRead;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Vector;

// the vending machine GUI
public class MachineGUI extends JFrame implements ActionListener {
    private VendingMachine vendingMachine1;

    private JPanel titlePanel;
    private JPanel middleMenuPanel;
    private JPanel inputItemPanel;
    private JPanel inventoryPanel;
    private JPanel exitPanel;

    private JLabel setQuestion;
    private JLabel exitLabel;
    private JLabel newItemLabel;

    private JButton addNewItemButton;
    private JButton checkInventoriesButton;
    private JButton exitAppButton;
    private JButton addButton;
    private JButton removeButton;
    private JButton backtoMenuButton1;
    private JButton backtoMenuButton2;
    private JButton loadButton;

    private JsonWrite jsonWrite;
    private JsonRead jsonRead;

    public static final String storeLink = "./data/machineTest.json";

    private String[] columnHeader;

    private JTextField newItemName;
    private JTextField newItemQuantity;
    private JTextField newItemCost;

    private HashSet<Item> inventory; // this was arraylist
    private Item newItem;

    private DefaultTableModel model;
    private JTable table;

    // EFFECTS: constructs the vending machine GUI
    public MachineGUI() {
        vendingMachine1 = new VendingMachine("Karen's business", 1);
        newItem = new Item(vendingMachine1.getMachineName(), vendingMachine1.getMachineID());
        jsonWrite = new JsonWrite(storeLink);
        jsonRead = new JsonRead(storeLink);
        inventory = new HashSet<>();

        initButtons();

        loadMachine();
        setTitlePanel();
        middleMenuPanel();
        inputItemPanel();
        printInventoriesPanel();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Vending machine application");
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setLayout(new FlowLayout());
        this.setSize(500, 500);
        this.getContentPane().setBackground(new Color(0x89CFF0));
        this.setVisible(true);


    }

    // MODIFIES: this
    // EFFECTS: initialize all buttons used in the program
    public void initButtons() {
        addNewItemButton = new JButton();
        checkInventoriesButton = new JButton();
        exitAppButton = new JButton("Exit Application");
        addButton = new JButton("ADD ITEM");
        removeButton = new JButton("REMOVE ITEM");
        backtoMenuButton1 = new JButton("Back to menu");
        backtoMenuButton2 = new JButton("Back to menu");
        loadButton = new JButton("Load items");
    }

    // EFFECTS: an override method
    @Override
    public void actionPerformed(ActionEvent e) {
    }

    // MODIFIES: this
    // EFFECTS: the title panel that displays the name of vending machine
    public void setTitlePanel() {
        titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,5));
        titlePanel.setBackground(Color.pink);
        titlePanel.setLayout(new FlowLayout());

        JLabel title = new JLabel("Welcome to " + "Karen's business", JLabel.CENTER); // edit machineName
        title.setFont(new Font("Calibri", Font.BOLD,30));
        titlePanel.add(title, BorderLayout.CENTER);
        titlePanel.setPreferredSize(new Dimension(480,100));
        titlePanel.setMaximumSize(new Dimension(480,100));

        ImageIcon iceCream = new ImageIcon("./data/eskrim.png");
        Image image = iceCream.getImage();
        Image imageScale = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
        iceCream = new ImageIcon(imageScale, BorderLayout.LINE_END);

        JLabel pictureLabel = new JLabel(iceCream);
        titlePanel.add(pictureLabel, BorderLayout.PAGE_END);

        this.getContentPane().add(titlePanel);


    }

    // MODIFIES: t
    // EFFECTS: the main menu panel that allows user to choose option - add new item/check items/exit app
    public void middleMenuPanel() {
        middleMenuPanel = new JPanel();
        middleMenuPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,5));
        middleMenuPanel.setBackground(new Color(0x30D5C8));
        middleMenuPanel.setLayout(new FlowLayout());
        middleMenuPanel.setPreferredSize(new Dimension(480,340));
        middleMenuPanel.setMaximumSize(new Dimension(480,340));
        //middleMenuPanel.setLayout(new BoxLayout(middleMenuPanel,BoxLayout.Y_AXIS));

        setQuestion = new JLabel("Hello! What do you want to do today?", JLabel.CENTER);
        setQuestion.setFont(new Font("Calibri", Font.ITALIC,22));
        middleMenuPanel.add(setQuestion, BorderLayout.CENTER);
        this.getContentPane().add(middleMenuPanel);


        addNewItemButton();
        checkInventoriesButton();
        exitAppButton();


    }

    // EFFECTS: button to add a new item
    public void addNewItemButton() {
        addNewItemButton.setPreferredSize(new Dimension(200,50));
        addNewItemButton.setMaximumSize(new Dimension(200,50));
        addNewItemButton.addActionListener(new AddItemListener(addNewItemButton));
        addNewItemButton.setText("Add a New Item");
        addNewItemButton.setFont(new Font("Calibri", Font.ITALIC,15));
        addNewItemButton.setFocusable(false);
        middleMenuPanel.add(addNewItemButton, BorderLayout.AFTER_LAST_LINE);

    }

    // MODIFIES: this
    // EFFECTS: class to listen or produce the next result after addNewItemButton is pressed
    class AddItemListener implements ActionListener {
        private JButton jbutton;

        // EFFECTS: constructs the add item button
        public AddItemListener(JButton jbutton) {
            this.jbutton = jbutton;
        }

        // MODIFIES: this
        // EFFECTS: removes the previous panel and adds the next panel (which is the input Item panel)
        @Override
        public void actionPerformed(ActionEvent e) {
            getContentPane().remove(middleMenuPanel);
            inputItemPanel();
            getContentPane().add(inputItemPanel);
            repaint();
            revalidate();
        }
    }

    // MODIFIES: this
    // EFFECTS: button to check or load inventories
    public void checkInventoriesButton() {
        checkInventoriesButton.setPreferredSize(new Dimension(200,50));
        checkInventoriesButton.setMaximumSize(new Dimension(200,50));
        checkInventoriesButton.addActionListener(new InventoryListener(checkInventoriesButton));
        checkInventoriesButton.setText("Check Inventories");
        checkInventoriesButton.setFont(new Font("Calibri", Font.ITALIC,15));
        checkInventoriesButton.setFocusable(false);
        middleMenuPanel.add(checkInventoriesButton, BorderLayout.AFTER_LAST_LINE);
    }

    // gives functionality of the checkInventoriesButton
    class InventoryListener implements ActionListener {
        private JButton jbutton;

        // EFFECTS: constructs to initialize the button
        public InventoryListener(JButton jbutton) {
            this.jbutton = jbutton;
        }

        // MODIFIES: this
        // EFFECTS: removes the current panel and adds the next panel (which is the printInventoriesPanel)
        @Override
        public void actionPerformed(ActionEvent e) {
            getContentPane().remove(middleMenuPanel);
            printInventoriesPanel();
            getContentPane().add(inventoryPanel);
            repaint();
            revalidate();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads or prints the inventory
    // *Note: this method is inspired by "How to Add JSON data to a JTable in JAVA" at tutorialmeta.com
    public void printInventoriesPanel() {
        inventoryPanel = new JPanel();
        inventoryPanel.setPreferredSize(new Dimension(480, 250));
        inventoryPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Your Inventories", TitledBorder.CENTER, TitledBorder.TOP));
        columnHeader = new String[]{"Item name", "Item ID", "Item Quantity", "Item cost"};

        model = new DefaultTableModel(columnHeader, 0);
        for (Item item: inventory) {
            Vector<String> row = new Vector<String>();
            row.add(item.getItemName());
            String stringID = Integer.toString(item.getItemID());
            row.add(stringID);
            String stringQuantity = Integer.toString(item.getItemQuantity());
            row.add(stringQuantity);
            String stringCost = Double.toString(item.getItemCost());
            row.add(stringCost);
            //model.addRow(row);
        }

        itemListRec();
        //setButtonPanel();
        //loadAppButton();
        backtoMenuButton2();
        table = new JTable(model);
        inventoryPanel.add(new JScrollPane(table), BorderLayout.NORTH);



    }

    // MODIFIES: this
    // EFFECTS: recurse around all items in the itemlist
    public void itemListRec() {
        ArrayList<Item> itemList = vendingMachine1.getVendingMachineInventory();
        for (Item item: itemList) {
            Vector<String> row = new Vector<String>();
            row.add(item.getItemName());
            String stringID = Integer.toString(item.getItemID());
            row.add(stringID);
            String stringQuantity = Integer.toString(item.getItemQuantity());
            row.add(stringQuantity);
            String stringCost = Double.toString(item.getItemCost());
            row.add(stringCost);
            model.addRow(row);
        }
    }


    // MODIFIES: this
    // EFFECTS: loads the vending machine from json using jsonRead
    public void loadMachine() {
        try {
            vendingMachine1 = jsonRead.readVending();
            System.out.println("Successfully loaded file at " + storeLink);
        } catch (IOException exception) {
            System.out.println("Sorry! Unable to load file at " + storeLink);
        }
    }

//    // MODIFIES: this
//    // EFFECTS: button to load the inventory
//    public void loadAppButton() {
//        try {
//            loadButton.setPreferredSize(new Dimension(80,30));
//            loadButton.setMaximumSize(new Dimension(80,30));
//            loadButton.addActionListener(new LoadListener(loadButton));
//            loadButton.setFont(new Font("Calibri", Font.ITALIC,15));
//            loadButton.setFocusable(false);
//            inventoryPanel.add(loadButton, BorderLayout.NORTH);
//        } catch (NullPointerException e) {
//            System.out.println("Oops! You have no items yet!");
//            inventoryPanel.add(loadButton, BorderLayout.NORTH);
//        }
//    }

//    // EFFECTS: provide functionality for the load application button
//    class LoadListener implements ActionListener {
//        private JButton jbutton;
//
//        // EFFECTS: constructs to initialize the button
//        public LoadListener(JButton jbutton) {
//            this.jbutton = jbutton;
//        }
//
//        // MODIFIES: this
//        // EFFECTS: removes the current panel and adds the next panel (which is the modifyPanel)
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            try {
//                vendingMachine1 = jsonRead.readVending();
////                for (Item item : inventory.viewClothing()) {
////                    model.addElement(displayClothing(clothing));
////                }
//                System.out.println("Successfully loaded file at " + storeLink);
//            } catch (IOException exception) {
//                System.out.println("Sorry! Unable to load file at " + storeLink);
//            }
//        }
//
////        public void displayItems() {
////            return ("")
////        }
//    }


//    // MODIFIES: this
//    // EFFECTS: panel to input save and exit buttons
//    public void setButtonPanel() {
//        buttonPanel = new JPanel();
//        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,5));
//        buttonPanel.setBackground(new Color(0x30D5C8));
//        buttonPanel.setLayout(new FlowLayout());
//        buttonPanel.setPreferredSize(new Dimension(80,50));
//        buttonPanel.setMaximumSize(new Dimension(80,50));
//        buttonPanel.setLayout(new BorderLayout());
//        inventoryPanel.add(buttonPanel, BorderLayout.SOUTH);
//
//        //setRemoveButton();
//    }


//    // MODIFIES: this
//    // EFFECTS: remove button after changes made in the JTable
//    public void setRemoveButton() {
//        removeButton.setPreferredSize(new Dimension(80, 50));
//        removeButton.setMaximumSize(new Dimension(80, 50));
////        removeButton.addActionListener(new RemoveListener(removeButton));
//        removeButton.addActionListener(this);
//        removeButton.setFont(new Font("Calibri", Font.ITALIC, 15));
//        removeButton.setFocusable(false);
//        removeButton.setVisible(true);
//        buttonPanel.add(removeButton, BorderLayout.PAGE_END);
//    }
    
//    // gives functionality for the removeButton
//    // *NOTE: this class is inspired by "How can we remove a selected row from a JTable" at tutorialspoint.com
//    class RemoveListener implements ActionListener {
//        private JButton jbutton;
//
//        // EFFECTS: constructs to initialize the button
//        public RemoveListener(JButton jbutton) {
//            this.jbutton = jbutton;
//        }
//
//        // MODIFIES: this
//        // EFFECTS: deletes the item from inventory and prints a confirmation statement that item has been removed
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            if (table.getSelectedRow() != -1) {
//                model.removeRow(table.getSelectedRow());
//                JOptionPane.showMessageDialog(null, "Selected item row has been deleted");
//            } else {
//                System.out.println("Oops! Row is unavailable.");
//            }
//        }
//    }


    // MODIFIES: this
    // EFFECTS: button to add a new item
    public void exitAppButton() {
        exitAppButton.setPreferredSize(new Dimension(200,50));
        exitAppButton.setMaximumSize(new Dimension(200,50));
        exitAppButton.addActionListener(new ExitListener(exitAppButton));
        exitAppButton.setFont(new Font("Calibri", Font.ITALIC,15));
        exitAppButton.setFocusable(false);
        middleMenuPanel.add(exitAppButton, BorderLayout.AFTER_LAST_LINE);
    }

    // EFFECTS: provide functionality for the exit application button
    class ExitListener implements ActionListener {
        private JButton jbutton;

        // EFFECTS: constructs to initialize the button
        public ExitListener(JButton jbutton) {
            this.jbutton = jbutton;
        }

        // MODIFIES: this
        // EFFECTS: removes the current panel and adds the next panel (which is the modifyPanel)
        @Override
        public void actionPerformed(ActionEvent e) {
            getContentPane().remove(middleMenuPanel);
            exitPanel();
            getContentPane().add(exitLabel);
            repaint();
            revalidate();

        }
    }

    // MODIFIES: this
    // EFFECTS: panel to exit application
    public void exitPanel() {
        exitPanel = new JPanel();
        exitPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,5));
        exitPanel.setBackground(new Color(0x30D5C8));
        exitPanel.setLayout(new FlowLayout());
        exitPanel.setPreferredSize(new Dimension(480,340));
        exitPanel.setMaximumSize(new Dimension(480,340));

        exitLabel = new JLabel("Thank you for coming! See you next time.", JLabel.CENTER);
        exitLabel.setFont(new Font("Calibri", Font.ITALIC, 22));
        exitPanel.add(exitLabel, BorderLayout.CENTER);


        printLog(EventLog.getInstance());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // EFFECTS: prints the event log after exit application
    public void printLog(EventLog log) {
        for (Event event: log) {
            System.out.println(event.toString());
        }
    }


    // MODIFIES: this
    // EFFECTS: a new panel to display user input of new item details
    public void inputItemPanel() {
        inputItemPanel = new JPanel();
        //inputItemPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,5));
        inputItemPanel.setBackground(new Color(0x30D5C8));
        inputItemPanel.setLayout(new FlowLayout());
        inputItemPanel.setPreferredSize(new Dimension(480,340));
        inputItemPanel.setMaximumSize(new Dimension(480,340));
        inputItemPanel.setLayout(new BoxLayout(inputItemPanel, BoxLayout.Y_AXIS));

        newItemLabel = new JLabel("INPUT NEW ITEM", JLabel.CENTER);
        newItemLabel.setFont(new Font("Calibri", Font.ITALIC, 22));
        inputItemPanel.add(newItemLabel, BorderLayout.CENTER);
        newItemDetails();
        addItemButton();
        backtoMenuButton1();
    }

    // MODIFIES: this
    // EFFECTS: textfields for user to input their new item details
    public void newItemDetails() {
        JLabel itemNameLabel = new JLabel("Insert product name: ", JLabel.CENTER);
        itemNameLabel.setFont(new Font("Calibri", Font.ITALIC, 15));
        newItemName = new JTextField(10);
        inputItemPanel.add(itemNameLabel);
        inputItemPanel.add(newItemName,BorderLayout.AFTER_LAST_LINE);
        //textFieldList.add(newItemName);

        JLabel itemCostLabel = new JLabel("Insert product cost: ", JLabel.CENTER);
        itemCostLabel.setFont(new Font("Calibri", Font.ITALIC, 15));
        newItemCost = new JTextField(10);
        inputItemPanel.add(itemCostLabel);
        inputItemPanel.add(newItemCost,BorderLayout.AFTER_LAST_LINE);
        //textFieldList.add(newItemCost);

        JLabel itemQuantityLabel = new JLabel("Insert product quantity: ", JLabel.CENTER);
        itemQuantityLabel.setFont(new Font("Calibri", Font.ITALIC, 15));
        newItemQuantity = new JTextField(10);
        inputItemPanel.add(itemQuantityLabel);
        inputItemPanel.add(newItemQuantity,BorderLayout.AFTER_LAST_LINE);
        //textFieldList.add(newItemQuantity);


    }

    // EFFECTS: create the item based on user input JTextfields
    public void makeItem() {
        String itemNameText = newItemName.getText();
        String itemQuantityText = newItemQuantity.getText();
        String itemCostText = newItemCost.getText();


        String itemName = itemNameText;
        int itemQuantity = 0;
        double itemCost = 0;

        try {
            itemQuantity = Integer.parseInt(itemQuantityText);
            //newItem.setItemQuantity(itemQuantity);
        } catch (NumberFormatException e) {
            System.out.println("item quantity must be an integer, quantity was set to 1");
        }

        try {
            itemCost = Double.parseDouble(itemCostText);
            //newItem.setItemCost(itemCost);
        } catch (NumberFormatException e) {
            System.out.println("item cost must be an integer, cost was set to $1");
        }


        if (!inventory.contains(newItem)) {
            newItem.itemInfo(itemName, 1, itemQuantity, itemCost);
            inventory.add(newItem);
            vendingMachine1.addItem(newItem);
        } else {
            System.out.println("Oops! You have a duplicate product.");
        }
    }


    // MODIFIES: this
    // EFFECTS: button to add the new item
    public void addItemButton() {
        addButton.setPreferredSize(new Dimension(80,30));
        addButton.setMaximumSize(new Dimension(80,30));
        addButton.addActionListener(new AddListener(addButton));
        addButton.setFont(new Font("Calibri", Font.ITALIC,12));
        addButton.setFocusable(false);

        inputItemPanel.add(addButton, BorderLayout.AFTER_LAST_LINE);

    }

    // gives functionality for the saveButton
    private class AddListener implements ActionListener {
        private JButton jbutton;

        // EFFECTS: constructs to initialize the button
        public AddListener(JButton jbutton) {
            this.jbutton = jbutton;
        }

        // EFFECTS: saves user input by using jsonWrite
        private void saveData() {
            try {
                jsonWrite.path();
                jsonWrite.write(vendingMachine1);
                jsonWrite.close();
                System.out.println("New item is now saved at " + storeLink);
            } catch (FileNotFoundException e) {
                System.out.println("Oops! Unable to save file to " + storeLink);
            }
        }

        // MODIFIES: this
        // EFFECTS: makes user input as one Item object, save the data into json,
        // and prints a statement saying that the data have been saved
        @Override
        public void actionPerformed(ActionEvent e) {
            makeItem();
            saveData();

        }
    }

    // MODIFIES: this
    // EFFECTS: button to go back to the main menu (from add item panel)
    public void backtoMenuButton1() {
        backtoMenuButton1.setPreferredSize(new Dimension(80,30));
        backtoMenuButton1.setMaximumSize(new Dimension(80,30));
        backtoMenuButton1.addActionListener(new BackMenuListener1(backtoMenuButton1));
        backtoMenuButton1.setFont(new Font("Calibri", Font.ITALIC,12));
        backtoMenuButton1.setFocusable(false);

        inputItemPanel.add(backtoMenuButton1, BorderLayout.AFTER_LAST_LINE);

    }

    // gives functionality for the backToMenuButton1
    private class BackMenuListener1 implements ActionListener {
        private JButton jbutton;

        // EFFECTS: constructs to initialize the button
        public BackMenuListener1(JButton jbutton) {
            this.jbutton = jbutton;
        }

        // MODIFIES: this
        // EFFECTS: changes the panel to the middle menu panel
        @Override
        public void actionPerformed(ActionEvent e) {
            getContentPane().remove(inputItemPanel);
            getContentPane().add(middleMenuPanel);
            repaint();
            revalidate();
        }
    }


    // MODIFIES: this
    // EFFECTS: button to go back to the main menu (from check inventories menu)
    public void backtoMenuButton2() {
        backtoMenuButton2.setPreferredSize(new Dimension(80,30));
        backtoMenuButton2.setMaximumSize(new Dimension(80,30));
        backtoMenuButton2.addActionListener(new BackMenuListener2(backtoMenuButton2));
        backtoMenuButton2.setFont(new Font("Calibri", Font.ITALIC,12));
        backtoMenuButton2.setFocusable(false);

        inventoryPanel.add(backtoMenuButton2, BorderLayout.AFTER_LAST_LINE);

    }

    // gives functionality for the backToMenuButton2
    private class BackMenuListener2 implements ActionListener {
        private JButton jbutton;

        // EFFECTS: constructs to initialize the button
        public BackMenuListener2(JButton jbutton) {
            this.jbutton = jbutton;
        }

        // MODIFIES: this
        // EFFECTS: changes the panel to the middle menu panel
        @Override
        public void actionPerformed(ActionEvent e) {
            getContentPane().remove(inventoryPanel);
            getContentPane().add(middleMenuPanel);
            repaint();
            revalidate();
        }
    }


}