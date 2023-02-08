package persistence;

import org.json.JSONArray;
import org.json.JSONObject;

// an interface that allows classes to be written as Json type
public interface Writable {
    // EFFECTS: returns the file as a Json
    JSONObject beJson();


}
