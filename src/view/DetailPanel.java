package view;

import model.Contact;
import model.Theme;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/** Panel containing contact details and contact action buttons. */
public class DetailPanel extends JPanel {

    // Avatar + Name 
    private JLabel avatar    = new JLabel();
    private JLabel nameLabel = new JLabel();
    private JLabel favLabel  = new JLabel();

    // Contact Information Labels
    private JLabel phoneLabel = new JLabel();
    private JLabel emailLabel = new JLabel();
    private JLabel notesLabel = new JLabel();
    private JLabel ipLabel    = new JLabel();
    
    // Contact Information Fields
    private JTextField nameField  = new JTextField();
    private JTextField phoneField = new JTextField();
    private JTextField emailField = new JTextField();
    private JTextField notesField = new JTextField();
    private JTextField ipField    = new JTextField();

    // Contact Information Wrapper/Containers
    private JPanel contactInfoLabels = new JPanel();
    private JPanel contactInfoFields = new JPanel();

    // Contact Action buttons
    private JButton favButton    = new JButton("Fav");
    private JButton editButton   = new JButton("Edit");
    private JButton deleteButton = new JButton("Delete");

    DetailPanel() {
        setLayout(new BorderLayout());
        setBackground(Theme.BG_MAIN);
        setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));

        initAvatarBox();
        createContactInfoLabels();
        createContactInfoFields();
        initActionButtons();
    }

    private void initAvatarBox() {
        JPanel contactHeader = new JPanel();
        contactHeader.setLayout(new BoxLayout(contactHeader, BoxLayout.Y_AXIS));
        contactHeader.setBackground(Theme.BG_MAIN);
        contactHeader.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Avatar
        avatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        avatar.setPreferredSize(new Dimension(100, 100));

        // Name
        nameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        nameLabel.setForeground(Theme.TEXT_PRIMARY);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Fav label
        favLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        favLabel.setForeground(Theme.ACCENT_YELLOW);
        favLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contactHeader.add(avatar);
        contactHeader.add(Box.createVerticalStrut(12));
        contactHeader.add(nameLabel);
        contactHeader.add(Box.createVerticalStrut(4));
        contactHeader.add(favLabel);
        contactHeader.add(Box.createVerticalStrut(24));

        add(contactHeader, BorderLayout.NORTH);
    }
    
    private void createContactInfoLabels() {

        contactInfoLabels.setLayout(new BoxLayout(contactInfoLabels, BoxLayout.Y_AXIS));
        contactInfoLabels.setBackground(Theme.BG_MAIN);

        var gap = Box.createVerticalStrut(1);
        
        contactInfoLabels.add(createLabelRow("Phone", phoneLabel));
        contactInfoLabels.add(gap);
        contactInfoLabels.add(createLabelRow("Email", emailLabel));
        contactInfoLabels.add(gap);
        contactInfoLabels.add(createLabelRow("IP",    ipLabel));
        contactInfoLabels.add(gap);
        contactInfoLabels.add(createLabelRow("Notes", notesLabel));
        contactInfoLabels.add(gap);

        add(contactInfoLabels, BorderLayout.CENTER);
    }

    private void createContactInfoFields() {
        contactInfoFields.setLayout(new BoxLayout(contactInfoFields, BoxLayout.Y_AXIS));
        contactInfoFields.setBackground(Theme.BG_MAIN);

        var gap = Box.createVerticalStrut(1);

        contactInfoFields.add(createFieldRow("Name", nameField));
        contactInfoFields.add(gap);
        contactInfoFields.add(createFieldRow("Phone", phoneField));
        contactInfoFields.add(gap);
        contactInfoFields.add(createFieldRow("Email", emailField));
        contactInfoFields.add(gap);
        contactInfoFields.add(createFieldRow("IP", ipField));
        contactInfoFields.add(gap);
        contactInfoFields.add(createFieldRow("Notes", notesField));
    }

    private JPanel createLabelRow(String fieldName, JLabel label) {
        JPanel row = new JPanel(new BorderLayout(16, 0));
        row.setBackground(Theme.BG_CARD);
        row.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, Theme.BORDER_COLOR),
            BorderFactory.createEmptyBorder(8, 14, 8, 14)
        ));

        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));

        JLabel fieldLabel = new JLabel(fieldName+":-");
        fieldLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        fieldLabel.setForeground(Theme.TEXT_SECONDARY);
        fieldLabel.setPreferredSize(new Dimension(56, 24));

        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        label.setForeground(Theme.TEXT_PRIMARY);

        row.add(fieldLabel, BorderLayout.WEST);
        row.add(label, BorderLayout.CENTER);

        return row;
    }

    private JPanel createFieldRow(String fieldName, JTextField field) {
        JPanel row = new JPanel(new BorderLayout(16, 0));
        row.setBackground(Theme.BG_CARD);
        row.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, Theme.BORDER_COLOR),
            BorderFactory.createEmptyBorder(8, 14, 8, 14)
        ));

        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));

        JLabel fieldLabel = new JLabel(fieldName + ":-");
        fieldLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        fieldLabel.setForeground(Theme.TEXT_SECONDARY);
        fieldLabel.setPreferredSize(new Dimension(56, 24));

        styleEditField(field);

        row.add(fieldLabel, BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        return row;
    }

    private void initActionButtons() {
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonRow.setBackground(Theme.BG_MAIN);

        styleActionButton(favButton,    Theme.ACCENT_BLUE, Theme.TEXT_PRIMARY);
        styleActionButton(editButton,   Theme.ACCENT_GRAY, Theme.TEXT_PRIMARY);
        styleActionButton(deleteButton, Theme.ACCENT_RED , Theme.TEXT_PRIMARY);

        favButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        editButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        deleteButton.setAlignmentX(Component.RIGHT_ALIGNMENT);

        buttonRow.add(favButton);
        buttonRow.add(editButton);
        buttonRow.add(deleteButton);

        add(buttonRow, BorderLayout.SOUTH);
    }

    private void styleActionButton(JButton button, Color bg, Color fg) {
        button.setBackground(bg);
        button.setPreferredSize(new Dimension(80,30));
        button.setForeground(fg);
        button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
    }

    public void showContact(Contact contact) {
        avatar.setIcon(loadAvatar(contact.getAvatarPath(), 100));
        avatar.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setText(contact.getName());
        phoneLabel.setText(contact.getPhone());
        emailLabel.setText(contact.getEmail().isEmpty() ? "—" : contact.getEmail());
        ipLabel.setText(contact.getIP().isEmpty()       ? "—" : contact.getIP());
        notesLabel.setText(contact.getNotes().isEmpty() ? "—" : contact.getNotes());
        favLabel.setText(contact.getFav()                ? "★ Favorite" : " ");
        
        revalidate();
        repaint();
    }

    public void enterEditMode(Contact contact) {
        nameField.setText(contact.getName());
        phoneField.setText(contact.getPhone());
        emailField.setText(contact.getEmail());
        ipField.setText(contact.getIP());
        notesField.setText(contact.getNotes());

        remove(contactInfoLabels);
        add(contactInfoFields, BorderLayout.CENTER);

        nameLabel.setVisible(false); // Hide label to allow editing via field
        editButton.setText("Save");
        avatar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        revalidate();
        repaint();
    }

    public void exitEditMode(Contact contact) {
        contact.setName(nameField.getText().trim());
        contact.setPhone(phoneField.getText().trim());
        contact.setEmail(emailField.getText().trim());
        contact.setIP(ipField.getText().trim());
        contact.setNotes(notesField.getText().trim());

        remove(contactInfoFields);
        add(contactInfoLabels, BorderLayout.CENTER);
        
        showContact(contact);
        nameLabel.setVisible(true);
        
        editButton.setText("Edit");
        avatar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        revalidate();
        repaint();
    }

    private void styleEditField(JTextField field) {
        field.setBackground(new Color(33, 38, 45));
        field.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
    }

    public void clearPanel() {
        avatar.setIcon(null);
        nameLabel.setText("");
        phoneLabel.setText("");
        emailLabel.setText("");
        ipLabel.setText("");
        notesLabel.setText("");
        favLabel.setText(" ");
        repaint();
    }
 
    private ImageIcon loadAvatar(String path, int size) {
        File file = new File(path);
        if (!file.exists()) {
            file = new File("resources/avatars/default.png");
        }
        try {
            BufferedImage img = ImageIO.read(file);
            if (img != null) {
                return new ImageIcon(img.getScaledInstance(size, size, Image.SCALE_SMOOTH));
            }
        } catch (IOException e) {}
        return new ImageIcon();
    }  

    public JLabel  getAvatar()       { return avatar;       }
    public JButton getFavButton()    { return favButton;    }
    public JButton getEditButton()   { return editButton;   }
    public JButton getDeleteButton() { return deleteButton; }
}
