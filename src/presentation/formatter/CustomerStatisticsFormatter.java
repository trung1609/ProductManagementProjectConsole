package presentation.formatter;

import dto.CustomerStatistics;
import presentation.menu_util.MenuColor;

public class CustomerStatisticsFormatter {

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

    public static void printRow(CustomerStatistics stats) {
        String spentStr = String.format("%,.0f VNĐ", stats.getTotalSpent());

        System.out.printf(
                "│ " + MenuColor.GREEN + "%-12d" + MenuColor.RESET +
                        " │ " + MenuColor.WHITE + "%-30.30s" + MenuColor.RESET +
                        " │ " + MenuColor.CYAN + "%15d" + MenuColor.RESET +
                        " │ " + MenuColor.YELLOW + "%21s" + MenuColor.RESET + " │%n",
                stats.getCustomerId(),
                stats.getCustomerName(),
                stats.getTotalOrders(),
                spentStr
        );
    }
}
