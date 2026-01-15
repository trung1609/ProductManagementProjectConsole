package dao.interfaceDao;

import entity.Customer;

import java.util.List;

public interface ICustomerDAO {
    boolean addCustomer(Customer customer);

    boolean updateCustomer(Customer customer);

    boolean deleteCustomer(int index);

    List<Customer> getAllCustomers();
}
