package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineTest {
    private VendingMachine vendingMachine1;
    private Item chicken;
    private String itemName = "tomato";
    private int itemID = 1;
    private int itemQuantity = 20;
    private double itemCost = 5.5;

    @BeforeEach
    void runBefore() {
        this.vendingMachine1 = new VendingMachine("Amazon", 1);
        this.chicken = new Item("Amazon", 1);
        chicken.itemInfo("Chicken", 1, 4, 20);
    }


    @Test
    void addItemTest(){
        Item beef = new Item("Amazon", 1);
        beef.itemInfo("Kobe beef", 2, 2, 49.9);
        vendingMachine1.addItem(beef);
        assertEquals(1, vendingMachine1.getVendingMachineInventory().size());
        Item pork = new Item("Amazon", 1);
        pork.itemInfo("Pork belly", 3, 5, 30);
        vendingMachine1.addItem(pork);
        assertEquals(2, vendingMachine1.getInventorySize());
    }

    @Test
    void checkMachineDetails(){
        assertEquals("Amazon", vendingMachine1.getMachineName());
        assertEquals(1, vendingMachine1.getMachineID());
    }

    @Test
    void checkInventory(){
        assertEquals("Chicken", chicken.getItemName());
        assertEquals(1, chicken.getItemID());
        assertEquals(4, chicken.getItemQuantity());
        assertEquals(20, chicken.getItemCost());
    }

    @Test
    void addQuantityTest(){
        this.chicken.addQuantity(10);
        assertEquals(14, chicken.getItemQuantity());
    }

    @Test
    void deleteQuantityTest(){
        this.chicken.deleteQuantity(2);
        assertEquals(2, chicken.getItemQuantity());
        this.chicken.deleteQuantity(5);
        assertEquals(2, chicken.getItemQuantity());
    }

    @Test
    void setQuantityTest(){
        this.chicken.setQuantity(20);
        assertEquals(20, chicken.getItemQuantity());
    }

    @Test
    void beJsonTest1(){
        JSONArray allItems = new JSONArray();
        JSONObject anItem = new JSONObject();
        anItem.put("itemName", "candy");
        anItem.put("itemID", "1567");
        anItem.put("itemQuantity", "30");
        anItem.put("itemCost", "2.5");
        allItems = allItems.put(anItem);


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Machine name", "Amazon");
        jsonObject.put("Machine ID", 1);
        jsonObject.put("Items", allItems);



        assertEquals("Amazon", jsonObject.get("Machine name"));
        assertEquals(1, jsonObject.get("Machine ID"));
        assertEquals(allItems, jsonObject.get("Items"));

    }

    @Test
    void beJsonTest2(){
        JSONArray jsonArray = new JSONArray();

        jsonArray = vendingMachine1.inventoryJson();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Machine name", "Amazon");
        jsonObject.put("Machine ID", 1);
        jsonObject.put("Items", jsonArray);

        assertEquals("Amazon", jsonObject.get("Machine name"));
        assertEquals(1, jsonObject.get("Machine ID"));
        assertEquals(jsonArray, jsonObject.get("Items"));
    }

    @Test
    void beJsonTest3(){
        assertEquals("Chicken",chicken.beJson().get("itemName"));
        assertEquals(1,chicken.beJson().get("itemID"));
        assertEquals(4,chicken.beJson().get("itemQuantity"));
        assertEquals(20.0,chicken.beJson().get("itemCost"));

        assertEquals("Amazon",vendingMachine1.beJson().get("Machine name"));
        assertEquals(1,vendingMachine1.beJson().get("Machine ID"));
    }

    @Test
    void beJsonTest4(){
        JSONArray allItems = new JSONArray();
        JSONObject anItem = new JSONObject();
        anItem.put("itemName", "candy");
        anItem.put("itemID", "1567");
        anItem.put("itemQuantity", "30");
        anItem.put("itemCost", "2.5");
        allItems = allItems.put(anItem);

        Item item = new Item("Karen's business" ,1);
        item.itemInfo("candy",1567,30,2.5);
        vendingMachine1.addItem(item);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject.put("Items", allItems));
        jsonArray = vendingMachine1.inventoryJson();
        String arrayString = jsonArray.toString();

        assertEquals("[{\"itemID\":1567,\"itemName\":\"candy\",\"itemQuantity\":30,\"itemCost\":2.5}]",
                arrayString);


    }
}