package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFileChooser;
import javax.swing.JLabel;

import model.Contact;
import view.AppGUI;
import view.ContactCard;
import service.ContactStorage;

/** Serves as the application backend and coordinates event handling. */
public class MainController {

    private AppGUI view;
    private ContactStorage storage;
    private List<Contact> contacts;

    private Contact selectedContact  = null;   // The data model
    private ContactCard selectedCard = null;   // The GUI representation of the model
    
    private boolean isFavFilterOn    = false;
    private boolean isEditMode       = false;

    public MainController(AppGUI view) {
        this.view     = view;
        this.storage  = new ContactStorage();
        this.contacts = storage.loadContacts();
        createContactCards(contacts);
        bindEvents();
    }

    private void bindEvents() {
        bindAvatar();
        bindSearch();
        bindNewContactButton();
        bindFilterButtons();
        bindContactActionButtons();
    }

    private void bindSearch() {
        view.getSearchField().addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String searchQuery = view.getSearchField().getText().trim().toLowerCase();

                if (searchQuery.equals("search contacts...") || searchQuery.isEmpty()) {
                    createContactCards(isFavFilterOn ? getFavContacts() : contacts);
                    return;
                }

                List<Contact> filteredList = new ArrayList<>();
                List<Contact> listToSearch = isFavFilterOn ? getFavContacts() : contacts;

                for (Contact c : listToSearch) {
                    if (c.getName().toLowerCase().contains(searchQuery) ||
                            c.getPhone().toLowerCase().contains(searchQuery))
                        filteredList.add(c);
                }
                createContactCards(filteredList);
            }
        });
    }

    private void bindFilterButtons() {
        view.getAllButton().addActionListener(e -> {
            isFavFilterOn = false;
            view.toggleFavFilter(false); // Styles the buttons upon toggle
            createContactCards(contacts);
        });

        view.getFavButton().addActionListener(e -> {
            isFavFilterOn = true;
            view.toggleFavFilter(true); // Styles the buttons upon toggle
            createContactCards(getFavContacts());
        });
    }

    private void bindNewContactButton() {
        view.getNewContactButton().addActionListener(e -> {
            String uuid = UUID.randomUUID().toString();
            Contact newContact = new Contact(uuid, "New Contact", "", "", "", "", "resources/avatars/default.png",
                    false);
            storage.saveContact(newContact);
            selectedContact = newContact;
            loadContactCards();
            view.getDetailPanel().showContact(newContact);
            toggleEditMode(true);
        });
    }

    private void loadContactCards() {
        contacts = storage.loadContacts();
        if (isFavFilterOn)
            createContactCards(getFavContacts());
        else
            createContactCards(contacts);
    }

    private void bindContactActionButtons() {
        view.getDetailPanel().getFavButton().addActionListener(e -> {
            if (selectedContact == null)
                return;

            selectedContact.setFav(!selectedContact.getFav());
            storage.saveContact(selectedContact);
            loadContactCards();

            if (selectedContact != null)
                view.getDetailPanel().showContact(selectedContact); // Update GUI
        });

        view.getDetailPanel().getDeleteButton().addActionListener(e -> {
            if (selectedContact == null)
                return;
            if(isEditMode)
                toggleEditMode(false);

            String uuid = selectedContact.getUUID();
            storage.deleteContact(uuid);
            selectedContact = null;
            loadContactCards();
        });

        view.getDetailPanel().getEditButton().addActionListener(e -> {
            if (selectedContact == null)
                return;

            isEditMode = !isEditMode;
            toggleEditMode(isEditMode);
        });
    }
    
    private void bindAvatar() {
        JLabel avatar = view.getDetailPanel().getAvatar();
        avatar.addMouseListener(new MouseAdapter() {
        // avatar.setCursor(new Cursor(Cursor.HAND_CURSOR));
            @Override
            public void mouseClicked(MouseEvent e) {
                if(isEditMode) {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setDialogTitle("Select Avatar");

                    int result = chooser.showOpenDialog(null);

                    if (result == JFileChooser.APPROVE_OPTION) {
                        File file = chooser.getSelectedFile();
                        var avatarPath = file.getAbsolutePath();
                        selectedContact.setAvatarPath(avatarPath);
                        storage.saveContact(selectedContact);
                        loadContactCards();
                    }
                }
            }
        });
    }

    private void toggleEditMode(boolean state) {
        isEditMode = state;
        if (isEditMode) {
            view.getDetailPanel().enterEditMode(selectedContact);
        } else {
            view.getDetailPanel().exitEditMode(selectedContact);
            storage.saveContact(selectedContact);
            loadContactCards();
            view.getDetailPanel().showContact(selectedContact);
        }
    }

    private void createContactCards(List<Contact> list) {
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
                    if(isEditMode)
                        toggleEditMode(false);

                    if (selectedCard != null)
                        selectedCard.setSelected(false);

                    selectedCard    = card;
                    selectedContact = contact;

                    card.setSelected(true);
                    view.getDetailPanel().showContact(contact);
                }
            });

            view.addContactCard(card);
        }
        view.refreshContactList();

        // Preserve Selection after refresh
        if (cardToSelect != null) {
            selectedCard = cardToSelect;
            selectedCard.setSelected(true);
            view.getDetailPanel().showContact(selectedContact);
        } else {
            // If selected card is notpresent in our lists, then remove references
            selectedCard    = null;
            selectedContact = null;
        }
    }

    private List<Contact> getFavContacts() {
        List<Contact> favContacts = new ArrayList<>();

        for (var contact : contacts) {
            if (contact.getFav())
                favContacts.add(contact);
        }

        return favContacts;
    }
}
