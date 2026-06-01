package service;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import model.Contact;

/** Reads and writes contacts to {@code contacts.json} file */
public class ContactStorage {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create(); // Json reader
    private static final String JSON_PATH = "contacts.json";

    public List<Contact> loadContacts() {
        ArrayList<Contact> contacts = new ArrayList<>(); // Create empty array

        try {
            // Load the JSON array
            FileReader reader = new FileReader(JSON_PATH); 
            JsonArray contactArray = GSON.fromJson(reader, JsonArray.class);

            // Convert each element of the array into an object
            for (var contactElement : contactArray) {
                JsonObject contactObject = contactElement.getAsJsonObject();

                // Fill our empty java array using values from JSON objects
                contacts.add(new Contact(
                        contactObject.get("uuid").getAsString(),
                        contactObject.get("name").getAsString(),
                        contactObject.get("phone").getAsString(),
                        contactObject.get("email").getAsString(),
                        contactObject.get("notes").getAsString(),
                        contactObject.get("ip").getAsString(),
                        contactObject.get("avatarPath").getAsString(),
                        contactObject.get("isFav").getAsBoolean()));
            }
            reader.close();
        } catch (Exception e) {
            System.err.println("Failed to load contacts.json: ");
            e.printStackTrace();
        }
        return contacts;
    }

    private void saveContacts(List<Contact> contacts) {
        try {
            // Convert our array to JSON and write it to contacts.json
            FileWriter writer = new FileWriter(JSON_PATH);
            writer.write(GSON.toJson(contacts)); 
            writer.close();
        } catch (Exception e) {
            System.err.println("Failed to write to contacts.json: ");
            e.printStackTrace();
        }
    }
    
    public void saveContact(Contact contact) {
        List<Contact> contacts = loadContacts();
        // If Contact Already Exists, This acts as Edit function

        // Search for our contact, if found replace it with edited one.
        boolean found = false;
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getUUID().equals(contact.getUUID())) {
                contacts.set(i, contact); // Edits the contact
                found = true;
                break;
            }
        }

        // Otherwise add it as a new contact
        if (!found)
            contacts.add(contact);

        // Save contacts array from memory to contacts.json
        saveContacts(contacts);
    }

    public void deleteContact(String uuid) {
        var contacts = loadContacts();

        // Search for contact, if found, it is deleted
        for (var contact : contacts) {
            if (contact.getUUID().equals(uuid)) {
                contacts.remove(contact);
                break;
            }
        }

        // Save contacts array from memory to contacts.json
        saveContacts(contacts);
    }
}
