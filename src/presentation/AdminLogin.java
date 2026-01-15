package presentation;

import entity.Admin;
import presentation.menu_util.MenuUtil;

import java.util.Scanner;

public class AdminLogin {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MenuUtil.printLoginHeader("ĐĂNG NHẬP QUẢN TRỊ");
        Admin admin = new Admin();
        admin.login(sc);
        MainMenu.main(args);
    }
}
