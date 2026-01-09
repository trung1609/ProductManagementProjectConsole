package presentation;

import java.util.Scanner;

public class MenuDashboard {
    static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        do {
            try {
                System.out.println("============ HỆ THỐNG QUẢN LÝ CỬA HÀNG ============");
                System.out.println("1. Đăng nhập Admin");
                System.out.println("2. Thoát");
                System.out.println("===================================================");
                System.out.print("Nhập lựa chọn: ");
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
            }catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập lại lựa chọn.");
            }
        } while (true);
    }
}
