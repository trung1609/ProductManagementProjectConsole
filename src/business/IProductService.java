package business;

import entity.Product;

import java.util.List;

public interface IProductService {
    Product findProductById(int productId);
    List<Product> searchProductByBrand(String brand);

    List<Product> searchProductByPrice(double price_from, double price_to);

    List<Product> searchProductByStock(int stock);
}
