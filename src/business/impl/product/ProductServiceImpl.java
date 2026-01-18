package business.impl.product;

import business.interfaceService.IProductService;
import dao.impl.product.ProductDAOImpl;
import entity.Product;
import util.DBUtil;
import util.ExceptionHandler;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements IProductService {
    public static boolean checkProductName(String productName) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("{? = call check_product_name(?)}");
            callSt.registerOutParameter(1, Types.BOOLEAN);
            callSt.setString(2, productName);
            callSt.execute();
            return callSt.getBoolean(1);
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi kiểm tra tên sản phẩm");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return false;
    }

    @Override
    public Product findProductById(int productId) {
        Connection conn = null;
        CallableStatement callSt = null;
        Product product = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("{call find_product_by_id(?)}");
            callSt.setInt(1, productId);
            boolean hasData = callSt.execute();
            if (hasData) {
                ResultSet rs = callSt.getResultSet();
                if (rs.next()) {
                    product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setBrand(rs.getString("brand"));
                    product.setPrice(rs.getDouble("price"));
                    product.setStock(rs.getInt("stock"));
                }
            }
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi tìm sản phẩm theo ID");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return product;
    }

    @Override
    public List<Product> getALlProducts() {
        ProductDAOImpl productDAO = new ProductDAOImpl();
        return productDAO.displayAllProducts();
    }

    @Override
    public boolean addProduct(Product product) {
        ProductDAOImpl productDAO = new ProductDAOImpl();
        return productDAO.addProduct(product);
    }

    @Override
    public boolean updateProduct(Product product) {
        ProductDAOImpl productDAO = new ProductDAOImpl();
        return productDAO.updateProduct(product);
    }

    @Override
    public boolean deleteProduct(int index) {
        ProductDAOImpl productDAO = new ProductDAOImpl();
        return productDAO.deleteProduct(index);
    }

    @Override
    public List<Product> searchProductByBrand(String brand) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Product> productList = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("{call search_product_by_brand(?)}");
            callSt.setString(1, brand);
            boolean hasData = callSt.execute();
            if (hasData) {
                ResultSet rs = callSt.getResultSet();
                productList = new ArrayList<>();
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setBrand(rs.getString("brand"));
                    product.setPrice(rs.getDouble("price"));
                    product.setStock(rs.getInt("stock"));
                    productList.add(product);
                }
            }
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi tìm kiếm sản phẩm theo thương hiệu");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return productList;
    }

    @Override
    public List<Product> searchProductByPrice(double price_from, double price_to) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Product> productList = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("{call search_product_by_price(?,?)}");
            callSt.setBigDecimal(1, BigDecimal.valueOf(price_from));
            callSt.setBigDecimal(2, BigDecimal.valueOf(price_to));
            boolean hasData = callSt.execute();
            if (hasData) {
                ResultSet rs = callSt.getResultSet();
                productList = new ArrayList<>();
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setBrand(rs.getString("brand"));
                    product.setPrice(rs.getDouble("price"));
                    product.setStock(rs.getInt("stock"));
                    productList.add(product);
                }
            }
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi tìm kiếm sản phẩm theo giá");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return productList;
    }

    @Override
    public List<Product> searchProductByStock(String product_name) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Product> productList = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("{call search_product_by_stock(?)}");
            callSt.setString(1, product_name);
            boolean hasData = callSt.execute();
            if (hasData) {
                ResultSet rs = callSt.getResultSet();
                productList = new ArrayList<>();
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setBrand(rs.getString("brand"));
                    product.setPrice(rs.getDouble("price"));
                    product.setStock(rs.getInt("stock"));
                    productList.add(product);
                }
            }
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi tìm kiếm sản phẩm theo tồn kho");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return productList;
    }
}
