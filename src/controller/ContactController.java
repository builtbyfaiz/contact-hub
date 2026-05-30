package controller;

import model.Contact;
import service.ContactStorage;
import view.AppGUI;
import view.ContactCard;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JToggleButton;

public class ContactController {

    private AppGUI view;
    private ContactStorage storage;
    private List<Contact> contacts;

    private Contact selectedContact  = null;   // The actual data model
    private ContactCard selectedCard = null;   // The GUI representation of the model
    private boolean isFavFilterOn    = false;
    private boolean isEditMode       = false;

    public ContactController(AppGUI view) {
        this.view = view;
        this.storage = new ContactStorage();
        this.contacts = storage.loadContacts();

        updateContactList(contacts);
        bindEvents();
    }

    private void bindEvents() {
        bindSearch();
        bindFilterButtons();
        bindNewContact();
        bindContactActionButtons();
    }

    private void bindSearch() {
        view.getSearchField().addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String searchQuery = view.getSearchField().getText().trim().toLowerCase();

                if (searchQuery.equals("search contacts...") || searchQuery.isEmpty()) {
                    updateContactList(isFavFilterOn ? getFavContacts() : contacts);
                    return;
                }

                List<Contact> filteredList = new ArrayList<>();
                List<Contact> listToSearch = isFavFilterOn ? getFavContacts() : contacts;

                for (Contact c : listToSearch) {
                    if (c.getName ().toLowerCase().contains(searchQuery) ||
                        c.getPhone().toLowerCase().contains(searchQuery))
                        filteredList.add(c);
                }
                updateContactList(filteredList);
            }
        });
    }

    private void bindFilterButtons() {
        view.getAllButton().addActionListener(e -> {
            isFavFilterOn = false;
            view.toggleFavFilter(false); // Styles the buttons upon toggle
            updateContactList(contacts);
        });

        view.getFavButton().addActionListener(e -> {
            isFavFilterOn = true;
            view.toggleFavFilter(true); // Styles the buttons upon toggle
            updateContactList(getFavContacts());
        });
    }

    private void bindNewContact() {
        view.getNewContactButton().addActionListener(e -> {
            String uuid = UUID.randomUUID().toString();
            Contact newContact = new Contact(uuid, "New Contact", "", "", "", "", "resources/avatars/default.png", false);
            storage.saveContact(newContact);
            toggleEditMode();
            selectedContact = newContact;
            syncContactCardList();
            view.getDetailPanel().showContact(selectedContact);
            view.getDetailPanel().enterEditMode(newContact);
        });
    }

    private void syncContactCardList() {
        contacts = storage.loadContacts();
        if(isFavFilterOn)
            updateContactList(getFavContacts());
        else
            updateContactList(contacts);
    }

    private void bindContactActionButtons() {
        view.getDetailPanel().getFavButton().addActionListener(e -> {
            if (selectedContact == null) return;

            selectedContact.toggleFav();
            storage.saveContact(selectedContact);
            syncContactCardList();

            if(selectedContact != null)
                view.getDetailPanel().showContact(selectedContact); // Update GUI
        });

        view.getDetailPanel().getDeleteButton().addActionListener(e -> {
            if (selectedContact == null) return;
            String uuid = selectedContact.getUUID();
            storage.deleteContact(uuid);
            selectedContact = null;
            syncContactCardList();
        });      
        
        view.getDetailPanel().getEditButton().addActionListener(e -> {
            if (selectedContact == null) return;
        
            toggleEditMode();

            if(isEditMode) {
                view.getDetailPanel().enterEditMode(selectedContact);
            } else {
                view.getDetailPanel().exitEditMode(selectedContact);
                storage.saveContact(selectedContact);
                syncContactCardList();
                view.getDetailPanel().showContact(selectedContact);
            }
        });
    }
    
    private void toggleEditMode() {
        isEditMode = !isEditMode;
    }

    private void updateContactList(List<Contact> list) {
        view.clearContactCards();
        view.getDetailPanel().clearPanel();

        ContactCard cardToSelect = null;

        for (Contact contact : list) {
            ContactCard card = new ContactCard(contact);
            
            if (selectedContact != null && contact.getUUID().equals(selectedContact.getUUID())) {
                cardToSelect    = card;
                selectedContact = contact;
            }

            card.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (selectedCard != null)
                        selectedCard.toggleSelected(false);

                    selectedCard    = card;     
                    selectedContact = contact;  

                    card.toggleSelected(true);
                    view.getDetailPanel().showContact(contact);
                }
            });

            view.addContactCard(card);
        }

        // view.addContactCard((Component) javax.swing.Box.createVerticalGlue());
        view.refreshContactList();

        // Preserve Selection
        if (cardToSelect != null) {
            selectedCard = cardToSelect;
            selectedCard.toggleSelected(true);
            view.getDetailPanel().showContact(selectedContact);
        } else {
            // If selected card is notpresent in our lists, then remove references
            selectedCard    = null;
            selectedContact = null;
        }
    }

    private List<Contact> getFavContacts() {
        return contacts.stream()
                .filter(Contact::isFav)
                .collect(Collectors.toList());
    }
}
