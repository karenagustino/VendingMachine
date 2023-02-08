package persistence;

import model.Item;
import model.VendingMachine;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriteTest extends JsonTest {

    // note: most of this method is inspired by the phase 2 example
    @Test
    void writeNoFileTest(){
        VendingMachine vendingMachine = new VendingMachine("Karen's Business", 1);
        JsonWrite writeAFile = new JsonWrite("./data/my\0illegal:noSuchFile.json");

        try {
            writeAFile.path();
            //fail("No such file exists!");
        } catch (IOException e) {
            // pass because there is exactly no file to be written to
        }
    }

    // note: most of this method is inspired by the phase 2 example
    @Test
    void writeEmptyFileTest() {
        VendingMachine vendingMachine = new VendingMachine("Karen's Business", 1);
        JsonWrite writeAFile = new JsonWrite("./data/emptyMachineTest.json");

        try {
            writeAFile.path();
            writeAFile.write(vendingMachine);
            writeAFile.close();

            JsonRead readAFile = new JsonRead("./data/emptyMachineTest.json");
            vendingMachine = readAFile.readVending();
            assertEquals("Karen's Business", vendingMachine.getMachineName());
            assertEquals(1, vendingMachine.getMachineID());
            assertEquals(0, vendingMachine.getInventorySize());
        } catch (FileNotFoundException e) {
            fail("Oops! I found an error. There should've been a file.");
        } catch (IOException e) {
            fail("Oops! I found an error. File can't be read or write.");
        }
    }

    // note: most of this method is inspired by the phase 2 example
    @Test
    void writeSomeFileTest() {
        VendingMachine vendingMachine = new VendingMachine("Karen's business", 1);
        Item items1 = new Item("Karen's business", 1);
        items1.itemInfo("tomato", -1029497117, 10,10);
        Item items2 = new Item("Karen's business", 1);
        items2.itemInfo("lettuce", -1655069210, 2,20);
        vendingMachine.addItem(items1);
        vendingMachine.addItem(items2);

        JsonWrite writeAFile = new JsonWrite("./data/anotherMachineTest.json");
        
        try {
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

        } catch (FileNotFoundException e) {
            fail("Oops! I found an error. There should've been a file.");
        } catch (IOException e) {
            fail("Oops! I found an error. File can't be read or write.");
        }

    }
}
