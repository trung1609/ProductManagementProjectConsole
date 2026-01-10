package presentation;

import business.AdminBusiness;
import entity.Admin;
import presentation.menuUtil.MenuUtil;

import java.util.Scanner;

public class AdminLogin {
    static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        do {
            MenuUtil.printLoginHeader("ĐĂNG NHẬP QUẢN TRỊ");

            Admin admin = new Admin();
            admin.login(sc);

            boolean checkLogin = AdminBusiness.loginAdmin(
                    admin.getUsername(),
                    admin.getPassword()
            );

            if (checkLogin) {
                System.out.println("✔ Đăng nhập thành công!");
                MainMenu.main(args);
                return;
            } else {
                System.err.println("✖ Sai tài khoản hoặc mật khẩu, vui lòng nhập lại.");
            }
            MenuUtil.printFooter();
        } while (true);
    }

}
