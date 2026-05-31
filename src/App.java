import java.awt.Color;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;

import controller.MainController;
import view.AppGUI;

public static void main(String[] args) {
    initLookAndFeel();
    SwingUtilities.invokeLater(() -> {
        AppGUI view = new AppGUI();
        new MainController(view);
    });
}

/** Uses the FlatLaf library to set default look and feel for the application */
private static void initLookAndFeel() {
    FlatDarkLaf.setup();
    UIManager.put("Component.arc", 12);
    UIManager.put("Button.arc", 12);
    UIManager.put("TextComponent.arc", 10);
    UIManager.put("Component.accentColor", new Color(31, 111, 235));
}