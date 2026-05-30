package view;

import model.Contact;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DetailPanel extends JPanel {

    // Colors
    private final Color bgMain        = new Color(13, 17, 23);
    private final Color bgCard        = new Color(22, 27, 34);

    private final Color textPrimary   = new Color(230, 237, 243);
    private final Color textSecondary = new Color(125, 133, 144);

    private final Color accentBlue    = new Color(31, 111, 235);
    private final Color accentRed     = new Color(180, 40, 40);
    private final Color accentGray    = new Color(33, 38, 45);

    private final Color borderColor   = new Color(48, 54, 61);

    // PFP + Name 
    private JLabel avatarLabel = new JLabel();
    private JLabel nameLabel   = new JLabel();
    private JLabel favLabel    = new JLabel();

    // Info rows
    private JLabel phoneLabel = new JLabel();
    private JLabel emailLabel = new JLabel();
    private JLabel ipLabel    = new JLabel();
    private JLabel notesLabel = new JLabel();
    
    // Info rows
    private JPanel infoLabelBox        = new JPanel();
    private JPanel infoFieldBox        = new JPanel();

    private JTextField nameField  = new JTextField();
    private JTextField phoneField = new JTextField();
    private JTextField emailField = new JTextField();
    private JTextField ipField    = new JTextField();
    private JTextField notesField = new JTextField();

    // Action buttons
    private JButton favButton    = new JButton("Fav");
    private JButton editButton   = new JButton("Edit");
    private JButton deleteButton = new JButton("Delete");

    // Panels
    private JPanel buttonRow;

    DetailPanel() {
        setLayout(new BorderLayout());
        setBackground(bgMain);

        setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));

        initAvatarBox();
        initInfoLabelBox();
        initInfoFieldBox();

        initButtonRow();
    }

    private void initAvatarBox() {
        JPanel avatarBox = new JPanel();
        avatarBox.setLayout(new BoxLayout(avatarBox, BoxLayout.Y_AXIS));
        avatarBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        avatarBox.setBackground(bgMain);

        avatarLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        avatarLabel.setPreferredSize(new Dimension(90, 90));

        nameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        nameLabel.setForeground(textPrimary);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        favLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        favLabel.setForeground(new Color(200,200,0));
        favLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        avatarBox.add(avatarLabel);
        avatarBox.add(Box.createVerticalStrut(12));
        avatarBox.add(nameLabel);
        avatarBox.add(Box.createVerticalStrut(4));
        avatarBox.add(favLabel);
        avatarBox.add(Box.createVerticalStrut(24));

        add(avatarBox, BorderLayout.NORTH);
    }

    private void initInfoLabelBox() {
        infoLabelBox.setLayout(new BoxLayout(infoLabelBox, BoxLayout.Y_AXIS));
        infoLabelBox.setBackground(bgMain);

        infoLabelBox.add(createInfoLabelRow("Phone", phoneLabel));
        infoLabelBox.add(Box.createVerticalStrut(1));
        infoLabelBox.add(createInfoLabelRow("Email", emailLabel));
        infoLabelBox.add(Box.createVerticalStrut(1));
        infoLabelBox.add(createInfoLabelRow("IP",    ipLabel));
        infoLabelBox.add(Box.createVerticalStrut(1));
        infoLabelBox.add(createInfoLabelRow("Notes", notesLabel));
        infoLabelBox.add(Box.createVerticalStrut(28));

        add(infoLabelBox, BorderLayout.CENTER);
    }

    private void initInfoFieldBox() {
        infoFieldBox.setLayout(new BoxLayout(infoFieldBox, BoxLayout.Y_AXIS));
        infoFieldBox.setBackground(bgMain);

        infoFieldBox.add(createInfoFieldRow("Name", nameField));
        infoFieldBox.add(Box.createVerticalStrut(1));
        infoFieldBox.add(createInfoFieldRow("Phone", phoneField));
        infoFieldBox.add(Box.createVerticalStrut(1));
        infoFieldBox.add(createInfoFieldRow("Email", emailField));
        infoFieldBox.add(Box.createVerticalStrut(1));
        infoFieldBox.add(createInfoFieldRow("IP", ipField));
        infoFieldBox.add(Box.createVerticalStrut(1));
        infoFieldBox.add(createInfoFieldRow("Notes", notesField));
    }

    private JPanel createInfoLabelRow(String fieldName, JLabel valueLabel) {
        JPanel row = new JPanel(new BorderLayout(16, 0));
        row.setBackground(bgCard);
        row.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, borderColor),
            BorderFactory.createEmptyBorder(10, 14, 10, 14)
        ));

        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));

        JLabel fieldLabel = new JLabel(fieldName+":-");
        fieldLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        fieldLabel.setForeground(textSecondary);
        fieldLabel.setPreferredSize(new Dimension(56, 22));

        valueLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        valueLabel.setForeground(textPrimary);

        row.add(fieldLabel, BorderLayout.WEST);
        row.add(valueLabel, BorderLayout.CENTER);

        return row;
    }

    private JPanel createInfoFieldRow(String fieldName, JTextField field) {
        JPanel row = new JPanel(new BorderLayout(16, 0));
        row.setBackground(bgCard);
        row.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, borderColor),
            BorderFactory.createEmptyBorder(10, 14, 10, 14)
        ));

        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));

        JLabel fieldLabel = new JLabel(fieldName);
        fieldLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
        fieldLabel.setForeground(textSecondary);
        fieldLabel.setPreferredSize(new Dimension(52, 20));

        styleEditField(field);

        row.add(fieldLabel, BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);

        return row;
    }

    private void initButtonRow() {
        buttonRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonRow.setBackground(bgMain);

        styleActionButton(favButton,    accentBlue, textPrimary);
        styleActionButton(editButton,   accentGray, textPrimary);
        styleActionButton(deleteButton, accentRed,  Color.WHITE);

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
        button.setForeground(fg);
        button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        button.setFocusPainted(false); // Remove tab highlight
        button.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public void showContact(Contact contact) {
        avatarLabel.setIcon(loadAvatar(contact.getAvatarPath(), 90));
        nameLabel.setText(contact.getName());
        phoneLabel.setText(contact.getPhone());
        emailLabel.setText(contact.getEmail().isEmpty() ? "—" : contact.getEmail());
        ipLabel.setText(contact.getIP().isEmpty()       ? "—" : contact.getIP());
        notesLabel.setText(contact.getNotes().isEmpty() ? "—" : contact.getNotes());
        favLabel.setText(contact.isFav()                ? "★ Favorite" : " ");

        revalidate();
        repaint();
    }

    public void enterEditMode(Contact contact) {

        nameField.setText(contact.getName());
        phoneField.setText(contact.getPhone());
        emailField.setText(contact.getEmail());
        ipField.setText(contact.getIP());
        notesField.setText(contact.getNotes());

        remove(infoLabelBox);
        add(infoFieldBox, BorderLayout.CENTER);

        nameLabel.setVisible(false); // As Name Label is part of Avatar Box

        editButton.setText("Save");

        revalidate();
        repaint();
    }

    public void exitEditMode(Contact contact) {

        contact.setName(nameField.getText().trim());
        contact.setPhone(phoneField.getText().trim());
        contact.setEmail(emailField.getText().trim());
        contact.setIP(ipField.getText().trim());
        contact.setNotes(notesField.getText().trim());

        remove(infoFieldBox);
        // initInfoLabelBox();
        add(infoLabelBox, BorderLayout.CENTER);

        nameLabel.setVisible(true);

        showContact(contact);

        editButton.setText("Edit");

        revalidate();
        repaint();
    }

    private void styleEditField(JTextField field) {
        field.setBackground(new Color(33, 38, 45));
        field.setForeground(new Color(230, 237, 243));
        field.setCaretColor(new Color(230, 237, 243));
        field.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(48, 54, 61), 1),
            BorderFactory.createEmptyBorder(2, 6, 2, 6)
        ));
    }

    public void clearPanel() {
        avatarLabel.setIcon(null);
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
    

    public JButton getFavButton()    { return favButton;    }
    public JButton getEditButton()   { return editButton;   }
    public JButton getDeleteButton() { return deleteButton; }
}
