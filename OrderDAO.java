package com.ecommerce.dao;

import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;

import java.sql.*;

public class OrderDAO {
    public int createOrder(Order order) throws SQLException {
        String insertOrder = "INSERT INTO orders (user_id, total_amount, status) VALUES (?,?,?)";
        String insertItem = "INSERT INTO order_item (order_id, product_id, quantity, unit_price) VALUES (?,?,?,?)";
        try (Connection c = DBConnection.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement psOrder = c.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS)) {
                psOrder.setInt(1, order.getUserId());
                psOrder.setDouble(2, order.getTotalAmount());
                psOrder.setString(3, order.getStatus());
                psOrder.executeUpdate();
                try (ResultSet rs = psOrder.getGeneratedKeys()) {
                    if (rs.next()) {
                        int orderId = rs.getInt(1);
                        try (PreparedStatement psItem = c.prepareStatement(insertItem)) {
                            for (OrderItem item : order.getItems()) {
                                psItem.setInt(1, orderId);
                                psItem.setInt(2, item.getProductId());
                                psItem.setInt(3, item.getQuantity());
                                psItem.setDouble(4, item.getUnitPrice());
                                psItem.addBatch();
                            }
                            psItem.executeBatch();
                        }
                        c.commit();
                        return orderId;
                    } else {
                        c.rollback();
                        throw new SQLException("Failed to create order (no ID returned)");
                    }
                }
            } catch (SQLException ex) {
                c.rollback();
                throw ex;
            } finally {
                c.setAutoCommit(true);
            }
        }
    }
}
