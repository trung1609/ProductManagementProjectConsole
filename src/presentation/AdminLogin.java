package presentation;

import business.AdminBusiness;
import entity.Admin;

import java.util.Scanner;

public class AdminLogin {
    static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("============ ĐĂNG NHẬP QUẢN TRỊ ============");
            Admin admin = new Admin();
            admin.login(sc);
            boolean checkLogin = AdminBusiness.loginAdmin(admin.getUsername(), admin.getPassword());
            if (checkLogin) {
                MainMenu.main(args);
                break;
            } else {
                System.err.println("Sai tài khoản và mật khẩu vui lòng nhập lại.");
            }
            System.out.println("============================================");
        } while (true);
    }
}
