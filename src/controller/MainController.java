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
import service.ContactStorage;
import view.AppGUI;
import view.ContactCard;
import view.DetailPanel;
import view.Sidebar;

/** Serves as the application backend and coordinates event handling. */
public class MainController {

    private Sidebar sidebar;
    private DetailPanel detailsPanel;

    private ContactStorage storage;
    private List<Contact> contacts;

    private Contact selectedContact  = null;   // The data model
    private ContactCard selectedCard = null;   // The GUI representation of the model
    
    private boolean isEditMode        = false;
    private boolean isFavFilterActive = false;

    // Constructor
    public MainController(AppGUI view) {
        this.sidebar      = view.getSidebar();
        this.detailsPanel = view.getDetailPanel();
        this.storage      = new ContactStorage();
        this.contacts     = storage.loadContacts();

        refreshContactCards(contacts);
        bindEvents();
    }

    // -- Bind Events --
    private void bindEvents() {
        bindSidebar();
        bindDetailsPanel();
    }

    private void bindSidebar() {
        bindSearch();
        bindFilterButtons();
        bindNewContactButton();
    }

    private void bindDetailsPanel() {
        bindAvatar();
        bindContactActionButtons();
    }

    // Bind sidebar components
    private void bindSearch() {
        // On each key press, run a query to get a filtered list and update gui
        sidebar.getSearchField().addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String searchQuery = sidebar.getSearchField().getText().trim().toLowerCase();

                if (searchQuery.equals("search contacts...") || searchQuery.isEmpty()) {
                    refreshContactCards(isFavFilterActive ? getFavContacts() : contacts);
                    return;
                }

                List<Contact> filteredList = new ArrayList<>();
                List<Contact> listToSearch = isFavFilterActive ? getFavContacts() : contacts;

                for (Contact c : listToSearch) {
                    if (c.getName().toLowerCase().contains(searchQuery) ||
                            c.getPhone().toLowerCase().contains(searchQuery))
                        filteredList.add(c);
                }
                refreshContactCards(filteredList);
            }
        });
    }
     
    private void bindFilterButtons() {
        sidebar.getAllButton().addActionListener(e -> {
            isFavFilterActive = false;
            sidebar.setFav(false); // Enables Fav button, Disables All button
            refreshContactCards(contacts);
        });

        sidebar.getFavButton().addActionListener(e -> {
            isFavFilterActive = true;
            sidebar.setFav(true); // Enables Fav button, Disables All button
            refreshContactCards(getFavContacts());
        });
    }

    private void bindNewContactButton() {
        sidebar.getNewContactButton().addActionListener(e -> {
            String  uuid       = UUID.randomUUID().toString();
            Contact newContact = new Contact(uuid,
                                        "New Contact",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "resources/avatars/default.png",
                                        false);

            selectedContact = newContact;           // Select new contact
            storage.saveContact(selectedContact);   // Save it to storage
            loadContactCards();                     // Load newly created contacts.json, and update GUI
            detailsPanel.showContact(newContact);   // Enter Edit mode
            toggleEditMode(true);
        });
    }

    // Bind detailsPanel components
    private void bindAvatar() {
        JLabel avatar = detailsPanel.getAvatar();

        // Enable file choosing when in edit mode.
        avatar.addMouseListener(new MouseAdapter() {
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

    private void bindContactActionButtons() {
        detailsPanel.getFavButton().addActionListener(e -> {
            if (selectedContact == null)
                return;

            selectedContact.setFav(!selectedContact.getFav());
            storage.saveContact(selectedContact);
            loadContactCards();

            if (selectedContact != null)
                detailsPanel.showContact(selectedContact); // Update GUI
        });

        detailsPanel.getDeleteButton().addActionListener(e -> {
            if (selectedContact == null)
                return;
            
            if(isEditMode){
                toggleEditMode(false);
                detailsPanel.exitEditMode(selectedContact);
            }

            String uuid = selectedContact.getUUID();
            storage.deleteContact(uuid);
            selectedContact = null;
            loadContactCards();
        });

        detailsPanel.getEditButton().addActionListener(e -> {
            if (selectedContact == null)
                return;

            isEditMode = !isEditMode;
            toggleEditMode(isEditMode);
        });
    }

    // -- Utils --
    private void refreshContactCards(List<Contact> list) {
        sidebar.clearContactsPanel();
        detailsPanel.clearPanel();

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
                    detailsPanel.showContact(contact);
                }
            });

            sidebar.addContactCard(card);
        }
        sidebar.revalidate();
        sidebar.repaint();

        // Preserve Selection after refresh
        if (cardToSelect != null) {
            selectedCard = cardToSelect;
            selectedCard.setSelected(true);
            detailsPanel.showContact(selectedContact);
        } else {
            // If selected card is not present in our lists, then null it
            selectedCard    = null;
            selectedContact = null;
        }
    }

    private void loadContactCards() {
        contacts = storage.loadContacts();
        if (isFavFilterActive)
            refreshContactCards(getFavContacts());
        else
            refreshContactCards(contacts);
    }
    
    private void toggleEditMode(boolean state) {
        isEditMode = state;
        if (isEditMode) {
            detailsPanel.enterEditMode(selectedContact);
        } else {
            boolean fieldsValid = detailsPanel.validateFields();

            if(!fieldsValid) {
                isEditMode = true;
                return;
            }
            
            detailsPanel.exitEditMode(selectedContact);
            storage.saveContact(selectedContact);
            loadContactCards();
            detailsPanel.showContact(selectedContact);
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
