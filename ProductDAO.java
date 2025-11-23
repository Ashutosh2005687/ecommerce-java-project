package com.ecommerce.dao;

import com.ecommerce.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    public void addProduct(Product p) throws SQLException {
        String sql = "INSERT INTO product (name,description,price,stock,category_id) VALUES (?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getDescription());
            ps.setDouble(3, p.getPrice());
            ps.setInt(4, p.getStock());
            if (p.getCategoryId() == null) ps.setNull(5, Types.INTEGER); else ps.setInt(5, p.getCategoryId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) p.setProductId(rs.getInt(1));
            }
        }
    }

    public List<Product> listAll() throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM product";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("description"));
                p.setPrice(rs.getDouble("price"));
                p.setStock(rs.getInt("stock"));
                Object obj = rs.getObject("category_id");
                p.setCategoryId(obj == null ? null : (Integer)obj);
                list.add(p);
            }
        }
        return list;
    }

    public Product findById(int id) throws SQLException {
        String sql = "SELECT * FROM product WHERE product_id = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Product p = new Product();
                    p.setProductId(rs.getInt("product_id"));
                    p.setName(rs.getString("name"));
                    p.setDescription(rs.getString("description"));
                    p.setPrice(rs.getDouble("price"));
                    p.setStock(rs.getInt("stock"));
                    Object obj = rs.getObject("category_id");
                    p.setCategoryId(obj == null ? null : (Integer)obj);
                    return p;
                }
            }
        }
        return null;
    }

    public void updateProduct(Product p) throws SQLException {
        String sql = "UPDATE product SET name=?, description=?, price=?, stock=?, category_id=? WHERE product_id=?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getDescription());
            ps.setDouble(3, p.getPrice());
            ps.setInt(4, p.getStock());
            if (p.getCategoryId() == null) ps.setNull(5, Types.INTEGER); else ps.setInt(5, p.getCategoryId());
            ps.setInt(6, p.getProductId());
            ps.executeUpdate();
        }
    }

    public void deleteProduct(int productId) throws SQLException {
        String sql = "DELETE FROM product WHERE product_id = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ps.executeUpdate();
        }
    }
}
