package business.interfaceService;

import entity.Product;

import java.util.List;

public interface IProductService {
    Product findProductById(int productId);

    List<Product> getALlProducts();

    boolean addProduct(Product product);

    boolean updateProduct(Product product);

    boolean deleteProduct(int index);

    List<Product> searchProductByBrand(String brand);

    List<Product> searchProductByPrice(double price_from, double price_to);

    List<Product> searchProductByStock(String product_name);
}
