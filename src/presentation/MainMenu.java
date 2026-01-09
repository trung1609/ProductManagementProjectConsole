package presentation;

import java.util.Scanner;

public class MainMenu {
    static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        do {
            try {
                System.out.println("============ MENU CHÍNH ============");
                System.out.println("1. Quản lý sản phẩm điện thoại");
                System.out.println("2. Quản lý khách hàng");
                System.out.println("3. Quản lý hóa đơn");
                System.out.println("4. Thống kê doanh thu");
                System.out.println("5. Đăng xuất");
                System.out.println("===================================");
                System.out.print("Nhập lựa chọn: ");
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        ProductManagement.main(args);
                        break;
                    case 2:
                        CustomerManagement.main(args);
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        MenuDashboard.main(args);
                        return;
                    default:
                        System.err.println("Vui lòng nhập lựa chọn phù hợp.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập lại lựa chọn.");
            }
        } while (true);
    }
}
