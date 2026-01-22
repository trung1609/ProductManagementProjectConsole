package presentation.menu_by_role;

import presentation.dashboard.MenuDashboard;
import presentation.products_menu.ProductManagement;
import presentation.invoice.InvoiceManagement;
import presentation.menu_util.MenuUtil;
import util.SessionManager;
import exception.ExceptionHandler;

import java.util.Scanner;

public class StaffMenu {
    public static void showMenu(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] staffMenu = {
                "Quản lý sản phẩm điện thoại",
                "Quản lý hóa đơn",
                "Đăng xuất"
        };

        do {
            try {
                MenuUtil.printMenu("MENU STAFF", staffMenu);

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        ProductManagement.main(args);
                        break;
                    case 2:
                        InvoiceManagement.main(args);
                        break;
                    case 3:
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
