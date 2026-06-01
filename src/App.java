import java.awt.Color;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;

import view.AppGUI;
import controller.MainController;

class App {
    public static void main(String[] args) {
    initLookAndFeel();

        // Start GUI on EDT
        SwingUtilities.invokeLater(() -> {
            AppGUI view = new AppGUI();
            new MainController(view);
        });
    }

    // Use FlatLaf library to set up default look and feel for application components.
    private static void initLookAndFeel() {
        
        // GUI helper library setup
        FlatDarkLaf.setup();
        UIManager.put("Component.arc", 12);
        UIManager.put("Button.arc", 12);
        UIManager.put("TextComponent.arc", 10);
        UIManager.put("Component.accentColor", new Color(31, 111, 235));
    }
}
