import com.formdev.flatlaf.FlatDarkLaf;

import controller.ContactController;
import view.AppGUI;

class App {
    public static void main(String[] args) {
        FlatDarkLaf.setup(); // Automatic Setup

        AppGUI view = new AppGUI();
        new ContactController(view);
    }
}