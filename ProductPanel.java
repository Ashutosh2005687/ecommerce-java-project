package com.ecommerce.ui;

import com.ecommerce.dao.ProductDAO;
import com.ecommerce.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ProductPanel extends JPanel {

    private ProductDAO dao = new ProductDAO();

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtName, txtPrice, txtStock;

    public ProductPanel() {
        setLayout(new BorderLayout());

        // ================= TABLE =================
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Name", "Price", "Stock"}, 0
        ) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ================= INPUT PANEL =================
        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 8, 8));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Product"));

        inputPanel.add(new JLabel("Product Name"));
        txtName = new JTextField();
        inputPanel.add(txtName);

        inputPanel.add(new JLabel("Price"));
        txtPrice = new JTextField();
        inputPanel.add(txtPrice);

        inputPanel.add(new JLabel("Stock"));
        txtStock = new JTextField();
        inputPanel.add(txtStock);

        JButton btnAdd = new JButton("Add Product");
        JButton btnRefresh = new JButton("Refresh");
        JButton btnDelete = new JButton("Delete Selected");

        inputPanel.add(btnAdd);
        inputPanel.add(btnRefresh);
        inputPanel.add(btnDelete);

        add(inputPanel, BorderLayout.SOUTH);

        // ================= EVENTS =================

        btnAdd.addActionListener(e -> addProduct());
        btnRefresh.addActionListener(e -> loadProducts());
        btnDelete.addActionListener(e -> deleteSelectedProduct());

        // ================= INITIAL LOAD =================
        loadProducts();
    }

    // ================= ADD PRODUCT =================
    private void addProduct() {

        // ---- Validation ----
        if (txtName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Product name is required");
            return;
        }

        if (txtPrice.getText().trim().isEmpty() || txtStock.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Price and Stock are required");
            return;
        }

        double price;
        int stock;

        try {
            price = Double.parseDouble(txtPrice.getText());
            stock = Integer.parseInt(txtStock.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter valid numeric values");
            return;
        }

        Product product = new Product(
                txtName.getText(),
                "Added via GUI",
                price,
                stock,
                null
        );

        try {
            dao.addProduct(product);
            JOptionPane.showMessageDialog(this, "Product added successfully");
            clearFields();
            loadProducts();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error occurred");
            e.printStackTrace();
        }
    }

    // ================= DELETE PRODUCT =================
    private void deleteSelectedProduct() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);

        try {
            dao.deleteProduct(id);
            JOptionPane.showMessageDialog(this, "Product deleted successfully");
            loadProducts();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to delete product");
            e.printStackTrace();
        }
    }

    // ================= LOAD PRODUCTS =================
    private void loadProducts() {
        try {
            List<Product> products = dao.listAll();
            tableModel.setRowCount(0);

            for (Product p : products) {
                tableModel.addRow(new Object[]{
                        p.getProductId(),
                        p.getName(),
                        p.getPrice(),
                        p.getStock()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load products");
            e.printStackTrace();
        }
    }

    // ================= CLEAR INPUT =================
    private void clearFields() {
        txtName.setText("");
        txtPrice.setText("");
        txtStock.setText("");
    }
}

