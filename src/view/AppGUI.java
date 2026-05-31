package view;

import model.Contact;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;

public class AppGUI {

    private final int windowWidth  = 960;
    private final int windowHeight = 720;

    private JFrame frame = new JFrame("Contact-Hub");

    // Left panel
    private JPanel     sidePanel   = new JPanel(new BorderLayout(0, 0));
    private JTextField searchField = new JTextField();
    private JButton    allButton   = new JButton("All");
    private JButton    favButton   = new JButton("Favorites");

    private JScrollPane scrollpane = new JScrollPane(); // A scrollable view to the contact list
    private JPanel     contactList = new JPanel();
    private JButton    newContactButton  = new JButton("+ New Contact");

    // Right panel
    private DetailPanel detailPanel = new DetailPanel();

    // Contacts
    private ArrayList<ContactCard> cards = new ArrayList<>();

    // Colors
    private final Color bgMain        = new Color(13, 17, 23);
    private final Color bgSidepanel   = new Color(22, 27, 34);

    private final Color textPrimary   = new Color(230, 237, 243);
    private final Color textSecondary = new Color(125, 133, 144);

    private final Color accentBlue    = new Color(31, 111, 235);
    private final Color accentGray    = new Color(33, 38, 45);

    private final Color borderColor   = new Color(48, 54, 61);

    public AppGUI() {
        initWindow();
        initLeftPanel();

        frame.add(sidePanel,   BorderLayout.WEST);
        frame.add(detailPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void initWindow() {
        frame.setSize(windowWidth, windowHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(bgMain);
    }

    private void initLeftPanel() {
        int width = (windowWidth * 30) / 100; // ~30% of window

        sidePanel.setPreferredSize(new Dimension(width, windowHeight));
        sidePanel.setBackground(bgSidepanel);
        sidePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, borderColor));

        JPanel topBox = new JPanel();
        topBox.setLayout(new BoxLayout(topBox, BoxLayout.Y_AXIS));
        topBox.setBackground(bgSidepanel);
        topBox.setBorder(BorderFactory.createEmptyBorder(16, 12, 12, 12));

        initSearchField();
        initFilterButtons();

        topBox.add(searchField);
        topBox.add(Box.createVerticalStrut(12));

        JPanel filterButtonsRow = new JPanel();
        filterButtonsRow.setLayout(new BoxLayout(filterButtonsRow, BoxLayout.X_AXIS));
        filterButtonsRow.setBackground(bgSidepanel);
        filterButtonsRow.add(allButton);
        filterButtonsRow.add(favButton);

        // topBox.add(Box.createVerticalStrut(12));
        topBox.add(filterButtonsRow);
        sidePanel.add(topBox, BorderLayout.NORTH);

        initContactList();
        initScrollPane();
        initNewContactButton();

        sidePanel.add(scrollpane, BorderLayout.CENTER);
        sidePanel.add(newContactButton, BorderLayout.SOUTH);
    }

    private void initSearchField() {
        searchField.setBackground(accentGray);   // Field Color
        searchField.setForeground(textPrimary);  // Text Color
        searchField.setCaretColor(textPrimary);  // Cursor Color

        searchField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14)); 
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderColor, 2),
            BorderFactory.createEmptyBorder(7, 10, 7, 10)
        ));

        searchField.setPreferredSize(new Dimension(1000, 40));

        searchField.setText("Search contacts...");
        searchField.setForeground(textSecondary); // Placeholder Text Color

        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Search contacts...")) {
                    searchField.setText("");
                    searchField.setForeground(textPrimary);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Search contacts...");
                    searchField.setForeground(textSecondary);
                }
            }
        });
    }

    private void initFilterButtons() {
        styleFilterButton(allButton,  true);  // "All Contacts" is active button by default
        styleFilterButton(favButton,  false);
    }

    private void initContactList() {
        contactList.setLayout(new BoxLayout(contactList, BoxLayout.Y_AXIS));
        contactList.setBackground(bgSidepanel);
    }

    private void initNewContactButton() {
        newContactButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14)); 
        newContactButton.setBackground(bgSidepanel);
        newContactButton.setForeground(accentBlue);
        newContactButton.setFocusPainted(false);
        newContactButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        newContactButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, borderColor),
            BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));
    }

    private void initScrollPane() {
        scrollpane = new JScrollPane(contactList);
        scrollpane.setBorder(BorderFactory.createEmptyBorder());
        scrollpane.setBackground(bgSidepanel);
        scrollpane.getViewport().setBackground(bgSidepanel);
        scrollpane.getVerticalScrollBar().setBackground(bgSidepanel);
        scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }
    
    private void styleFilterButton(JButton button, boolean active) {
        button.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13)); 
        button.setFocusPainted(false); // Removes Tab highlighting
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(active ? accentBlue : borderColor, 1),
            BorderFactory.createEmptyBorder(8, 12, 4, 12)
        ));
        button.setBackground(active ? accentBlue  : accentGray);
        button.setForeground(active ? textPrimary : textSecondary);
    }

    // True = All Active, False = FavBtn Active
    public void toggleFavFilter(boolean toggle) {
        if(toggle == true) {
            styleFilterButton(favButton, true);
            styleFilterButton(allButton, false);
        }
        if(toggle == false) {
            styleFilterButton(favButton, false);
            styleFilterButton(allButton, true);
        }
    }

    // Utils
    public void addContactCard(ContactCard card) {
        cards.add(card);
        contactList.add(card);
    }

    public void clearContactCards() {
        cards.clear();
        contactList.removeAll();
        contactList.revalidate();
        contactList.repaint();
    }

    public void refreshContactList() {
        contactList.revalidate();
        contactList.repaint();
    }

    // Getters
    public JFrame            getWindow()           { return frame; }
    public JButton           getAllButton()        { return allButton; }
    public JButton           getFavButton()        { return favButton; }
    public JTextField        getSearchField()      { return searchField; }
    public DetailPanel       getDetailPanel()      { return detailPanel; }
    public JButton           getNewContactButton() { return newContactButton;}
    public List<ContactCard> getCards()            { return cards; }
}
