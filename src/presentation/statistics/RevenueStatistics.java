package presentation.statistics;

import business.impl.statistics.StatisticsServiceImpl;
import dto.CustomerStatistics;
import entity.Invoice;
import dto.ProductStatistics;
import entity.Role;
import exception.ExceptionHandler;
import presentation.dashboard.MenuDashboard;
import presentation.formatter.CustomerStatisticsFormatter;
import presentation.formatter.ProductStatisticsFormatter;
import presentation.menu_by_role.AdminMenu;
import presentation.menu_util.MenuUtil;
import util.SessionManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class RevenueStatistics {
    private static final StatisticsServiceImpl statisticsService = new StatisticsServiceImpl();
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] revenueMenu = {
                "Doanh thu theo ngày",
                "Doanh thu theo tháng",
                "Doanh thu theo năm",
                "Top sản phẩm bán chạy",
                "Top khách hàng thân thiết",
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
                        topSellingProducts30Days();
                        break;
                    case 5:
                        topCustomerVIP();
                        break;
                    case 6:
                        // Quay về menu Admin (chỉ Admin mới vào được RevenueStatistics)
                        Role currentRole = SessionManager.getCurrentRole();
                        if (currentRole == Role.ADMIN) {
                            AdminMenu.showMenu(args);
                        } else {
                            MenuUtil.printError("Phiên đăng nhập đã hết hạn!");
                            MenuDashboard.main(args);
                        }
                        return;
                    default:
                        MenuUtil.printError("Vui lòng nhập từ 1 đến 6.");
                }
            } catch (NumberFormatException e) {
                ExceptionHandler.handleNumberFormatException();
            }
        } while (true);
    }


    public static void revenueByDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Scanner sc = new Scanner(System.in);
        LocalDate date;
        String[] revenueMenu = {
                "Nhập ngày để tính doanh thu",
                "Doanh thu của tất cả các ngày",
                "Quay lại"
        };
        do {
            try {
                MenuUtil.printMenu("DOANH THU THEO NGÀY", revenueMenu);
                int option = Integer.parseInt(sc.nextLine());
                switch (option) {
                    case 1:
                        do {
                            try {
                                System.out.print("Nhập ngày (dd-MM-yyyy): ");
                                date = LocalDate.parse(sc.nextLine(), dtf);
                                break;
                            } catch (DateTimeParseException e) {
                                ExceptionHandler.handleDateTimeParseException();
                            }
                        } while (true);

                        double total = statisticsService.totalRevenueByDate(date);

                        StatisticsUI.printRevenueResult(
                                "Doanh thu theo ngày",
                                date.format(dtf),
                                total
                        );
                        StatisticsUI.waitEnter();
                        break;
                    case 2:
                        List<Invoice> totalRevenueEachDate = statisticsService.totalRevenueEachDate();
                        StatisticsUI.printRevenueEachDateResult(
                                "Doanh thu của tất cả các ngày",
                                totalRevenueEachDate,
                                dtf
                        );
                        StatisticsUI.waitEnter();
                        break;
                    case 3:
                        return;
                    default:
                        MenuUtil.printError("Vui lòng nhập từ 1 đến 3.");
                }
            } catch (NumberFormatException e) {
                ExceptionHandler.handleNumberFormatException();
            }
        } while (true);
    }


    public static void revenueByMonth() {
        Scanner sc = new Scanner(System.in);
        int month;
        int year;
        String[] revenueMenu = {
                "Nhập tháng và năm để tính doanh thu",
                "Liệt kê doanh thu theo tất cả các tháng",
                "Quay lại"
        };
        do {
            try {
                MenuUtil.printMenu("DOANH THU THEO THÁNG", revenueMenu);
                int option = Integer.parseInt(sc.nextLine());
                switch (option) {
                    case 1:
                        do {
                            try {
                                System.out.print("Nhập tháng: ");
                                month = Integer.parseInt(sc.nextLine());
                                if (month < 1 || month > 12) {
                                    MenuUtil.printError("Vui lòng nhập tháng từ 1 đến 12.");
                                    continue;
                                }
                                break;
                            } catch (NumberFormatException e) {
                                ExceptionHandler.handleNumberFormatException();
                            }
                        } while (true);

                        do {
                            try {
                                System.out.print("Nhập năm: ");
                                year = Integer.parseInt(sc.nextLine());
                                if (year < 1 || year > 2027) {
                                    MenuUtil.printError("Vui lòng nhập năm hợp lệ.");
                                    continue;
                                }
                                break;
                            } catch (NumberFormatException e) {
                                ExceptionHandler.handleNumberFormatException();
                            }
                        } while (true);

                        double total = statisticsService.totalRevenueByMonth(month, year);

                        StatisticsUI.printRevenueResult(
                                "Doanh thu theo tháng",
                                String.format("%02d-%d", month, year),
                                total
                        );
                        StatisticsUI.waitEnter();
                        break;
                    case 2:
                        List<Invoice> monthlyList = statisticsService.totalRevenueEachMonth();
                        StatisticsUI.printRevenueEachMonthResult("Doanh thu theo từng tháng", monthlyList);
                        StatisticsUI.waitEnter();
                        break;
                    case 3:
                        return;
                    default:
                        MenuUtil.printError("Vui lòng nhập từ 1 đến 3.");
                }
            } catch (NumberFormatException e) {
                ExceptionHandler.handleNumberFormatException();
            }
        } while (true);
    }


    public static void revenueByYear() {
        Scanner sc = new Scanner(System.in);
        int year;
        String[] revenueMenu = {
                "Nhập năm để tính doanh thu",
                "Liệt kê doanh thu theo tất cả các năm",
                "Quay lại"
        };
        do {
            try {
                MenuUtil.printMenu("DOANH THU THEO NĂM", revenueMenu);
                int option = Integer.parseInt(sc.nextLine());
                switch (option) {
                    case 1:
                        do {
                            try {
                                System.out.print("Nhập năm: ");
                                year = Integer.parseInt(sc.nextLine());
                                if (year < 1 || year > 2027) {
                                    MenuUtil.printError("Vui lòng nhập năm hợp lệ.");
                                    continue;
                                }
                                break;
                            } catch (NumberFormatException e) {
                                ExceptionHandler.handleNumberFormatException();
                            }
                        } while (true);

                        double total = statisticsService.totalRevenueByYear(year);

                        StatisticsUI.printRevenueResult(
                                "Doanh thu theo năm",
                                String.valueOf(year),
                                total
                        );
                        StatisticsUI.waitEnter();
                        break;
                    case 2:
                        List<Invoice> yearlyList = statisticsService.totalRevenueEachYear();
                        StatisticsUI.printRevenueEachYearResult("Doanh thu theo từng năm", yearlyList);
                        StatisticsUI.waitEnter();
                        break;
                    case 3:
                        return;
                    default:
                        MenuUtil.printError("Vui lòng nhập từ 1 đến 3.");
                }
            } catch (NumberFormatException e) {
                ExceptionHandler.handleNumberFormatException();
            }
        } while (true);
    }

    public static void topSellingProducts30Days() {
        Scanner sc = new Scanner(System.in);
        int limit;
        do {
            try {
                System.out.print("Nhập số lượng sản phẩm bán chạy muốn xem: ");
                limit = Integer.parseInt(sc.nextLine());
                if (limit <= 0) {
                    MenuUtil.printError("Vui lòng nhập số lớn hơn 0.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                ExceptionHandler.handleNumberFormatException();
            }
        } while (true);
        List<ProductStatistics> productStatistics = statisticsService.getTopSellingProducts(limit);
        ProductStatisticsFormatter.printHeader();
        for (ProductStatistics ps : productStatistics) {
            ProductStatisticsFormatter.printRow(ps);
        }
        ProductStatisticsFormatter.printFooter();
        System.out.println("Tổng số sản phẩm bán chạy được hiển thị: " + productStatistics.size());
        StatisticsUI.waitEnter();
    }

    public static void topCustomerVIP() {
        Scanner sc = new Scanner(System.in);
        int limit;
        do {
            try {
                System.out.print("Nhập số lượng khách hàng VIP muốn xem: ");
                limit = Integer.parseInt(sc.nextLine());
                if (limit <= 0) {
                    MenuUtil.printError("Vui lòng nhập số lớn hơn 0.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                ExceptionHandler.handleNumberFormatException();
            }
        } while (true);
        List<CustomerStatistics> customerStatistics = statisticsService.getTopCustomers(limit);
        CustomerStatisticsFormatter.printHeader();
        for (CustomerStatistics cs : customerStatistics) {
            CustomerStatisticsFormatter.printRow(cs);
        }
        CustomerStatisticsFormatter.printFooter();
        System.out.println("Tổng số sản phẩm bán chạy được hiển thị: " + customerStatistics.size());
        StatisticsUI.waitEnter();
    }

}
