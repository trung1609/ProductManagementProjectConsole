package presentation;

import business.impl.customer.CustomerServiceImpl;
import dao.impl.customer.CustomerDAOImpl;
import entity.Customer;
import presentation.menuUtil.MenuUtil;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CustomerManagement {
    static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] customerMenu = {
                "Hiển thị danh sách khách hàng",
                "Thêm khách hàng mới",
                "Cập nhật thông tin khách hàng",
                "Xóa khách hàng theo ID",
                "Quay lại menu chính"
        };

        do {
            try {
                MenuUtil.printMenu("QUẢN LÝ KHÁCH HÀNG", customerMenu);

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        getAllCustomers();
                        break;
                    case 2:
                        addCustomer();
                        break;
                    case 3:
                        updateCustomer(sc);
                        break;
                    case 4:
                        deleteCustomer(sc);
                        break;
                    case 5:
                        MainMenu.main(args);
                        return;
                    default:
                        System.err.println("Vui lòng nhập lựa chọn phù hợp.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số.");
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
            Customer customer = new Customer();
            customer.printCustomerHeader();
            for (Customer c : customerList) {
                c.printCustomerRow(c);
            }
            customer.printCustomerFooter();
            System.out.println("Tổng số khách hàng: " + customerList.size());
        }
    }

    public static void updateCustomer(Scanner sc) {
        CustomerServiceImpl customerService = new CustomerServiceImpl();
        CustomerDAOImpl customerDAO = new CustomerDAOImpl();
        System.out.print("Nhập mã khách hàng cần cập nhật: ");
        int id = Integer.parseInt(sc.nextLine());
        Customer customer = customerService.findCustomerById(id);
        if (customer == null) {
            System.err.println("Không tìm thấy mã khách hàng");
            return;
        }
        boolean isExist = true;
        do {
            System.out.println("1. Cập nhật tên khách hàng");
            System.out.println("2. Cập nhật số điện thoại khách hàng");
            System.out.println("3. Cập nhật email khách hàng");
            System.out.println("4. Cập nhật địa chỉ khách hàng");
            System.out.println("5. Thoát");
            System.out.print("Nhập lựa chọn: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    do {
                        String regex = "[\\p{L}]+";
                        System.out.print("Nhập tên mới của khách hàng: ");
                        String newName = sc.nextLine().trim();
                        if (!Pattern.matches(regex, newName)) {
                            System.err.println("Vui lòng nhập lại tên khách hàng chỉ chứa chữ cái.");
                            continue;
                        }
                        customer.setName(newName);
                        break;
                    } while (true);
                    break;
                case 2:
                    do {
                        String regex = "0[3|5789][0-9]{8}";
                        System.out.print("Nhập số điện thoại mới của khách hàng: ");
                        String newPhone = sc.nextLine();
                        if (!Pattern.matches(regex, newPhone)) {
                            System.err.println("Vui lòng nhập lại số điện thoại đúng định dạng");
                            continue;
                        }
                        customer.setPhone(newPhone);
                        break;
                    } while (true);
                    break;
                case 3:
                    do {
                        String regex = "[a-zA-Z0-9._]+@[\\w]+\\.[a-zA-Z]{2,4}";
                        System.out.print("Nhập email mới của khách hàng: ");
                        String newEmail = sc.nextLine().trim();
                        if (!Pattern.matches(regex, newEmail)) {
                            System.err.println("Vui lòng nhập đúng định dạng email.");
                            continue;
                        }
                        CustomerServiceImpl customerServiceUpdate = new CustomerServiceImpl();
                        boolean isEmailExist = customerServiceUpdate.checkEmail(newEmail);
                        if (isEmailExist) {
                            System.err.println("Email đã tồn tại. Vui lòng nhập lại.");
                            continue;
                        }
                        customer.setEmail(newEmail);
                        break;
                    } while (true);
                    break;
                case 4:
                    System.out.print("Nhập địa chỉ mới của khách hàng: ");
                    customer.setAddress(sc.nextLine().trim());
                    break;
                case 5:
                    isExist = false;
                    break;
                default:
                    System.err.println("Vui lòng nhập lựa chọn phù hợp.");
            }
        } while (isExist);

        boolean result = customerDAO.updateCustomer(customer);
        if (result) {
            System.out.println("Cập nhật thông tin khách hàng thành công.");
        } else {
            System.err.println("Có lỗi khi cập nhật thông tin.");
        }
    }

    public static void deleteCustomer(Scanner sc) {
        CustomerServiceImpl customerService = new CustomerServiceImpl();
        CustomerDAOImpl customerDAO = new CustomerDAOImpl();
        System.out.print("Nhập mã khách hàng cần xóa: ");
        int id = Integer.parseInt(sc.nextLine());
        Customer customer = customerService.findCustomerById(id);
        if (customer == null) {
            System.err.println("Không tìm thấy mã khách hàng.");
            return;
        }
        System.out.print("Bạn có chắc chắn muốn xóa khách hàng này không ? (Y/N) ");
        String choice = sc.nextLine();
        if (choice.equalsIgnoreCase("N")) {
            System.out.println("Đã hủy xóa khách hàng thành công.");
        } else if (choice.equalsIgnoreCase("Y")) {
            customerDAO.deleteCustomer(id);
            System.out.println("Xóa khách hàng thành công.");
        }
    }
}
