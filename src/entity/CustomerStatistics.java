package entity;

import presentation.menu_util.MenuColor;

public class CustomerStatistics {
    private int customerId;
    private String customerName;
    private int totalOrders;
    private double totalSpent;

    public CustomerStatistics() {
    }

    public CustomerStatistics(int customerId, String customerName, int totalOrders, double totalSpent) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.totalOrders = totalOrders;
        this.totalSpent = totalSpent;
    }

    public static void printHeader() {
        System.out.println(MenuColor.CYAN +
                "┌──────────────┬────────────────────────────────┬─────────────────┬───────────────────────┐"
                + MenuColor.RESET);

        System.out.printf(MenuColor.YELLOW +
                        "│ %-12s │ %-30s │ %-15s │ %-21s │%n"
                        + MenuColor.RESET,
                "Customer ID", "Customer Name", "Total Orders", "Total Spent");

        System.out.println(MenuColor.CYAN +
                "├──────────────┼────────────────────────────────┼─────────────────┼───────────────────────┤"
                + MenuColor.RESET);
    }

    public static void printFooter() {
        System.out.println(MenuColor.CYAN +
                "└──────────────┴────────────────────────────────┴─────────────────┴───────────────────────┘"
                + MenuColor.RESET);
    }

    // Getters and Setters
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

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public double getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(double totalSpent) {
        this.totalSpent = totalSpent;
    }

    public void printRow() {
        String revenueStr = String.format("%,.0f VNĐ", totalSpent);

        System.out.printf(
                "│ " + MenuColor.GREEN + "%-12d" + MenuColor.RESET +
                        " │ " + MenuColor.WHITE + "%-30.30s" + MenuColor.RESET +
                        " │ " + MenuColor.CYAN + "%15d" + MenuColor.RESET +
                        " │ " + MenuColor.YELLOW + "%21s" + MenuColor.RESET + " │%n",
                customerId,
                customerName,
                totalOrders,
                revenueStr
        );
    }
}
