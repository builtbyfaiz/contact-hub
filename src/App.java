import com.formdev.flatlaf.FlatDarkLaf;

import controller.MainController;
import view.AppGUI;

class App {
    public static void main(String[] args) {
        FlatDarkLaf.setup(); // Automatic Setup

        AppGUI view = new AppGUI();
        new MainController(view);
    }
}