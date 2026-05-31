import java.awt.Color;

import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;

import controller.MainController;
import view.AppGUI;

class App {
    public static void main(String[] args) {
        FlatDarkLaf.setup();

        UIManager.put("Component.arc", 12);
        UIManager.put("Button.arc", 12);
        UIManager.put("TextComponent.arc", 10);

        UIManager.put("Component.accentColor", new Color(31, 111, 235));
        
        AppGUI view = new AppGUI();
        new MainController(view);
    }
}