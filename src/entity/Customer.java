package entity;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Customer {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String address;

    public Customer() {
    }

    public Customer(int id, String name, String phone, String email, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return String.format("ID: %d - Name: %s - Phone: %s - Email: %s - Address: %s", id, name, phone, email, address);
    }

    public void input(Scanner sc) {
        inputName(sc);
        inputPhone(sc);
        inputEmail(sc);
        inputAddress(sc);
    }

    public void inputName(Scanner sc) {
        String regex = "[\\p{L}]+";
        do {
            System.out.print("Nhập tên khách hàng: ");
            String nameInput = sc.nextLine().trim();
            if (!Pattern.matches(regex, nameInput)) {
                System.err.println("Vui lòng nhập lại tên khách hàng chỉ chứa chữ cái.");
                continue;
            }
            this.name = nameInput;
            break;
        } while (true);
    }

    public void inputPhone(Scanner sc) {
        String regex = "0[3|5789][0-9]{8}";
        do {
            System.out.print("Nhập số điện thoại khách hàng: ");
            String phoneInput = sc.nextLine();
            if (!Pattern.matches(regex, phoneInput)) {
                System.err.println("Vui lòng nhập lại số điện thoại đúng định dạng");
                continue;
            }
            this.phone = phoneInput;
            break;
        } while (true);
    }

    public void inputEmail(Scanner sc) {
        String regex = "[a-zA-Z0-9._]+@[\\w]+\\.[a-zA-Z]{2,4}";
        do {
            System.out.print("Nhập email khách hàng: ");
            String emailInput = sc.nextLine().trim();
            if (!Pattern.matches(regex, emailInput)) {
                System.err.println("Vui lòng nhập đúng định dạng email.");
                continue;
            }
            this.email = emailInput;
            break;
        } while (true);
    }

    public void inputAddress(Scanner sc) {
        System.out.print("Nhập địa chỉ: ");
        this.address = sc.nextLine().trim();
    }

}
