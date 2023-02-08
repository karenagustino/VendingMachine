package persistence;

import model.VendingMachine;
import model.Item;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Note: most of this test method is inspired by the phase 2 sample given
public class JsonTest {
    void checkItem(String itemName, int itemID, int itemQuantity, double itemCost, Item item) {
        assertEquals(itemName, item.getItemName());
        assertEquals(itemID, item.getItemID());
        assertEquals(itemQuantity, item.getItemQuantity());
        assertEquals(itemCost, item.getItemCost());
    }
}
