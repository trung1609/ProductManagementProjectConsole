package business.interfaceService;

import entity.Customer;

public interface ICustomerService {
    boolean checkEmail(String email);

    Customer findCustomerById(int id);
}
