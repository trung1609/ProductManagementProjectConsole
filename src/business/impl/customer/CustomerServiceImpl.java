package business.impl.customer;

import business.interfaceService.ICustomerService;
import dao.impl.customer.CustomerDAOImpl;
import dao.interfaceDao.ICustomerDAO;
import entity.Customer;

import java.util.List;

public class CustomerServiceImpl implements ICustomerService {
    private final ICustomerDAO customerDAO;

    public CustomerServiceImpl() {
        this.customerDAO = new CustomerDAOImpl();
    }

    @Override
    public boolean checkEmail(String email) {
        return customerDAO.checkEmail(email);
    }

    @Override
    public boolean checkPhone(String phone) {
        return customerDAO.checkPhone(phone);
    }

    @Override
    public Customer findCustomerById(int id) {
        return customerDAO.findCustomerById(id);
    }

    @Override
    public boolean createCustomer(Customer customer) {
        return customerDAO.addCustomer(customer);
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return customerDAO.updateCustomer(customer);
    }

    @Override
    public boolean deleteCustomer(int index) {
        return customerDAO.deleteCustomer(index);
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerDAO.getAllCustomers();
    }
}
