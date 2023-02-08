package persistence;

import model.*;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

// EFFECTS: writes user input as a JSON file
public class JsonWrite {
    private PrintWriter file;
    private String destination;

    // EFFECTS: constructs all inputs to be written to the destination file
    // Note: this method is inspired by the phase 2 sample given
    public JsonWrite(String destination) {
        this.destination = destination;
    }


    // MODIFIES: this
    // EFFECTS: formats the file to go to destination and throws an exception to check if file exists
    // Note: this method is inspired by the phase 2 sample given
    public void path() throws FileNotFoundException {
        file = new PrintWriter(destination);
    }

    // MODIFIES: this
    // EFFECTS: write the items in terms of JSON
    // Note: this method is inspired by the phase 2 sample given
    public void write(VendingMachine vendingMachine) {
        JSONObject json = vendingMachine.beJson();
        saveToFile(json.toString());
    }

    // MODIFIES: this
    // EFFECTS: closes the JSON file
    // Note: this method is inspired by the phase 2 sample given
    public void close() {
        file.close();
    }


    // MODIFIES: this
    // EFFECTS: saves the string of the vending machine to a file
    // Note: this method is inspired by the phase 2 sample given
    public void saveToFile(String vendingMachineString) {
        file.print(vendingMachineString);
    }
}
