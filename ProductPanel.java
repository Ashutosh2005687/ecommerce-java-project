package com.ecommerce.ui;

import com.ecommerce.dao.ProductDAO;
import com.ecommerce.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ProductPanel extends JPanel {
    private ProductDAO dao = new ProductDAO();
    private JTable table;
    private DefaultTableModel tableModel;

    public ProductPanel() {
        setLayout(new BorderLayout());
        tableModel = new DefaultTableModel(new Object[]{"ID","Name","Price","Stock"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel controls = new JPanel();
        JButton btnRefresh = new JButton("Refresh");
        JButton btnAdd = new JButton("Add Sample");
        JButton btnDelete = new JButton("Delete Selected");
        controls.add(btnRefresh);
        controls.add(btnAdd);
        controls.add(btnDelete);
        add(controls, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> loadProducts());

        btnAdd.addActionListener(e -> {
            Product p = new Product("New Item", "Demo product", 99.0, 10, null);
            try {
                dao.addProduct(p);
                JOptionPane.showMessageDialog(this, "Added product ID: " + p.getProductId());
                loadProducts();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(this, "Select a row"); return; }
            int id = (int) tableModel.getValueAt(row, 0);
            try {
                dao.deleteProduct(id);
                loadProducts();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        // initial load
        loadProducts();
    }

    private void loadProducts() {
        try {
            List<Product> list = dao.listAll();
            tableModel.setRowCount(0);
            for (Product p : list) {
                tableModel.addRow(new Object[]{p.getProductId(), p.getName(), p.getPrice(), p.getStock()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load products: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
