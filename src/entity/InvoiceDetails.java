package entity;

import presentation.consoleColor.ConsoleColor;

public class InvoiceDetails {
    private int id;
    private int invoice_id;
    private int product_id;
    private int quantity;
    private double price;

    public InvoiceDetails(int id, int invoice_id, int product_id, int quantity, double price) {
        this.id = id;
        this.invoice_id = invoice_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
    }

    public InvoiceDetails() {
    }

    public static void printHeader() {
        System.out.println(ConsoleColor.CYAN +
                "┌─────┬────────────┬────────────┬──────────┬───────────────┐"
                + ConsoleColor.RESET);

        System.out.printf(ConsoleColor.YELLOW +
                        "│ %-3s │ %-10s │ %-10s │ %-8s │ %-13s │%n"
                        + ConsoleColor.RESET,
                "ID", "Invoice ID", "Product ID", "Quantity", "Unit Price");

        System.out.println(ConsoleColor.CYAN +
                "├─────┼────────────┼────────────┼──────────┼───────────────┤"
                + ConsoleColor.RESET);
    }

    public void printInvoiceDetailRow() {
        System.out.printf(
                "│ " + ConsoleColor.GREEN + "%-3d" + ConsoleColor.RESET +
                        " │ " + ConsoleColor.WHITE + "%-10d" + ConsoleColor.RESET +
                        " │ " + ConsoleColor.WHITE + "%-10d" + ConsoleColor.RESET +
                        " │ " + ConsoleColor.GREEN + "%-8d" + ConsoleColor.RESET +
                        " │ " + ConsoleColor.YELLOW + "%13.2f" + ConsoleColor.RESET +
                        " │%n",
                id,
                invoice_id,
                product_id,
                quantity,
                price
        );
    }

    public static void printFooter() {
        System.out.println(ConsoleColor.CYAN +
                "└─────┴────────────┴────────────┴──────────┴───────────────┘"
                + ConsoleColor.RESET);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
