package view;

import model.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;

/** The Main GUI of the class */
public class AppGUI {

    private final int windowWidth  = 960;
    private final int windowHeight = 720;

    private JFrame frame = new JFrame("Contact-Hub");

    /// Left panel
    private JPanel     sidePanel   = new JPanel(new BorderLayout(0, 0));

    private JTextField searchField = new JTextField();
    private JButton    allButton   = new JButton("All");
    private JButton    favButton   = new JButton("Favorites");

    private JPanel     contactList      = new JPanel();
    private JButton    newContactButton = new JButton("+ New Contact");
    private JScrollPane scrollpane      = new JScrollPane();             // A scrollable view to the contact list

    // Right panel
    private DetailPanel detailPanel = new DetailPanel();

    // Contacts
    private ArrayList<ContactCard> contactCards = new ArrayList<>();

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
        frame.getContentPane().setBackground(Theme.BG_MAIN);
    }

    private void initLeftPanel() {
        int width = (windowWidth * 30) / 100; // ~30% of window

        sidePanel.setPreferredSize(new Dimension(width, windowHeight));
        sidePanel.setBackground(Theme.BG_SIDEPANEL);
        sidePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Theme.BORDER_COLOR));

        JPanel topBox = new JPanel();
        JPanel filterButtons = createFilterButtons(topBox);

        topBox.add(filterButtons);
        sidePanel.add(topBox, BorderLayout.NORTH);

        initContactList();
        initScrollPane();
        initNewContactButton();

        sidePanel.add(scrollpane, BorderLayout.CENTER);
        sidePanel.add(newContactButton, BorderLayout.SOUTH);
    }

    private JPanel createFilterButtons(JPanel topBox) {
        topBox.setLayout(new BoxLayout(topBox, BoxLayout.Y_AXIS));
        topBox.setBackground(Theme.BG_SIDEPANEL);
        topBox.setBorder(BorderFactory.createEmptyBorder(16, 12, 12, 12));

        initSearchField();
        initFilterButtons();

        topBox.add(searchField);
        topBox.add(Box.createVerticalStrut(12));

        JPanel filterButtonsRow = new JPanel();
        filterButtonsRow.setLayout(new BoxLayout(filterButtonsRow, BoxLayout.X_AXIS));
        filterButtonsRow.setBackground(Theme.BG_SIDEPANEL);
        filterButtonsRow.add(allButton);
        filterButtonsRow.add(favButton);
        return filterButtonsRow;
    }

    private void initSearchField() {
        searchField.setBackground(Theme.ACCENT_GRAY);   // Field Color
        searchField.setForeground(Theme.TEXT_PRIMARY);  // Text Color
        searchField.setCaretColor(Theme.TEXT_PRIMARY);  // Cursor Color

        searchField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14)); 
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER_COLOR, 2),
            BorderFactory.createEmptyBorder(7, 10, 7, 10)
        ));

        searchField.setPreferredSize(new Dimension(1000, 40));

        searchField.setText("Search contacts...");
        searchField.setForeground(Theme.TEXT_SECONDARY); // Placeholder Text Color

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

    private void initFilterButtons() {
        styleFilterButton(allButton,  true);  // "All Contacts" is active button by default
        styleFilterButton(favButton,  false);
    }

    private void initContactList() {
        contactList.setLayout(new BoxLayout(contactList, BoxLayout.Y_AXIS));
        contactList.setBackground(Theme.BG_SIDEPANEL);
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
    }

    private void initScrollPane() {
        scrollpane = new JScrollPane(contactList);
        scrollpane.setBorder(BorderFactory.createEmptyBorder());
        scrollpane.setBackground(Theme.BG_SIDEPANEL);
        scrollpane.getViewport().setBackground(Theme.BG_SIDEPANEL);
        scrollpane.getVerticalScrollBar().setBackground(Theme.BG_SIDEPANEL);
        scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
        contactCards.add(card);
        contactList.add(card);
    }

    public void clearContactCards() {
        contactCards.clear();
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
    public JButton           getNewContactButton() { return newContactButton; }
    public List<ContactCard> getContactCards()     { return contactCards; }
}
