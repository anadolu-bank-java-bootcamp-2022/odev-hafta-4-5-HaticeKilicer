package com.gokhantamkoc.javabootcamp.odevhafta45.repository;

import com.gokhantamkoc.javabootcamp.odevhafta45.model.Owner;
import com.gokhantamkoc.javabootcamp.odevhafta45.model.Product;
import com.gokhantamkoc.javabootcamp.odevhafta45.util.DatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductRepository {

    DatabaseConnection databaseConnection;

  
/*	public ProductRepository(DatabaseConnection databaseConnection2) {
		// TODO Auto-generated constructor stub
	}*/

	@Autowired
    public void setDatabaseConnection(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public List<Product> getAll() {
        // BU METHODU 1. GOREV ICIN DOLDURUNUZ
    	
        final String SQL = "SELECT id, name, description FROM product";
        List<Product> products = new ArrayList<>();
        try (PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(SQL)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                products.add(
                    new Product(
                        id,
                        name,
                        description
                    )
                );
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return products;

    }
        
    public Product get(long id) {
        // BU METHODU 1. GOREV ICIN DOLDURUNUZ
        final String SQL = "SELECT * FROM swapper.product where id = ? limit 1";
        try (PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new Product(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("description")
                );
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex.getMessage());
        }
         
    }
    
    public void save(Product product) throws RuntimeException {
        // BU METHODU 1. GOREV ICIN DOLDURUNUZ
    	
    	final String SQL = "INSERT INTO product(id, name, description) values(?, ?, ?)";
    	try (PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(SQL)) {
    		preparedStatement.setLong(1, product.getId());
    		preparedStatement.setString(2, product.getName());
    		preparedStatement.setString(3, product.getDescription());
    		int affectedRows = preparedStatement.executeUpdate();
    		if (affectedRows <= 0) {
    			throw new RuntimeException(String.format("Could not save product %s!", product.getName()));
    		}
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		throw new RuntimeException(ex.getMessage());
    	}

    }
    
    public void update(Product product) throws RuntimeException {
        // BU METHODU 1. GOREV ICIN DOLDURUNUZ
    	
        Product foundProduct = this.get(product.getId());
        if (foundProduct != null) {
            final String SQL = "UPDATE swapper.product set description = ?, name = ? where id = ?";
            try (PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(SQL)) {
                preparedStatement.setString(1, product.getName());
                preparedStatement.setString(2, product.getDescription());
                preparedStatement.setLong(3, product.getId());
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows <= 0) {
                    throw new RuntimeException(String.format("Could not update owner %s!", product.getName()));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex.getMessage());
            }
        }                
    }

    // BU METHODU SILMEYINIZ YOKSA TESTLER CALISMAZ
    public void delete(long id) throws RuntimeException {
        Product foundProduct = this.get(id);
        if (foundProduct != null) {
            final String SQL = "delete from swapper.product where id = ?";
            try (PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(SQL)) {
                preparedStatement.setLong(1, id);
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows <= 0) {
                    throw new RuntimeException(String.format("Could not delete product with id %d!", id));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex.getMessage());
            }
        }
    }
}
