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
                System.out.println();
                MainMenu.main(args);
                return;
            } else {
                MenuUtil.printError("Sai tài khoản hoặc mật khẩu, vui lòng nhập lại.");
            }
            System.out.println();
        } while (true);
    }

}
