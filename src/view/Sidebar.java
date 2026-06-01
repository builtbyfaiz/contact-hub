package view;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

import javax.swing.*;

import model.Theme;

/** Class for the Sidebar GUI of the application  */
public class Sidebar extends JPanel {

    private JTextField searchField = new JTextField();
    private JButton    allButton   = new JButton("All");
    private JButton    favButton   = new JButton("Favorites");

    private JPanel  contactsPanel    = new JPanel();
    private JButton newContactButton = new JButton("+ New Contact");

    // GUI representation of contacts as cards
    private ArrayList<ContactCard> contactCards = new ArrayList<>();

    // Constructor
    public Sidebar(int windowWidth, int windowHeight) {
        setupSidebar(windowWidth, windowHeight);
        initComponents();
    }

    // -- Sidebar Configuration --
    private void setupSidebar(int windowWidth, int windowHeight) {   
        int width = (windowWidth * 30) / 100; // ~30% of window     
        setLayout(new BorderLayout(0, 0));
        setPreferredSize(new Dimension(width, windowHeight));
        setBackground(Theme.BG_SIDEPANEL);
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Theme.BORDER_COLOR));
    }
    
    private void initComponents() {
        initHeaderPanel();
        initContactPane();
        initNewContactButton();
    }

    // -- Region Initialization --
    private void initHeaderPanel() {

        // Header Panel Config
        JPanel headerPanel = new JPanel(); 
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(Theme.BG_SIDEPANEL);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(16, 12, 12, 12));
        
        // Initiate Elements
        initSearchField();
        JPanel filterButtonsRow = createFilterButtonsRow();
        
        // Add Elements to Header Panel
        headerPanel.add(searchField);
        headerPanel.add(Box.createVerticalStrut(12)); 
        headerPanel.add(filterButtonsRow);                   

        add(headerPanel, BorderLayout.NORTH); // Add to sidebar
    }

    private void initContactPane() {
        contactsPanel.setLayout(new BoxLayout(contactsPanel, BoxLayout.Y_AXIS));
        contactsPanel.setBackground(Theme.BG_SIDEPANEL);

        // Display contact cards inside a scrollable container.
        JScrollPane scrollpane = new JScrollPane(contactsPanel);
        scrollpane.setBorder(BorderFactory.createEmptyBorder());
        scrollpane.setBackground(Theme.BG_SIDEPANEL);
        scrollpane.getViewport().setBackground(Theme.BG_SIDEPANEL);
        scrollpane.getVerticalScrollBar().setBackground(Theme.BG_SIDEPANEL);
        scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollpane, BorderLayout.CENTER);
    }
    
    private void initNewContactButton() {
        newContactButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14)); 
        newContactButton.setBackground(Theme.BG_SIDEPANEL);
        newContactButton.setForeground(Theme.ACCENT_BLUE);
        newContactButton.setFocusPainted(false);
        newContactButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        newContactButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BORDER_COLOR),
            BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));
        
        add(newContactButton, BorderLayout.SOUTH); // Add to sidebar
    } 

    // -- Component by Component Initialization --
    private void initSearchField() {
        searchField.setBackground(Theme.ACCENT_GRAY);   // Field Color
        searchField.setForeground(Theme.TEXT_PRIMARY);  // Text Color
        searchField.setCaretColor(Theme.TEXT_PRIMARY);  // Blinking Cursor Color

        searchField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14)); 
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER_COLOR, 2),
            BorderFactory.createEmptyBorder(7, 10, 7, 10)
        ));

        searchField.setPreferredSize(new Dimension(1000, 40));  
        searchField.setText         ("Search contacts...");   // Placeholder Text
        searchField.setForeground   (Theme.TEXT_SECONDARY);     // Placeholder Text Color

        // Add listeners to show placeholder text.
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Search contacts...")) {
                    searchField.setText("");
                    searchField.setForeground(Theme.TEXT_PRIMARY);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Search contacts...");
                    searchField.setForeground(Theme.TEXT_SECONDARY);
                }
            }
        });
    }
    
    private JPanel createFilterButtonsRow() {
        styleFilterButton(allButton,  true);  // All contact button is active button by default
        styleFilterButton(favButton,  false); // Fav filter is disabled at initialization

        // Storing buttons in container to center them in sidebar
        JPanel filterButtonsRow = new JPanel();     
        filterButtonsRow.setLayout(new BoxLayout(filterButtonsRow, BoxLayout.X_AXIS));
        filterButtonsRow.setBackground(Theme.BG_SIDEPANEL);
        filterButtonsRow.add(allButton);
        filterButtonsRow.add(favButton);
        return filterButtonsRow;
    }
    
    private void styleFilterButton(JButton button, boolean active) {
        button.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13)); 
        button.setFocusPainted(false); // Removes Tab highlighting
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(active ? Theme.ACCENT_BLUE : Theme.BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(8, 12, 4, 12)
        ));
        button.setBackground(active ? Theme.ACCENT_BLUE  : Theme.ACCENT_GRAY);
        button.setForeground(active ? Theme.TEXT_PRIMARY : Theme.TEXT_SECONDARY);
    }

    // -- Utils --
    public void addContactCard(ContactCard card) {
        contactCards.add(card);
        contactsPanel.add(card);
    }

    public void clearContactsPanel() {
        contactCards.clear();
        contactsPanel.removeAll();
        contactsPanel.revalidate();
        contactsPanel.repaint();
    }

    public void setFav(boolean active) {
        if(active) {
            styleFilterButton(favButton, true);   // Enable fav button
            styleFilterButton(allButton, false);  // Disable all button
        } else {
            styleFilterButton(allButton, true);   // Enable all button
            styleFilterButton(favButton, false);  // Disable fav button
        }
    }

    // -- Getters --
    public JTextField getSearchField()      { return searchField; }
    public JButton    getAllButton()        { return allButton; }
    public JButton    getFavButton()        { return favButton; }
    public JButton    getNewContactButton() { return newContactButton; }
}
