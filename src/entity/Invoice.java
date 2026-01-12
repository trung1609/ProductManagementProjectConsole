package entity;


import presentation.consoleColor.ConsoleColor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Invoice {
    private int id;
    private int customerId;
    private String customerName;
    private LocalDate createdAt;
    private double totalAmount;

    public Invoice() {
    }

    public Invoice(int id, int customerId,String customerName, LocalDate createdAt, double totalAmount) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.createdAt = createdAt;
        this.totalAmount = totalAmount;
    }

    public static void printHeader() {
        System.out.println(ConsoleColor.CYAN +
                "┌─────┬─────────────┬────────────────────┬───────────────┬─────────────────┐"
                + ConsoleColor.RESET);

        System.out.printf(ConsoleColor.YELLOW +
                        "│ %-3s │ %-11s │ %-18s │ %-13s │ %-15s │%n"
                        + ConsoleColor.RESET,
                "ID", "Customer ID", "Customer Name", "Created At", "Total Amount");

        System.out.println(ConsoleColor.CYAN +
                "├─────┼─────────────┼────────────────────┼───────────────┼─────────────────┤"
                + ConsoleColor.RESET);
    }

    public void printInvoiceRow() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        System.out.printf(
                "│ " + ConsoleColor.GREEN + "%-3d" + ConsoleColor.RESET +
                        " │ " + ConsoleColor.WHITE + "%-11d" + ConsoleColor.RESET +
                        " │ " + ConsoleColor.WHITE + "%-18s" + ConsoleColor.RESET +
                        " │ " + ConsoleColor.WHITE + "%-13s" + ConsoleColor.RESET +
                        " │ " + ConsoleColor.YELLOW + "%,15.0f" + ConsoleColor.RESET +
                        " │%n",
                id,
                customerId,
                customerName,
                createdAt.format(dtf),
                totalAmount
        );
    }

    public static void printFooter() {
        System.out.println(ConsoleColor.CYAN +
                "└─────┴─────────────┴────────────────────┴───────────────┴─────────────────┘"
                + ConsoleColor.RESET);
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
