package org.example.ecommercestore.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.ecommercestore.model.Cart;
import org.example.ecommercestore.model.products;

public class productDao {
    private Connection connection;

    public productDao(Connection connection) {
        this.connection = connection;
    }

    public List<products> getAllProducts() {
        List<products> productList = new ArrayList<>();
        String query = "SELECT * FROM \"productIts\""; // Ensure proper quoting
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                products product = new products();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setProductName(resultSet.getString("productName"));
                product.setPrice(resultSet.getDouble("price"));
                product.setImage(resultSet.getString("image"));
                productList.add(product);
            }
            System.out.println("Products retrieved successfully: " + productList.size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }

    public List<Cart> getCartProducts(ArrayList<Cart> cartList) throws SQLException {
        List<Cart> productList = new ArrayList<>();

        if(cartList != null && !cartList.isEmpty()) {
            String query = "SELECT * FROM \"productIts\" WHERE id = ?";
            try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)){
                for(Cart item : cartList) {
                    preparedStatement.setInt(1, item.getId());
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                            Cart row = new Cart();
                            row.setId(resultSet.getInt("id"));
                            row.setName(resultSet.getString("name"));
                            row.setProductName(resultSet.getString("productName"));
                            row.setPrice(resultSet.getDouble("price") * item.getQuantity());
                            row.setQuantity(item.getQuantity());
                            productList.add(row);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return productList;
    }

    public products getProductById(int productId) {
        products product = null;
        String query = "SELECT * FROM \"productIts\" WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, productId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    product = new products();
                    product.setId(resultSet.getInt("id"));
                    product.setName(resultSet.getString("name"));
                    product.setProductName(resultSet.getString("productName"));
                    product.setPrice(resultSet.getDouble("price"));
                    product.setQuantity(resultSet.getInt("quantity")); // Assuming quantity is available
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

}