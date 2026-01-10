package dao.interfaceDAO;

import entity.Product;

import java.util.List;

public interface IProductDAO {
    boolean addProduct(Product product);

    boolean updateProduct(Product product);

    boolean deleteProduct(int index);

    List<Product> displayAllProducts();


}
