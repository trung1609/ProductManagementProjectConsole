package entity;

import presentation.menu_util.MenuColor;

public class InvoiceDetails {
    private int id;
    private int invoice_id;
    private int customerId;
    private String customerName;
    private int product_id;
    private String product_name;
    private int quantity;
    private double price;
    private double total;

    public InvoiceDetails(int id, int invoice_id, int customerId,
                          String customerName, int product_id, String product_name, int quantity, double price) {
        this.id = id;
        this.invoice_id = invoice_id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.product_id = product_id;
        this.product_name = product_name;
        this.quantity = quantity;
        this.price = price;
    }

    public InvoiceDetails() {
    }

    public static void printHeader() {
        System.out.println(MenuColor.CYAN +
                "┌─────┬────────────┬─────────────┬────────────────────┬──────────────────────┬──────────┬───────────────────┬───────────────────┐"
                + MenuColor.RESET);

        System.out.printf(MenuColor.YELLOW +
                        "│ %-3s │ %-10s │ %-11s │ %-18s │ %-20s │ %-8s │ %-17s │ %-17s │%n"
                        + MenuColor.RESET,
                "ID", "Invoice ID", "Customer ID", "Customer Name", "Product Name", "Qty", "Unit Price", "Total");

        System.out.println(MenuColor.CYAN +
                "├─────┼────────────┼─────────────┼────────────────────┼──────────────────────┼──────────┼───────────────────┼───────────────────┤"
                + MenuColor.RESET);
    }

    public static void printFooter() {
        System.out.println(MenuColor.CYAN +
                "└─────┴────────────┴─────────────┴────────────────────┴──────────────────────┴──────────┴───────────────────┴───────────────────┘"
                + MenuColor.RESET);
    }

    public static void printTotalAmount(double totalAmount) {
        int width = 129;
        String line = "─".repeat(width - 2);

        System.out.println(MenuColor.CYAN + "┌" + line + "┐" + MenuColor.RESET);

        String label = " TỔNG TIỀN:";
        String amount = String.format("%,.0f VNĐ ", totalAmount);

        int spaceBetween = width - 2 - label.length() - amount.length();

        System.out.printf("│" + MenuColor.YELLOW + "%s" + MenuColor.RESET + "%" + spaceBetween + "s" + MenuColor.GREEN + "%s" + MenuColor.RESET + "│%n",
                label, "", amount);

        System.out.println(MenuColor.CYAN + "└" + line + "┘" + MenuColor.RESET);
    }

    public void printInvoiceDetailRow() {
        System.out.printf(
                "│ " + MenuColor.GREEN + "%-3d" + MenuColor.RESET +
                        " │ " + MenuColor.WHITE + "%-10d" + MenuColor.RESET +
                        " │ " + MenuColor.WHITE + "%-11d" + MenuColor.RESET +
                        " │ " + MenuColor.WHITE + "%-18.18s" + MenuColor.RESET +
                        " │ " + MenuColor.WHITE + "%-20.20s" + MenuColor.RESET +
                        " │ " + MenuColor.GREEN + "%-8d" + MenuColor.RESET +
                        " │ " + MenuColor.YELLOW + "%13s VNĐ" + MenuColor.RESET +
                        " │ " + MenuColor.YELLOW + "%13s VNĐ" + MenuColor.RESET +
                        " │%n",
                id, invoice_id, customerId, customerName, product_name, quantity,
                String.format("%,.0f", price),
                String.format("%,.0f", quantity * price)
        );
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

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
