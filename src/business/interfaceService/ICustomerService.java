package business.interfaceService;

import entity.Customer;

import java.util.List;

public interface ICustomerService {
    boolean checkEmail(String email);

    boolean checkPhone(String phone);

    Customer findCustomerById(int id);

    boolean createCustomer(Customer customer);

    boolean updateCustomer(Customer customer);

    boolean deleteCustomer(int index);

    List<Customer> findAllCustomers();
}
