package model;

import org.json.JSONObject;
import persistence.Writable;

// EFFECTS: creates a value of all the inventories within the vending machine
public class Item extends VendingMachine implements Writable {

    private int itemQuantity;          // quantity of items left
    private int itemID;                // the ID of each item
    private String itemName;           // the item's name or brand
    private double itemCost;              // the cost of each item

    // EFFECTS: constructs the vending machine description
    public Item(String machineName, int machineID) {
        super(machineName, machineID);
    }

    // EFFECTS: assigns the item's descriptions in the vending machine
    public void itemInfo(String itemName, int itemID, int itemQuantity, double itemCost) {
        this.itemName = itemName;
        this.itemID = itemID;
        this.itemQuantity = itemQuantity;
        this.itemCost = itemCost;
    }

    // EFFECTS: gets the items ID
    public int getItemID() {
        return itemID;
    }

    // EFFECTS: gets the quantity of items left
    public int getItemQuantity() {
        return itemQuantity;
    }

    // EFFECTS: gets the item or product's name
    public String getItemName() {
        return itemName;
    }

    // EFFECTS: gets the cost of each item
    public double getItemCost() {
        return itemCost;
    }

    // MODIFIES: this
    // EFFECTS: adds the quantity of stock for each item
    public int addQuantity(int quantityAdded) {
        this.itemQuantity = this.itemQuantity + quantityAdded;
        return itemQuantity;

    }

    // MODIFIES: this
    // EFFECTS: deletes the quantity of stock for each item
    public int deleteQuantity(int quantityDeleted) {
        if (this.itemQuantity >= quantityDeleted) {
            this.itemQuantity = this.itemQuantity - quantityDeleted;
        }
        return itemQuantity;
    }

    // MODIFIES: this
    // EFFECTS: sets or change the quantity of the item for a certain amount
    public int setQuantity(int quantityChanged) {
        this.itemQuantity = quantityChanged;
        return itemQuantity;
    }

    // EFFECTS: makes the item to be written as a JSON
    @Override
    public JSONObject beJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("itemName", itemName);
        jsonObject.put("itemID", itemID);
        jsonObject.put("itemQuantity", itemQuantity);
        jsonObject.put("itemCost", itemCost);
        return jsonObject;
    }


}
