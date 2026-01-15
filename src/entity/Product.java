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

    public void printProductHeader() {
        System.out.println(MenuColor.CYAN +
                "┌─────┬────────────────────┬───────────────┬───────────────┬──────────┐"
                + MenuColor.RESET);

        System.out.printf(MenuColor.YELLOW +
                        "│ %-3s │ %-18s │ %-13s │ %-13s │ %-8s │%n"
                        + MenuColor.RESET,
                "ID", "Name", "Brand", "Price", "Stock");

        System.out.println(MenuColor.CYAN +
                "├─────┼────────────────────┼───────────────┼───────────────┼──────────┤"
                + MenuColor.RESET);
    }


    public void printProductRow(Product p) {
        System.out.printf(
                "│ " + MenuColor.GREEN + "%-3d" + MenuColor.RESET +
                        " │ " + MenuColor.WHITE + "%-18s" + MenuColor.RESET +
                        " │ " + MenuColor.WHITE + "%-13s" + MenuColor.RESET +
                        " │ " + MenuColor.YELLOW + "%,13.0f" + MenuColor.RESET +
                        " │ " + MenuColor.GREEN + "%8d" + MenuColor.RESET +
                        " │%n",
                p.getId(),
                p.getName(),
                p.getBrand(),
                p.getPrice(),
                p.getStock()
        );
    }


    public void printProductFooter() {
        System.out.println(MenuColor.CYAN +
                "└─────┴────────────────────┴───────────────┴───────────────┴──────────┘"
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
                System.out.println("Vui lòng nhập đúng định dạng giá tiền.");
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
