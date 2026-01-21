package presentation;

import business.impl.customer.CustomerServiceImpl;
import entity.Customer;
import presentation.menu_util.MenuUtil;
import presentation.statistics.StatisticsUI;
import exception.ExceptionHandler;

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
                        MenuUtil.printError("Vui lòng nhập lựa chọn phù hợp.");
                }
            } catch (NumberFormatException e) {
                ExceptionHandler.handleNumberFormatException();
            }
        } while (true);
    }

    public static void addCustomer() {
        Scanner sc = new Scanner(System.in);
        Customer customer = new Customer();
        customer.input(sc);
        CustomerServiceImpl customerService = new CustomerServiceImpl();
        boolean result = customerService.createCustomer(customer);
        if (result) {
            MenuUtil.printSuccess("Thêm khách hàng thành công.");
        } else {
            MenuUtil.printError("Có lỗi khi thêm khách hàng.");
        }
    }

    public static void getAllCustomers() {
        CustomerServiceImpl customerService = new CustomerServiceImpl();
        List<Customer> customerList = customerService.findAllCustomers();
        if (customerList.isEmpty()) {
            MenuUtil.printError("Chưa có khách hàng được thêm.");
        } else {
            Customer customer = new Customer();
            MenuUtil.printListItems("DANH SÁCH KHÁCH HÀNG", 120);
            customer.printCustomerHeader();
            for (Customer c : customerList) {
                c.printCustomerRow(c);
            }
            customer.printCustomerFooter();
            System.out.println("Tổng số khách hàng: " + customerList.size());
            StatisticsUI.waitEnter();
        }
    }

    public static void updateCustomer(Scanner sc) {
        String[] updateCustomerMenu = {
                "Cập nhật tên khách hàng",
                "Cập nhật số điện thoại khách hàng",
                "Cập nhật email khách hàng",
                "Cập nhật địa chỉ khách hàng",
                "Thoát"
        };
        CustomerServiceImpl customerService = new CustomerServiceImpl();
        MenuUtil.printListItems("DANH SÁCH KHÁCH HÀNG", 95);
        List<Customer> customerList = customerService.findAllCustomers();
        Customer customerDisplay = new Customer();
        customerDisplay.printCustomerHeader();
        for (Customer c : customerList) {
            c.printCustomerRow(c);
        }
        customerDisplay.printCustomerFooter();
        System.out.print("Nhập mã khách hàng cần cập nhật: ");
        int id = Integer.parseInt(sc.nextLine());
        Customer customer = customerService.findCustomerById(id);
        if (customer == null) {
            MenuUtil.printError("Không tìm thấy mã khách hàng");
            return;
        }
        boolean isExist = true;
        MenuUtil.printListItems("THÔNG TIN KHÁCH HÀNG CẦN CẬP NHẬT", 95);
        customer.printCustomerHeader();
        for (int i = 0; i < 1; i++) {
            customer.printCustomerRow(customer);
        }
        customer.printCustomerFooter();
        do {
            try {
                MenuUtil.printMenu("CẬP NHẬT THÔNG TIN KHÁCH HÀNG", updateCustomerMenu);
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        do {
                            String regex = "[\\p{L} ]+";
                            System.out.print("Nhập tên mới của khách hàng: ");
                            String newName = sc.nextLine().trim();
                            if (!Pattern.matches(regex, newName)) {
                                MenuUtil.printError("Vui lòng nhập lại tên khách hàng chỉ chứa chữ cái và khoảng cách.");
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
                                MenuUtil.printError("Vui lòng nhập lại số điện thoại đúng định dạng");
                                continue;
                            }
                            boolean isExistPhone = customerService.checkPhone(newPhone);
                            if (isExistPhone) {
                                MenuUtil.printError("Số điện thoại đã tồn tại. Vui lòng nhập lại.");
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
                                MenuUtil.printError("Vui lòng nhập đúng định dạng email.");
                                continue;
                            }
                            CustomerServiceImpl customerServiceUpdate = new CustomerServiceImpl();
                            boolean isEmailExist = customerServiceUpdate.checkEmail(newEmail);
                            if (isEmailExist) {
                                MenuUtil.printError("Email đã tồn tại. Vui lòng nhập lại.");
                                continue;
                            }
                            customer.setEmail(newEmail);
                            break;
                        } while (true);
                        break;
                    case 4:
                        do {
                            System.out.print("Nhập địa chỉ: ");
                            String addressInput = sc.nextLine().trim();
                            if (addressInput.isEmpty()) {
                                MenuUtil.printError("Địa chỉ không được để trống. Vui lòng nhập lại.");
                                continue;
                            }
                            customer.setAddress(addressInput);
                            break;
                        } while (true);
                        break;
                    case 5:
                        isExist = false;
                        break;
                    default:
                        System.out.println("Vui lòng nhập lựa chọn phù hợp.");
                }
            } catch (NumberFormatException e) {
                ExceptionHandler.handleNumberFormatException();
            }
        } while (isExist);

        boolean result = customerService.updateCustomer(customer);
        if (result) {
            MenuUtil.printSuccess("Cập nhật thông tin khách hàng thành công.");
        } else {
            MenuUtil.printError("Có lỗi khi cập nhật thông tin.");
        }
    }

    public static void deleteCustomer(Scanner sc) {
        CustomerServiceImpl customerService = new CustomerServiceImpl();
        Customer customer = new Customer();
        List<Customer> customerList = customerService.findAllCustomers();
        MenuUtil.printListItems("DANH SÁCH SẢN PHẨM", 95);
        customer.printCustomerHeader();
        for (Customer c : customerList) {
            c.printCustomerRow(c);
        }
        customer.printCustomerFooter();
        System.out.print("Nhập mã khách hàng cần xóa: ");
        int id = Integer.parseInt(sc.nextLine());
        Customer checkCustomerId = customerService.findCustomerById(id);
        if (checkCustomerId == null) {
            MenuUtil.printError("Không tìm thấy mã khách hàng.");
            return;
        }
        System.out.print("Bạn có chắc chắn muốn xóa khách hàng này không ? (Y/N) ");
        String choice = sc.nextLine();
        if (choice.equalsIgnoreCase("N")) {
            MenuUtil.printSuccess("Đã hủy xóa khách hàng thành công.");
        } else if (choice.equalsIgnoreCase("Y")) {
            boolean result = customerService.deleteCustomer(id);
            if (result) {
                MenuUtil.printSuccess("Xóa khách hàng thành công.");
            } else {
                MenuUtil.printError("Có lỗi khi xóa khách hàng.");
            }
        }
    }
}
