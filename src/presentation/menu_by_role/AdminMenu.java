package presentation.menu_by_role;

import exception.ExceptionHandler;
import presentation.customers_menu.CustomerManagement;
import presentation.dashboard.MenuDashboard;
import presentation.invoice.InvoiceManagement;
import presentation.menu_util.MenuUtil;
import presentation.products_menu.ProductManagement;
import presentation.statistics.RevenueStatistics;
import util.SessionManager;

import java.util.Scanner;

public class AdminMenu {
    public static void showMenu(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] adminMenu = {
                "Quản lý sản phẩm điện thoại",
                "Quản lý khách hàng",
                "Quản lý hóa đơn",
                "Thống kê doanh thu",
                "Đăng xuất"
        };

        do {
            try {
                MenuUtil.printMenu("MENU ADMIN", adminMenu);

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
                        SessionManager.clearSession();
                        MenuUtil.printSuccess("Đăng xuất thành công!");
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
