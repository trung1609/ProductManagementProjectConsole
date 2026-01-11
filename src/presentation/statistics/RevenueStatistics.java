package presentation.statistics;

import business.impl.statistics.StatisticsServiceImpl;
import presentation.MainMenu;
import presentation.menuUtil.MenuUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class RevenueStatistics {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] revenueMenu = {
                "Doanh thu theo ngày",
                "Doanh thu theo tháng",
                "Doanh thu theo năm",
                "Quay lại menu chính"
        };

        do {
            try {
                MenuUtil.printMenu("THỐNG KÊ DOANH THU", revenueMenu);

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        revenueByDate();
                        break;
                    case 2:
                        revenueByMonth();
                        break;
                    case 3:
                        revenueByYear();
                        break;
                    case 4:
                        MainMenu.main(args);
                        break;
                    default:
                        MenuUtil.printError("Vui lòng nhập từ 1 đến 4.");
                }
            } catch (NumberFormatException e) {
                MenuUtil.printError("Vui lòng nhập số.");
            }
        } while (true);
    }


    public static void revenueByDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        StatisticsServiceImpl statisticsService = new StatisticsServiceImpl();
        Scanner sc = new Scanner(System.in);
        LocalDate date;

        do {
            try {
                System.out.print("Nhập ngày (dd-MM-yyyy): ");
                date = LocalDate.parse(sc.nextLine(), dtf);
                break;
            } catch (DateTimeParseException e) {
                MenuUtil.printError("Vui lòng nhập đúng định dạng.");
            }
        } while (true);

        double total = statisticsService.totalRevenueByDate(date);

        StatisticsUI.printRevenueResult(
                "Doanh thu theo ngày",
                date.format(dtf),
                total
        );
        StatisticsUI.waitEnter();
    }


    public static void revenueByMonth() {
        StatisticsServiceImpl statisticsService = new StatisticsServiceImpl();
        Scanner sc = new Scanner(System.in);
        int month;
        int year;
        do {
            try {
                System.out.print("Nhập tháng: ");
                month = Integer.parseInt(sc.nextLine());
                break;
            }catch (NumberFormatException e) {
                MenuUtil.printError("Vui lòng nhập số.");
            }
        }while (true);

        do {
            try {
                System.out.print("Nhập năm: ");
                year = Integer.parseInt(sc.nextLine());
                break;
            }catch (NumberFormatException e) {
                MenuUtil.printError("Vui lòng nhập số.");
            }
        }while (true);

        double total = statisticsService.totalRevenueByMonth(month, year);

        StatisticsUI.printRevenueResult(
                "Doanh thu theo tháng",
                String.format("%02d-%d", month, year),
                total
        );
        StatisticsUI.waitEnter();
    }


    public static void revenueByYear() {
        StatisticsServiceImpl statisticsService = new StatisticsServiceImpl();
        Scanner sc = new Scanner(System.in);
        int year;
        do {
            try {
                System.out.print("Nhập năm: ");
                year = Integer.parseInt(sc.nextLine());
                break;
            }catch (NumberFormatException e) {
                MenuUtil.printError("Vui lòng nhập số.");
            }
        }while (true);

        double total = statisticsService.totalRevenueByYear(year);

        StatisticsUI.printRevenueResult(
                "Doanh thu theo năm",
                String.valueOf(year),
                total
        );
        StatisticsUI.waitEnter();
    }

}
