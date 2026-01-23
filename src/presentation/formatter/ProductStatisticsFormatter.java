package presentation.formatter;

import dto.ProductStatistics;
import presentation.menu_util.MenuColor;

public class ProductStatisticsFormatter {

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

    public static void printFooter() {
        System.out.println(MenuColor.CYAN +
                "└──────────────┴────────────────────────────────┴─────────────────┴───────────────────────┘"
                + MenuColor.RESET);
    }

    public static void printRow(ProductStatistics stats) {
        String revenueStr = String.format("%,.0f VNĐ", stats.getTotalRevenue());

        System.out.printf(
                "│ " + MenuColor.GREEN + "%-12d" + MenuColor.RESET +
                        " │ " + MenuColor.WHITE + "%-30.30s" + MenuColor.RESET +
                        " │ " + MenuColor.CYAN + "%15d" + MenuColor.RESET +
                        " │ " + MenuColor.YELLOW + "%21s" + MenuColor.RESET + " │%n",
                stats.getProductId(),
                stats.getProductName(),
                stats.getTotalSold(),
                revenueStr
        );
    }
}
