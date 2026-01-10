package entity;


import presentation.consoleColor.ConsoleColor;

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

    public static void printHeader() {
        System.out.println(ConsoleColor.CYAN +
                "┌─────┬──────────────┬──────────────┬─────────────────┐"
                + ConsoleColor.RESET);

        System.out.printf(ConsoleColor.YELLOW +
                        "│ %-3s │ %-12s │ %-12s │ %-15s │%n"
                        + ConsoleColor.RESET,
                "ID", "Customer ID", "Created At", "Total Amount");

        System.out.println(ConsoleColor.CYAN +
                "├─────┼──────────────┼──────────────┼─────────────────┤"
                + ConsoleColor.RESET);
    }

    public void printInvoiceRow() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        System.out.printf(
                "│ " + ConsoleColor.GREEN + "%-3d" + ConsoleColor.RESET +
                        " │ " + ConsoleColor.WHITE + "%-12d" + ConsoleColor.RESET +
                        " │ " + ConsoleColor.WHITE + "%-12s" + ConsoleColor.RESET +
                        " │ " + ConsoleColor.YELLOW + "%15.2f" + ConsoleColor.RESET +
                        " │%n",
                id,
                customerId,
                createdAt.format(dtf),
                totalAmount
        );
    }

    public static void printFooter() {
        System.out.println(ConsoleColor.CYAN +
                "└─────┴──────────────┴──────────────┴─────────────────┘"
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


}
