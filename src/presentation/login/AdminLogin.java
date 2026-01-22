package presentation.login;

import entity.Admin;
import entity.Role;
import presentation.dashboard.MenuDashboard;
import presentation.menu_by_role.AdminMenu;
import presentation.menu_by_role.StaffMenu;
import presentation.menu_util.MenuUtil;
import util.SessionManager;

import java.util.Scanner;

public class AdminLogin {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MenuUtil.printLoginHeader("ĐĂNG NHẬP QUẢN TRỊ");
        Admin admin = new Admin();
        admin.login(sc);

        // Lưu thông tin user vào session
        SessionManager.setCurrentUser(admin);

        // Chuyển hướng đến menu tương ứng theo role
        if (admin.getRole() == Role.ADMIN) {
            AdminMenu.showMenu(args);
        } else if (admin.getRole() == Role.STAFF) {
            StaffMenu.showMenu(args);
        } else {
            MenuUtil.printError("Role không hợp lệ!");
            MenuDashboard.main(args);
        }
    }
}
