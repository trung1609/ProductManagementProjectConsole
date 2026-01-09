package entity;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Invoice {
    private int id;
    private int customerId;
    private LocalDate createdAt;
    private double totalAmount;

    public Invoice() {
    }

    public Invoice(int id, int customerId, LocalDate createdAt, double totalAmount) {
        this.id = id;
        this.customerId = customerId;
        this.createdAt = createdAt;
        this.totalAmount = totalAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public static void printHeader() {
        System.out.println("┌─────┬──────────────┬──────────────┬─────────────────┐");
        System.out.printf("│ %-3s │ %-12s │ %-12s │ %-15s │%n",
                "ID", "Customer ID", "Created At", "Total Amount");
        System.out.println("├─────┼──────────────┼──────────────┼─────────────────┤");
    }

    public static void printFooter() {
        System.out.println("└─────┴──────────────┴──────────────┴─────────────────┘");
    }

    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return String.format("│ %-3d │ %-12d │ %-12s │ %15.2f │",
                id, customerId, createdAt.format(dtf), totalAmount);
    }
}
