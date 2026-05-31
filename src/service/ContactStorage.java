package service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.Contact;

import com.google.gson.GsonBuilder;
import java.io.FileWriter;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/** Reads and writes contacts to {@code contacts.json} file */
public class ContactStorage {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create(); // Json reader
    private static final String JSON_PATH = "resources/contacts.json";

    public List<Contact> loadContacts() {
        ArrayList<Contact> contacts = new ArrayList<>();

        try {
            FileReader reader = new FileReader(JSON_PATH);
            JsonArray contactArray = GSON.fromJson(reader, JsonArray.class);

            for (var contactElement : contactArray) {
                JsonObject contactObject = contactElement.getAsJsonObject();

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

    public void saveContact(Contact contact) {
        List<Contact> contacts = loadContacts();

        // If Contact Already Exists, This acts as Edit function
        boolean found = false;
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getUUID().equals(contact.getUUID())) {
                contacts.set(i, contact); // Edits the contact
                found = true;
                break;
            }
        }

        if (!found)
            contacts.add(contact); // Adds new contact

        saveAllContacts(contacts);
    }

    public void deleteContact(String uuid) {
        var contacts = loadContacts();

        for (var contact : contacts) {
            if (contact.getUUID().equals(uuid)) {
                contacts.remove(contact);
                break;
            }
        }

        saveAllContacts(contacts);
    }

    private void saveAllContacts(List<Contact> contacts) {
        try {
            FileWriter writer = new FileWriter(JSON_PATH);
            writer.write(GSON.toJson(contacts));
            writer.close();
        } catch (Exception e) {
            System.err.println("Failed to write to contacts.json: ");
            e.printStackTrace();
        }
    }
}
