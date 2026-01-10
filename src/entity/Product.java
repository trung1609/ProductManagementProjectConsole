package entity;

import presentation.consoleColor.ConsoleColor;

import java.util.Scanner;

public class Product {
    private int id;
    private String name;
    private String brand;
    private double price;
    private int stock;

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

    public void printProductHeader() {
        System.out.println(ConsoleColor.CYAN +
                "┌─────┬──────────────┬──────────────┬──────────────┬──────────┐"
                + ConsoleColor.RESET);

        System.out.printf(ConsoleColor.YELLOW +
                        "│ %-3s │ %-12s │ %-12s │ %-12s │ %-8s │%n"
                        + ConsoleColor.RESET,
                "ID", "Name", "Brand", "Price", "Stock");

        System.out.println(ConsoleColor.CYAN +
                "├─────┼──────────────┼──────────────┼──────────────┼──────────┤"
                + ConsoleColor.RESET);
    }


    public void printProductRow(Product p) {
        System.out.printf(
                "│ " + ConsoleColor.GREEN + "%-3d" + ConsoleColor.RESET +
                        " │ " + ConsoleColor.WHITE + "%-12s" + ConsoleColor.RESET +
                        " │ " + ConsoleColor.WHITE + "%-12s" + ConsoleColor.RESET +
                        " │ " + ConsoleColor.YELLOW + "%12.2f" + ConsoleColor.RESET +
                        " │ " + ConsoleColor.GREEN + "%8d" + ConsoleColor.RESET +
                        " │%n",
                p.getId(),
                p.getName(),
                p.getBrand(),
                p.getPrice(),
                p.getStock()
        );
    }


    public void printProductFooter() {
        System.out.println(ConsoleColor.CYAN +
                "└─────┴──────────────┴──────────────┴──────────────┴──────────┘"
                + ConsoleColor.RESET);
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
                System.out.print("Nhập gia bán: ");
                double priceInput = Double.parseDouble(sc.nextLine());
                if (priceInput <= 0) {
                    System.err.println("Vui lòng nhập giá bán lớn hơn hoặc bằng 0");
                    continue;
                }
                this.price = priceInput;
                break;
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập đúng định dạng giá tiền.");
            }
        } while (true);
    }

    public void inputStock(Scanner sc) {
        do {
            try {
                System.out.print("Nhập số lượng tồn kho: ");
                int stockInput = Integer.parseInt(sc.nextLine());
                if (stockInput <= 0) {
                    System.err.println("Vui lòng nhập số lượng lớn hơn 0");
                    continue;
                }
                this.stock = stockInput;
                break;
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập đúng định dạng số lượng tồn kho.");
            }
        } while (true);
    }
}
