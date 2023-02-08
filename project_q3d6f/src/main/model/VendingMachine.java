package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.HashSet;

// EFFECTS: creates a vending machine that has an inventory
public class VendingMachine implements Writable {

    private ArrayList<Item> vendingMachineInventory; // collects all the item names in inventory // was private final
    private String machineName;        // the machine, business, or company name
    private int machineID;             // the machine ID
    private boolean anyChanges;


    // EFFECTS: allows user to organize multiple "vending machines" or business
    public VendingMachine(String machineName, int machineID) {
        this.machineName = machineName;
        this.machineID = machineID;

        this.vendingMachineInventory = new ArrayList<Item>();
        anyChanges = false;
    }


    // EFFECTS: returns the vending machine's name
    public String getMachineName() {
        return machineName;
    }

    // EFFECTS: returns the vending machine's ID
    public int getMachineID() {
        return machineID;
    }

    // EFFECTS: returns the inventory
    public ArrayList<Item> getVendingMachineInventory() {
        return vendingMachineInventory;
    }

    // EFFECTS: returns the inventory size
    public int getInventorySize() {
        return vendingMachineInventory.size();
    }

    // MODIFIES: this
    // EFFECTS: adds item to inventory
    public void addItem(Item itemName) {
        vendingMachineInventory.add(itemName);
        EventLog.getInstance().logEvent(new Event("A new item was successfully added to your inventory"));
        anyChanges = true;
    }

    // MODIFIES: this
    // EFFECTS: make vending machine description an object
    @Override
    public JSONObject beJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Machine name", machineName);
        jsonObject.put("Machine ID", machineID);
        jsonObject.put("Items", inventoryJson());
        return jsonObject;
    }

    // MODIFIES: this
    // EFFECTS: put each item in the inventory into json
    // note: this method is inspired by the phase 2 example
    public JSONArray inventoryJson() {
        JSONArray allItems = new JSONArray();

        for (Item item : vendingMachineInventory) {
            allItems.put(item.beJson());
        }
        return allItems;

    }

//    // EFFECTS: notify observers if the inventory in vending machine changes
//    @Override
//    public void notifyObservers() {
//        for (Observer change : observers) {
//            change.update(anyChanges);
//        }
//    }

//    public JSONArray updateInventory(int index) {
//        ArrayList<String> list = new ArrayList<String>();
//        JSONArray jsonArray = new JSONArray();
//
//        int len = jsonArray.length();
//        for (int i = 0; i < len;i++) {
//            list.add(jsonArray.get(i).toString());
//        }
//        list.remove(index);
//        JSONArray jsArray = new JSONArray(list);
//        return jsArray;
//    }
}
