package view;

import model.Contact;
import model.Theme;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/** The {@code ContactCard} class represents a single GUI element/card created from a specific contact */
public class ContactCard extends JPanel {
    private JLabel avatarLabel = new JLabel();
    private JPanel textBlock   = new JPanel();
    private JLabel nameLabel   = new JLabel();
    private JLabel numLabel    = new JLabel();

    private boolean selected = false;

    public ContactCard(Contact contact) {
        setLayout(new BorderLayout(12, 0));
        setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        setBackground(Theme.BG_CARD);

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(250, 60));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        setAlignmentX(Component.LEFT_ALIGNMENT);

        initAvatar(contact.getAvatarPath());
        initLabels(contact.getName(), contact.getPhone());

        textBlock.setLayout(new BoxLayout(textBlock, BoxLayout.Y_AXIS));
        textBlock.setOpaque(false);
        
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        numLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        textBlock.add(Box.createVerticalGlue());
        textBlock.add(nameLabel);
        textBlock.add(Box.createVerticalStrut(2));
        textBlock.add(numLabel);
        textBlock.add(Box.createVerticalGlue());

        add(avatarLabel, BorderLayout.WEST);
        add(textBlock, BorderLayout.CENTER);

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (!selected) setCardBackground(Theme.CARD_HOVER);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (!selected) setCardBackground(Theme.BG_CARD);
            }
        });
    }

    private void initAvatar(String avatarPath) {
        ImageIcon icon = loadAvatar(avatarPath, 50);
        avatarLabel.setIcon(icon);
        avatarLabel.setPreferredSize(new Dimension(50, 50));
    }

    private void initLabels(String name, String phone) {
        nameLabel.setText(name);
        nameLabel.setForeground(Theme.TEXT_PRIMARY);
        nameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));

        numLabel.setText(phone);
        numLabel.setForeground(Theme.TEXT_SECONDARY);
        numLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
    }

    // -- Utils --

    private ImageIcon loadAvatar(String path, int size) {
        try {
            BufferedImage img = ImageIO.read(new File(path));
            if (img != null) {
                Image scaled = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            }
        } catch (Exception ignored) {}

        try {
            BufferedImage img = ImageIO.read(new File("resources/avatars/default.png"));
            if (img != null) {
                Image scaled = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            }
        } catch (Exception ignored) {}

        return new ImageIcon();
    }

    /// Setters
    public void setSelected(boolean selected) {
        this.selected = selected;
        setCardBackground(selected ? Theme.CARD_SELECTED : Theme.BG_CARD);
    }

    private void setCardBackground(Color color) {
        setBackground(color);
        repaint();
    }
}