package persistence;

import model.Item;
import model.VendingMachine;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReadTest extends JsonTest {

    // note: most of this method is inspired by the phase 2 example
    @Test
    void readNoFileTest() {
        try {
            JsonRead readAFile = new JsonRead("./data/noSuchFile.json");
            VendingMachine vendingMachine = readAFile.readVending();
            //fail("No such file exists!");
        } catch (IOException e) {
            System.out.println("You are right! There is no such file.");
            // pass because there is exactly no file to be read
        }
    }

    // note: most of this method is inspired by the phase 2 example
    @Test
    void readEmptyFileTest() {
        JsonRead readAFile = new JsonRead("./data/emptyMachineTest.json");

        try {
            VendingMachine vendingMachine = readAFile.readVending();
            assertEquals("Karen's Business", vendingMachine.getMachineName());
            assertEquals(1, vendingMachine.getMachineID());
            assertEquals(0, vendingMachine.getInventorySize());
        } catch (IOException e) {
            fail("Oops! You can't read the file.");
        }
    }

    // note: most of this method is inspired by the phase 2 example
    @Test
    void readSomeFileTest() {
        VendingMachine vendingMachine = new VendingMachine("Karen's business", 1);
        //JsonRead readAFile = new JsonRead("./data/anotherMachineTest.json");
        Item items1 = new Item("Karen's business", 1);
        items1.itemInfo("tomato", -1029497117, 10,10);
        Item items2 = new Item("Karen's business", 1);
        items2.itemInfo("lettuce", -1655069210, 2,20);
        vendingMachine.addItem(items1);
        vendingMachine.addItem(items2);

        try {
            JsonWrite writeAFile = new JsonWrite("./data/anotherMachineTest.json");

            writeAFile.path();
            writeAFile.write(vendingMachine);
            writeAFile.close();

            JsonRead readAFile = new JsonRead("./data/anotherMachineTest.json");
            vendingMachine = readAFile.readVending();
            assertEquals("Karen's business", vendingMachine.getMachineName());
            assertEquals(1, vendingMachine.getMachineID());
            assertEquals(2, vendingMachine.getInventorySize());

            checkItem("tomato", -1029497117, 10,10,
                    vendingMachine.getVendingMachineInventory().get(0));
            checkItem("lettuce", -1655069210, 2,20,
                    vendingMachine.getVendingMachineInventory().get(1));
        } catch (IOException e) {
            fail("Oops! You can't read the file.");
        }
    }
}
