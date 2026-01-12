package presentation.statistics;

import presentation.menuUtil.MenuUtil;

import java.util.Scanner;

public class StatisticsUI {

    public static void printRevenueResult(String title, String label, double value) {
        int width = 50;

        System.out.println(MenuUtil.CYAN +
                "┌" + "─".repeat(width - 2) + "┐" + MenuUtil.RESET);

        printCentered(title.toUpperCase(), width);

        System.out.println(MenuUtil.CYAN +
                "├" + "─".repeat(width - 2) + "┤" + MenuUtil.RESET);

        String content = String.format("%-20s : %,15.0f", label, value);
        int paddingRight = width - content.length() - 4;

        System.out.printf(
                "│ %s%" + paddingRight + "s │%n",
                content, ""
        );

        System.out.println(MenuUtil.CYAN +
                "└" + "─".repeat(width - 2) + "┘" + MenuUtil.RESET);
    }


    private static void printCentered(String text, int width) {
        int padding = width - 2 - text.length();
        int left = padding / 2;
        int right = padding - left;

        System.out.print("│");
        System.out.print(" ".repeat(left));
        System.out.print(MenuUtil.YELLOW + text + MenuUtil.RESET);
        System.out.print(" ".repeat(right));
        System.out.println("│");
    }

    public static void waitEnter() {
        System.out.println("\nNhấn Enter để quay lại menu...");
        new Scanner(System.in).nextLine();
    }
}
