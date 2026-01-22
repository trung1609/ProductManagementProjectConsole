package presentation.main_menu;

import exception.ExceptionHandler;
import presentation.customers_menu.CustomerManagement;
import presentation.dashboard.MenuDashboard;
import presentation.invoice.InvoiceManagement;
import presentation.menu_util.MenuUtil;
import presentation.products_menu.ProductManagement;
import presentation.statistics.RevenueStatistics;

import java.util.Scanner;

public class MainMenu {
    static void main(String[] args) {
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
                        MenuUtil.printError("Vui lòng nhập lựa chọn phù hợp.");
                }
            } catch (NumberFormatException e) {
                ExceptionHandler.handleNumberFormatException();
            }
        } while (true);
    }
}

