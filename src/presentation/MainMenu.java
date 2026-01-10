package presentation;

import presentation.invoice.InvoiceManagement;

import java.util.Scanner;
import presentation.menuUtil.MenuUtil;
import presentation.statistics.RevenueStatistics;

public class MainMenu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] mainMenu = {
                "Quản lý sản phẩm điện thoại",
                "Quản lý khách hàng",
                "Quản lý hóa đơn",
                "Thống kê doanh thu",
                "Đăng xuất"
        };

        do {
            try {
                MenuUtil.printMenu("MENU CHÍNH", mainMenu);

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        ProductManagement.main(args);
                        break;
                    case 2:
                        CustomerManagement.main(args);
                        break;
                    case 3:
                        InvoiceManagement.main(args);
                        break;
                    case 4:
                        RevenueStatistics.main(args);
                        break;
                    case 5:
                        MenuDashboard.main(args);
                        return;
                    default:
                        System.err.println("Vui lòng nhập lựa chọn phù hợp.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số.");
            }
        } while (true);
    }
}

