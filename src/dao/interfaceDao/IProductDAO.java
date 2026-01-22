package dao.interfaceDao;

import entity.Product;

import java.util.List;

public interface IProductDAO {
    boolean addProduct(Product product);

    boolean updateProduct(Product product);

    boolean deleteProduct(int index);

    List<Product> displayAllProducts();

    List<Product> getProductInStock();

    boolean checkProductName(String productName);

    Product findProductById(int productId);

    List<Product> searchProductByBrand(String brand);

    List<Product> searchProductByPrice(double priceFrom, double priceTo);

    List<Product> searchProductByStock(String productName);
}
