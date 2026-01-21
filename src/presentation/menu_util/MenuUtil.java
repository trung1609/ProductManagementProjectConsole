package presentation.menu_util;


public class MenuUtil {


    public static final boolean ENABLE_COLOR = true;

    private static String color(String c) {
        return ENABLE_COLOR ? c : "";
    }

    public static void printMenu(String title, String[] options) {
        int width = 50;

        printLine(width, '┌', '┐');
        printCentered(color(MenuColor.CYAN) + title + color(MenuColor.RESET), width);
        printLine(width, '├', '┤');

        for (int i = 0; i < options.length; i++) {
            String index = color(MenuColor.YELLOW) + (i + 1) + "." + color(MenuColor.RESET);
            String text = color(MenuColor.GREEN) + options[i] + color(MenuColor.RESET);

            String content = index + " " + text;

            int paddingRight = width - stripAnsi(content).length() - 4;
            System.out.printf("│ %s%" + paddingRight + "s │%n", content, "");
        }

        printLine(width, '└', '┘');
        System.out.print("Nhập lựa chọn: ");
    }

    public static void printListItems(String title, int width) {
        int textLength = stripAnsi(title).length();
        int totalPadding = width - 2 - textLength;
        int left = totalPadding / 2;
        int right = totalPadding - left;
        System.out.println();
        for (int i = 0; i < left; i++) System.out.print(" ");
        System.out.print(color(MenuColor.CYAN) + title + color(MenuColor.CYAN));
        for (int i = 0; i < right; i++) System.out.print(" ");
        System.out.println();
    }

    private static void printLine(int width, char leftChar, char rightChar) {
        System.out.print(leftChar);
        for (int i = 0; i < width - 2; i++) {
            System.out.print("─");
        }
        System.out.println(rightChar);
    }

    private static void printCentered(String text, int width) {
        int textLength = stripAnsi(text).length();
        int totalPadding = width - 2 - textLength;
        int left = totalPadding / 2;
        int right = totalPadding - left;

        System.out.print("│");
        for (int i = 0; i < left; i++) System.out.print(" ");
        System.out.print(text);
        for (int i = 0; i < right; i++) System.out.print(" ");
        System.out.println("│");
    }

    private static String stripAnsi(String text) {
        return text.replaceAll("\u001B\\[[;\\d]*m", "");
    }


    public static void printLoginHeader(String title) {
        int width = 55;
        printLine(width, '┌', '┐');
        printCenteredColor(title, width, MenuColor.CYAN);
        printLine(width, '└', '┘');
    }
    public static void printCenteredColor(String text, int width, String color) {
        int padding = (width - 2 - text.length()) / 2;
        String format = "│" + " ".repeat(padding) + color + "%s" + MenuColor.RESET
                + " ".repeat(width - 2 - padding - text.length()) + "│%n";
        System.out.printf(format, text);
    }

    public static void printError(String message) {
        System.out.println(color(MenuColor.RED) + " × " + message + color(MenuColor.RESET));
    }

    public static void printSuccess(String message) {
        System.out.println(color(MenuColor.GREEN) + " ✓ " + message + color(MenuColor.RESET));
    }
}

