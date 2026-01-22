package dao.impl.customer;

import dao.interfaceDao.ICustomerDAO;
import entity.Customer;
import exception.ExceptionHandler;
import util.DBUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements ICustomerDAO {

    @Override
    public boolean addCustomer(Customer customer) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("call add_customer(?,?,?,?)");
            callSt.setString(1, customer.getName());
            callSt.setString(2, customer.getPhone());
            callSt.setString(3, customer.getEmail());
            callSt.setString(4, customer.getAddress());
            callSt.execute();
            return true;
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi thêm khách hàng");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return false;
    }


    @Override
    public boolean updateCustomer(Customer customer) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("call update_customer(?,?,?,?,?)");
            callSt.setInt(1, customer.getId());
            callSt.setString(2, customer.getName());
            callSt.setString(3, customer.getPhone());
            callSt.setString(4, customer.getEmail());
            callSt.setString(5, customer.getAddress());
            callSt.execute();
            return true;
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi cập nhật khách hàng");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return false;
    }

    @Override
    public boolean deleteCustomer(int index) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("call delete_customer(?)");
            callSt.setInt(1, index);
            callSt.execute();
            return true;
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi xóa khách hàng");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return true;
    }

    @Override
    public List<Customer> getAllCustomers() {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Customer> customers = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("{call get_all_customer()}");
            boolean hasData = callSt.execute();
            if (hasData) {
                ResultSet rs = callSt.getResultSet();
                customers = new ArrayList<>();
                while (rs.next()) {
                    Customer customer = new Customer();
                    customer.setId(rs.getInt("id"));
                    customer.setName(rs.getString("name"));
                    customer.setPhone(rs.getString("phone"));
                    customer.setEmail(rs.getString("email"));
                    customer.setAddress(rs.getString("address"));
                    customer.setStatus(rs.getBoolean("status"));
                    customers.add(customer);
                }
            }
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi lấy danh sách khách hàng");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return customers;
    }

    @Override
    public boolean checkEmail(String email) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("call check_email_customer(?,?)");
            callSt.setString(1, email);
            callSt.registerOutParameter(2, Types.BOOLEAN);
            callSt.execute();
            return callSt.getBoolean(2);
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi kiểm tra email");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return false;
    }

    @Override
    public boolean checkPhone(String phone) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("call check_phone_customer(?,?)");
            callSt.setString(1, phone);
            callSt.registerOutParameter(2, Types.BOOLEAN);
            callSt.execute();
            return callSt.getBoolean(2);
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi kiểm tra số điện thoại");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return false;
    }

    @Override
    public Customer findCustomerById(int id) {
        Connection conn = null;
        CallableStatement callSt = null;
        Customer customer = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("{call find_customer_by_id(?)}");
            callSt.setInt(1, id);
            boolean hasData = callSt.execute();
            if (hasData) {
                ResultSet rs = callSt.getResultSet();
                if (rs.next()) {
                    customer = new Customer();
                    customer.setId(rs.getInt("id"));
                    customer.setName(rs.getString("name"));
                    customer.setEmail(rs.getString("email"));
                    customer.setPhone(rs.getString("phone"));
                    customer.setAddress(rs.getString("address"));
                }
            }
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi tìm khách hàng theo ID");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return customer;
    }
}
