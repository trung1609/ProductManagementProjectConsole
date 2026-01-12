package presentation;

import presentation.menuUtil.MenuUtil;

import java.util.Scanner;

public class MenuDashboard {
    static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] menu = {
                "Đăng nhập Admin",
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
                MenuUtil.printError("Vui lòng nhập số.");
            }
        } while (true);
    }
}
