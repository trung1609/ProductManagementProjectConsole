package dao.impl.product;

import dao.interfaceDao.IProductDAO;
import entity.Product;
import exception.ExceptionHandler;
import util.DBUtil;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements IProductDAO {
    @Override
    public boolean addProduct(Product product) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("call add_product(?,?,?,?)");
            callSt.setString(1, product.getName());
            callSt.setString(2, product.getBrand());
            callSt.setBigDecimal(3, BigDecimal.valueOf(product.getPrice()));
            callSt.setInt(4, product.getStock());
            callSt.execute();
            return true;
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi thêm sản phẩm");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return false;
    }

    @Override
    public boolean updateProduct(Product product) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("call update_product(?,?,?,?,?)");
            callSt.setInt(1, product.getId());
            callSt.setString(2, product.getName());
            callSt.setString(3, product.getBrand());
            callSt.setBigDecimal(4, BigDecimal.valueOf(product.getPrice()));
            callSt.setInt(5, product.getStock());
            callSt.execute();
            return true;
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi cập nhật sản phẩm");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return false;
    }

    @Override
    public boolean deleteProduct(int index) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("call delete_product(?)");
            callSt.setInt(1, index);
            callSt.execute();
            return true;
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi xóa sản phẩm");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return false;
    }

    @Override
    public List<Product> displayAllProducts() {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Product> productList = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("{call get_all_product()}");
            boolean hasData = callSt.execute();
            if (hasData) {
                ResultSet rs = callSt.getResultSet();
                productList = new ArrayList<Product>();
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setBrand(rs.getString("brand"));
                    product.setPrice(rs.getDouble("price"));
                    product.setStock(rs.getInt("stock"));
                    product.setStatus(rs.getBoolean("status"));
                    productList.add(product);
                }
            }
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi hiển thị danh sách sản phẩm");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return productList;
    }

    @Override
    public List<Product> getProductInStock() {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Product> productList = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("{call get_product_inStock()}");
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
                    product.setStatus(rs.getBoolean("status"));
                    productList.add(product);
                }
            }
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi lấy sản phẩm trong kho");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return productList;
    }

    @Override
    public boolean checkProductName(String productName) {
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
    public List<Product> searchProductByPrice(double priceFrom, double priceTo) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Product> productList = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("{call search_product_by_price(?,?)}");
            callSt.setBigDecimal(1, BigDecimal.valueOf(priceFrom));
            callSt.setBigDecimal(2, BigDecimal.valueOf(priceTo));
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
    public List<Product> searchProductByStock(String productName) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Product> productList = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("{call search_product_by_stock(?)}");
            callSt.setString(1, productName);
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
