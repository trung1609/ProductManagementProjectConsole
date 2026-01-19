package presentation.statistics;

import entity.Invoice;
import presentation.menu_util.MenuColor;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class StatisticsUI {

    public static void printRevenueResult(String title, String label, double value) {
        int width = 50;

        System.out.println(MenuColor.CYAN +
                "┌" + "─".repeat(width - 2) + "┐" + MenuColor.RESET);

        printCentered(title.toUpperCase(), width);

        System.out.println(MenuColor.CYAN +
                "├" + "─".repeat(width - 2) + "┤" + MenuColor.RESET);

        String content = String.format("%-20s : %,15.0f", label, value);
        int paddingRight = width - content.length() - 4;

        System.out.printf(
                "│ %s%" + paddingRight + "s │%n",
                content, ""
        );

        System.out.println(MenuColor.CYAN +
                "└" + "─".repeat(width - 2) + "┘" + MenuColor.RESET);
    }


    private static void printCentered(String text, int width) {
        int padding = width - 2 - text.length();
        int left = padding / 2;
        int right = padding - left;

        System.out.print("│");
        System.out.print(" ".repeat(left));
        System.out.print(MenuColor.YELLOW + text + MenuColor.RESET);
        System.out.print(" ".repeat(right));
        System.out.println("│");
    }

    public static void waitEnter() {
        System.out.println("\nNhấn Enter để quay lại menu...");
        new Scanner(System.in).nextLine();
    }

    public static void printRevenueEachDateResult(String title, double totalAllDate, List<Invoice> totalRevenueEachDate, DateTimeFormatter dtf) {
        int width = 50;

        DateTimeFormatter fmt = dtf != null ? dtf : DateTimeFormatter.ofPattern("dd-MM-yyyy");

        System.out.println(MenuColor.CYAN +
                "┌" + "─".repeat(width - 2) + "┐" + MenuColor.RESET);

        printCentered(title.toUpperCase(), width);

        System.out.println(MenuColor.CYAN +
                "├" + "─".repeat(width - 2) + "┤" + MenuColor.RESET);


        if (totalRevenueEachDate == null || totalRevenueEachDate.isEmpty()) {
            String empty = "Không có dữ liệu";
            int leftPad = (width - 2 - empty.length()) / 2;
            int rightPad = width - 2 - empty.length() - leftPad;

            System.out.print("│");
            System.out.print(" ".repeat(leftPad));
            System.out.print(MenuColor.WHITE + empty + MenuColor.RESET);
            System.out.print(" ".repeat(rightPad));
            System.out.println("│");

            System.out.println(MenuColor.CYAN +
                    "└" + "─".repeat(width - 2) + "┘" + MenuColor.RESET);
            return;
        }


        for (Invoice inv : totalRevenueEachDate) {
            String dateStr = inv.getCreatedAt() != null ? inv.getCreatedAt().format(fmt) : "-";
            String content = String.format("%-20s : %,15.0f", dateStr, inv.getTotalAmount());
            int paddingRight = width - content.length() - 4;

            System.out.printf("│ %s%" + paddingRight + "s │%n", content, "");
        }


        System.out.println(MenuColor.CYAN +
                "├" + "─".repeat(width - 2) + "┤" + MenuColor.RESET);


        String totalLabel = "TỔNG";
        String totalContent = String.format("%-20s : %,15.0f", totalLabel, totalAllDate);
        int paddingRight = width - totalContent.length() - 4;
        System.out.printf("│ %s%" + paddingRight + "s │%n", totalContent, "");

        System.out.println(MenuColor.CYAN +
                "└" + "─".repeat(width - 2) + "┘" + MenuColor.RESET);
    }


    public static void printRevenueEachMonthResult(String title, List<Invoice> monthlyList) {
        double total = 0.0;
        if (monthlyList != null) {
            for (Invoice inv : monthlyList) total += inv.getTotalAmount();
        }
        DateTimeFormatter monthFmt = DateTimeFormatter.ofPattern("MM-yyyy");
        printRevenueEachDateResult(title, total, monthlyList, monthFmt);
    }


    public static void printRevenueEachYearResult(String title, List<Invoice> yearlyList) {
        double total = 0.0;
        if (yearlyList != null) {
            for (Invoice inv : yearlyList) total += inv.getTotalAmount();
        }
        DateTimeFormatter yearFmt = DateTimeFormatter.ofPattern("yyyy");
        printRevenueEachDateResult(title, total, yearlyList, yearFmt);
    }
}
