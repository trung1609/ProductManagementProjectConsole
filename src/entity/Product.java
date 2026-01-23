package entity;

import business.impl.product.ProductServiceImpl;
import presentation.menu_util.MenuColor;
import presentation.menu_util.MenuUtil;

import java.util.Scanner;

public class Product {
    private int id;
    private String name;
    private String brand;
    private double price;
    private int stock;
    private boolean status;

    public Product() {
    }

    public Product(int id, String name, String brand, double price, int stock) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.stock = stock;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static void printProductHeader() {
        // Tổng độ rộng mới: 5 + 20 + 15 + 19 + 10 = 69 (cộng các thanh chia)
        System.out.println(MenuColor.CYAN +
                "┌─────┬──────────────────────┬───────────────┬─────────────────────┬──────────┐"
                + MenuColor.RESET);

        System.out.printf(MenuColor.YELLOW +
                        "│ %-3s │ %-20s │ %-13s │ %-19s │ %-8s │%n"
                        + MenuColor.RESET,
                "ID", "Name", "Brand", "Price", "Stock");

        System.out.println(MenuColor.CYAN +
                "├─────┼──────────────────────┼───────────────┼─────────────────────┼──────────┤"
                + MenuColor.RESET);
    }

    public void printProductRow(Product p) {
        // Sử dụng String.format riêng cho tiền để kiểm soát độ rộng 19 ký tự
        String priceStr = String.format("%,.0f VNĐ", p.getPrice());

        System.out.printf(
                "│ " + MenuColor.GREEN + "%-3d" + MenuColor.RESET +
                        " │ " + MenuColor.WHITE + "%-20.20s" + MenuColor.RESET +
                        " │ " + MenuColor.WHITE + "%-13.13s" + MenuColor.RESET +
                        " │ " + MenuColor.YELLOW + "%19s" + MenuColor.RESET +
                        " │ " + MenuColor.GREEN + "%8d" + MenuColor.RESET +
                        " │%n",
                p.getId(), p.getName(), p.getBrand(), priceStr, p.getStock()
        );
    }

    public static void printProductFooter() {
        System.out.println(MenuColor.CYAN +
                "└─────┴──────────────────────┴───────────────┴─────────────────────┴──────────┘"
                + MenuColor.RESET);
    }


    public void input(Scanner sc) {
        inputName(sc);
        inputBrand(sc);
        inputPrice(sc);
        inputStock(sc);
    }

    public void inputName(Scanner sc) {
        do {
            try {
                System.out.print("Nhập tên sản phẩm: ");
                this.name = sc.nextLine();
                if (name.isEmpty()) {
                    MenuUtil.printError("Vui lòng không bỏ trống tên sản phẩm.");
                    continue;
                }
                ProductServiceImpl productService = new ProductServiceImpl();
                if (productService.checkProductName(this.name)) {
                    MenuUtil.printError("Tên sản phẩm đã tồn tại. Vui lòng nhập tên khác.");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public void inputBrand(Scanner sc) {
        do {
            try {
                System.out.print("Nhập thương hiệu sản phẩm: ");
                this.brand = sc.nextLine();
                if (brand.isEmpty()) {
                    MenuUtil.printError("Vui lòng không bỏ trống thương hiệu sản phẩm.");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public void inputPrice(Scanner sc) {
        do {
            try {
                System.out.print("Nhập giá bán: ");
                double priceInput = Double.parseDouble(sc.nextLine());
                if (priceInput <= 0) {
                    MenuUtil.printError("Vui lòng nhập giá bán lớn hơn hoặc bằng 0");
                    continue;
                }
                this.price = priceInput;
                break;
            } catch (NumberFormatException e) {
                MenuUtil.printError("Vui lòng nhập đúng định dạng giá tiền.");
            }
        } while (true);
    }

    public void inputStock(Scanner sc) {
        do {
            try {
                System.out.print("Nhập số lượng tồn kho: ");
                int stockInput = Integer.parseInt(sc.nextLine());
                if (stockInput <= 0) {
                    MenuUtil.printError("Vui lòng nhập số lượng lớn hơn 0");
                    continue;
                }
                this.stock = stockInput;
                break;
            } catch (NumberFormatException e) {
                MenuUtil.printError("Vui lòng nhập đúng định dạng số lượng tồn kho.");
            }
        } while (true);
    }
}
