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

    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return String.format("ID: %d - Customer ID: %d - Created At: %s - Total Amount: %.2f", id, customerId, createdAt.format(dtf), totalAmount);
    }
}
