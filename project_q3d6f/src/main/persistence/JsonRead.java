package persistence;

import model.Item;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import model.VendingMachine;

// EFFECTS: reads the vending machine in terms of a JSON file
public class JsonRead {
    private String fileName;
    private VendingMachine vendingMachine;

    // EFFECTS: constructs the place where Json gets the source to read
    public JsonRead(String fileName) {
        this.fileName = fileName;
    }

    // EFFECTS: read the inventories in file and return it, throws an exception if found error
    // Note: all code within this method is inspired by the phase 2 sample given
    public VendingMachine readVending() throws IOException {
        String jsonData = readFile(fileName);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMachine(jsonObject);
    }

    // EFFECTS: reads the entire json file and translate it into a string
    // Note: most code within this method is inspired by the phase 2 sample given
    public String readFile(String fileName) throws IOException {
        String jsonData = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
        return jsonData;
    }

    // EFFECTS: parses the machine from being a json object into string
    // Note: most code within this method is inspired by the phase 2 sample given
    public VendingMachine parseMachine(JSONObject jsonObject) {
        String machineName = jsonObject.getString("Machine name");
        int machineID = jsonObject.getInt("Machine ID");
        vendingMachine = new VendingMachine(machineName, machineID);
        return parseInventory(jsonObject, vendingMachine);
    }


    // MODIFIES: vendingMachine
    // EFFECTS: parses the inventories in vending machine from being a json object
    // Note: most code within this method is inspired by the phase 2 sample given
    public VendingMachine parseInventory(JSONObject jsonObject, VendingMachine vendingMachine) {
        JSONArray itemArray = jsonObject.getJSONArray("Items");
        for (int i = 0; i < itemArray.length(); i++) {
            JSONObject eachItem = itemArray.getJSONObject(i);
            String itemName = eachItem.getString("itemName");
            int itemId = eachItem.getInt("itemID");
            int itemQuantity = eachItem.getInt("itemQuantity");
            double itemCost = eachItem.getDouble("itemCost");

            Item myObj = new Item("Karen's business", 1);
            myObj.itemInfo(itemName, itemId, itemQuantity, itemCost);

            vendingMachine.addItem(myObj);
        }
        return vendingMachine;
    }




}
