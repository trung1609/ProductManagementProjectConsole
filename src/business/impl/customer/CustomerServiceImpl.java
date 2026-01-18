package business.impl.customer;

import business.interfaceService.ICustomerService;
import dao.impl.customer.CustomerDAOImpl;
import entity.Customer;
import util.DBUtil;
import util.ExceptionHandler;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.List;

public class CustomerServiceImpl implements ICustomerService {
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

    @Override
    public boolean createCustomer(Customer customer) {
        CustomerDAOImpl customerDAOImpl = new CustomerDAOImpl();
        return customerDAOImpl.addCustomer(customer);
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        CustomerDAOImpl customerDAOImpl = new CustomerDAOImpl();
        return customerDAOImpl.updateCustomer(customer);
    }

    @Override
    public boolean deleteCustomer(int index) {
        CustomerDAOImpl customerDAOImpl = new CustomerDAOImpl();
        return customerDAOImpl.deleteCustomer(index);
    }

    @Override
    public List<Customer> findAllCustomers() {
        CustomerDAOImpl customerDAOImpl = new CustomerDAOImpl();
        return customerDAOImpl.getAllCustomers();
    }
}
