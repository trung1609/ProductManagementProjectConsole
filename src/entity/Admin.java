package entity;

import business.impl.admin.AdminServiceImpl;
import business.interfaceService.IAdminService;
import org.mindrot.jbcrypt.BCrypt;
import presentation.menu_util.MenuUtil;

import java.util.Scanner;

public class Admin {
    private int id;
    private String username;
    private String password;
    private Role role;

    public Admin() {
    }

    public Admin(int id, String username, String password, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void login(Scanner sc) {
        IAdminService adminService = new AdminServiceImpl();

        do {
            System.out.print("Nhập username: ");
            String input_username = sc.nextLine();
            System.out.print("Nhập password: ");
            String input_password = sc.nextLine();

            boolean check_exist = adminService.checkExistAdmin(input_username);
            String hashPass = null;
            boolean checkPassword = false;

            if (check_exist) {
                hashPass = adminService.getPasswordByUsername(input_username);
                if (hashPass != null) {
                    checkPassword = checkPassword(input_password, hashPass);
                }
            }

            //kết luận cuối
            if (check_exist && checkPassword) {
                this.username = input_username;
                this.password = input_password;
                // Lấy role của user
                String roleStr = adminService.getRoleByUsername(input_username);
                this.role = Role.valueOf(roleStr);
                MenuUtil.printSuccess("Đăng nhập thành công! Role: " + this.role);
                break;
            } else {
                MenuUtil.printError("Sai tài khoản hoặc mật khẩu, vui lòng nhập lại.");
            }
        } while (true);
    }
}
