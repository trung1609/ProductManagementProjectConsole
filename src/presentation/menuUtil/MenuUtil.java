package presentation.menuUtil;


public class MenuUtil {


    public static final String RESET = "\u001B[0m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String RED = "\u001B[31m";


    public static final boolean ENABLE_COLOR = true;

    private static String color(String c) {
        return ENABLE_COLOR ? c : "";
    }

    public static void printMenu(String title, String[] options) {
        int width = 50;

        printLine(width);
        printCentered(color(CYAN) + title + color(RESET), width);
        printLine(width);

        for (int i = 0; i < options.length; i++) {
            String index = color(YELLOW) + (i + 1) + "." + color(RESET);
            String text = color(GREEN) + options[i] + color(RESET);

            String content = index + " " + text;

            int paddingRight = width - stripAnsi(content).length() - 4;
            System.out.printf("│ %s%" + paddingRight + "s │%n", content, "");
        }

        printLine(width);
        System.out.print("Nhập lựa chọn: ");
    }


    private static void printLine(int width) {
        System.out.print("┌");
        for (int i = 0; i < width - 2; i++) System.out.print("─");
        System.out.println("┐");
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
        printLine(width);
        printCentered(color(CYAN) + title + color(RESET), width);
        printLine(width);
    }

    public static void printFooter() {
        int width = 55;
        printLine(width);
    }
}

