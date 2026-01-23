package business.impl.product;

import business.interfaceService.IProductService;
import dao.impl.product.ProductDAOImpl;
import dao.interfaceDao.IProductDAO;
import entity.Product;

import java.util.List;

public class ProductServiceImpl implements IProductService {
    private final IProductDAO productDAO;

    public ProductServiceImpl() {
        this.productDAO = new ProductDAOImpl();
    }

    @Override
    public boolean checkProductName(String productName) {
        IProductDAO productDAO = new ProductDAOImpl();
        return productDAO.checkProductName(productName);
    }

    @Override
    public Product findProductById(int productId) {
        return productDAO.findProductById(productId);
    }

    @Override
    public List<Product> getALlProducts() {
        return productDAO.displayAllProducts();
    }

    @Override
    public List<Product> getProductInStock() {
        return productDAO.getProductInStock();
    }

    @Override
    public boolean addProduct(Product product) {
        return productDAO.addProduct(product);
    }

    @Override
    public boolean updateProduct(Product product) {
        return productDAO.updateProduct(product);
    }

    @Override
    public boolean deleteProduct(int index) {
        return productDAO.deleteProduct(index);
    }

    @Override
    public List<Product> searchProductByBrand(String brand) {
        return productDAO.searchProductByBrand(brand);
    }

    @Override
    public List<Product> searchProductByPrice(double price_from, double price_to) {
        return productDAO.searchProductByPrice(price_from, price_to);
    }

    @Override
    public List<Product> searchProductByStock(String product_name) {
        return productDAO.searchProductByStock(product_name);
    }
}
