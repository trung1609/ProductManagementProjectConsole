package entity;

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
    public InvoiceDetails() {}

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

    public static void printHeader() {
        System.out.println("┌─────┬────────────┬────────────┬──────────┬───────────────┐");
        System.out.printf("│ %-3s │ %-10s │ %-10s │ %-8s │ %-13s │%n",
                "ID", "Invoice ID", "Product ID", "Quantity", "Unit Price");
        System.out.println("├─────┼────────────┼────────────┼──────────┼───────────────┤");
    }

    public static void printFooter() {
        System.out.println("└─────┴────────────┴────────────┴──────────┴───────────────┘");
    }

    @Override
    public String toString() {
        return String.format("│ %-3d │ %-10d │ %-10d │ %-8d │ %13.2f │",
                id, invoice_id, product_id, quantity, price);
    }
}
