package dao.impl;

import business.impl.CustomerServiceImpl;
import dao.ICustomerDAO;
import entity.Customer;
import util.DBUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements ICustomerDAO {

    @Override
    public boolean addCustomer(Customer customer) {
        CustomerServiceImpl customerService = new CustomerServiceImpl();
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            if(customerService.checkEmail(customer.getEmail())){
                System.err.println("Email đã tồn tại. Vui lòng nhập lại.");
                return false;
            }
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("call add_customer(?,?,?,?)");
            callSt.setString(1, customer.getName());
            callSt.setString(2, customer.getPhone());
            callSt.setString(3, customer.getEmail());
            callSt.setString(4, customer.getAddress());
            callSt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return false;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return false;
    }

    @Override
    public boolean deleteCustomer(int index) {
        return false;
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
                    customers.add(customer);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return customers;
    }
}
