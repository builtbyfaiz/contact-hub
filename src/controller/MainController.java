package controller;

import java.util.List;

import model.Contact;
import view.AppGUI;

public class MainController {
    private AppGUI view;
    private List<Contact> contacts;
    
    public MainController(AppGUI view) {
        ContactController contactController = new ContactController(view, contacts);

    }
}
