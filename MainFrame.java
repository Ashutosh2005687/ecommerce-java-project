package com.ecommerce.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("E-Commerce - Admin Console (Review-1)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,600);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Products", new ProductPanel());
        // future tabs: Users, Orders

        add(tabs, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        // setLookAndFeel optional
        SwingUtilities.invokeLater(() -> {
            MainFrame mf = new MainFrame();
            mf.setVisible(true);
        });
    }
}
