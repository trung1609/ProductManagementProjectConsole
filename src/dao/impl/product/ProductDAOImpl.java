package dao.impl.product;

import dao.interfaceDao.IProductDAO;
import entity.Product;
import util.DBUtil;
import util.ExceptionHandler;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
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
}
