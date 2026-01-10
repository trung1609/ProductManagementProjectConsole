package entity;

import java.util.Scanner;

public class Admin {
    private int id;
    private String username;
    private String password;

    public Admin() {
    }

    public Admin(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
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

    public void login(Scanner scanner) {
        System.out.print("Tài khoản: ");
        this.username = scanner.nextLine();
        System.out.print("Mật khẩu: ");
        this.password = scanner.nextLine();
    }
}
