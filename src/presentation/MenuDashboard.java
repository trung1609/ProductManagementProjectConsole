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
                        System.exit(0);
                    default:
                        System.err.println("Vui lòng nhập lựa chọn phù hợp");
                }
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số.");
            }
        } while (true);
    }
}
