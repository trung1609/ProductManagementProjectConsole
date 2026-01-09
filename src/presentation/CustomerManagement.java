package presentation;

import dao.impl.CustomerDAOImpl;
import entity.Customer;

import java.util.List;
import java.util.Scanner;

public class CustomerManagement {
    static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        do {
            try {
                System.out.println("============ QUẢN LÝ KHÁCH HÀNG ============");
                System.out.println("1. Hiển thị danh sách khách hàng");
                System.out.println("2. Thêm khách hàng mới");
                System.out.println("3. Cập nhật thông tin khách hàng");
                System.out.println("4. Xóa khách hàng theo ID");
                System.out.println("5. Quay lại menu chính");
                System.out.println("============================================");
                System.out.print("Lựa chọn của bạn: ");
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        getAllCustomers();
                        break;
                    case 2:
                        addCustomer();
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        MainMenu.main(args);
                        return;
                    default:
                        System.err.println("Vui lòng nhập lựa chọn phù hợp.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập lại lựa chọn.");
            }
        } while (true);
    }

    public static void addCustomer() {
        Scanner sc = new Scanner(System.in);
        Customer customer = new Customer();
        customer.input(sc);
        CustomerDAOImpl customerDAO = new CustomerDAOImpl();
        boolean result = customerDAO.addCustomer(customer);
        if (result) {
            System.out.println("Thêm khách hàng thành công.");
        } else {
            System.err.println("Có lỗi khi thêm khách hàng.");
        }
    }

    public static void getAllCustomers() {
        CustomerDAOImpl customerDAO = new CustomerDAOImpl();
        List<Customer> customerList = customerDAO.getAllCustomers();
        if (customerList.isEmpty()) {
            System.out.println("Chưa có khách hàng được thêm.");
        } else {
            customerList.forEach(System.out::println);
        }
    }
}
