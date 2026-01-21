package entity;

import presentation.menu_util.MenuColor;

public class ProductStatistics {
    private int productId;
    private String productName;
    private int totalSold;
    private double totalRevenue;

    public ProductStatistics() {
    }

    public ProductStatistics(int productId, String productName, int totalSold, double totalRevenue) {
        this.productId = productId;
        this.productName = productName;
        this.totalSold = totalSold;
        this.totalRevenue = totalRevenue;
    }

    public static void printHeader() {
        System.out.println(MenuColor.CYAN +
                "┌──────────────┬────────────────────────────────┬─────────────────┬───────────────────────┐"
                + MenuColor.RESET);

        System.out.printf(MenuColor.YELLOW +
                        "│ %-12s │ %-30s │ %-15s │ %-21s │%n"
                        + MenuColor.RESET,
                "Product ID", "Product Name", "Total Sold", "Total Revenue");

        System.out.println(MenuColor.CYAN +
                "├──────────────┼────────────────────────────────┼─────────────────┼───────────────────────┤"
                + MenuColor.RESET);
    }

    public void printRow() {
        String revenueStr = String.format("%,.0f VNĐ", totalRevenue);

        System.out.printf(
                "│ " + MenuColor.GREEN + "%-12d" + MenuColor.RESET +
                        " │ " + MenuColor.WHITE + "%-30.30s" + MenuColor.RESET +
                        " │ " + MenuColor.CYAN + "%15d" + MenuColor.RESET +
                        " │ " + MenuColor.YELLOW + "%21s" + MenuColor.RESET + " │%n",
                productId,
                productName,
                totalSold,
                revenueStr
        );
    }

    public static void printFooter() {
        System.out.println(MenuColor.CYAN +
                "└──────────────┴────────────────────────────────┴─────────────────┴───────────────────────┘"
                + MenuColor.RESET);
    }

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getTotalSold() {
        return totalSold;
    }

    public void setTotalSold(int totalSold) {
        this.totalSold = totalSold;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
