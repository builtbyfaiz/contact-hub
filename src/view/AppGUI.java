package view;

import java.awt.*;
import javax.swing.*;

import model.Theme;

/** The Main GUI class containing sidebar and detailPanel */
public class AppGUI {

    private final int WINDOW_WIDTH  = 960;
    private final int WINDOW_HEIGHT = 720;

    private JFrame frame = new JFrame("Contact-Hub");

    // Left and Right panels
    private Sidebar     sidebar     = new Sidebar(WINDOW_WIDTH, WINDOW_HEIGHT);
    private DetailPanel detailPanel = new DetailPanel();

    public AppGUI() {
        initWindow();

        frame.add(sidebar,     BorderLayout.WEST);
        frame.add(detailPanel, BorderLayout.CENTER);    
    }

    private void initWindow() {
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Theme.BG_MAIN);
        frame.setVisible(true);
    }

    // Getters
    public JFrame      getWindow()      { return frame;       }
    public Sidebar     getSidebar()     { return sidebar;     }
    public DetailPanel getDetailPanel() { return detailPanel; }
}
