package presentation.dashboard;

import presentation.login.AdminLogin;
import presentation.menu_util.MenuUtil;
import exception.ExceptionHandler;

import java.util.Scanner;

public class MenuDashboard {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] menu = {
                "Đăng nhập",
                "Thoát"
        };
        do {
            try {
                MenuUtil.printMenu("HỆ THỐNG QUẢN LÝ CỬA HÀNG", menu);

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        AdminLogin.main(args);
                        break;
                    case 2:
                        System.out.println("Cảm ơn bạn đã sử dụng hệ thống!");
                        MenuUtil.printSuccess("Thoát chương trình thành công.");
                        System.exit(0);
                    default:
                        MenuUtil.printError("Vui lòng nhập lựa chọn phù hợp");
                }
            } catch (NumberFormatException e) {
                ExceptionHandler.handleNumberFormatException();
            }
        } while (true);
    }
}
