package presentation.invoice;

import business.impl.invoice.InvoiceDetailsServiceImpl;
import entity.InvoiceDetails;
import presentation.menu_util.MenuUtil;
import presentation.statistics.StatisticsUI;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class MenuSearchInvoice {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] searchInvoiceMenu = {
                "Xem chi tiết hóa đơn",
                "Tìm kiếm theo tên khách hàng",
                "Tìm kiếm theo ngày / tháng / năm",
                "Quay lại menu hóa đơn"
        };

        do {
            try {
                MenuUtil.printMenu("TÌM KIẾM HÓA ĐƠN", searchInvoiceMenu);

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        getAllInvoiceDetailsByInvoiceId();
                        break;
                    case 2:
                        searchByCustomerName();
                        break;
                    case 3:
                        searchByinvoiceDate();
                        break;
                    case 4:
                        InvoiceManagement.main(args);
                        break;
                    default:
                        MenuUtil.printError("Vui lòng nhập từ 1 đến 3.");
                }
            } catch (NumberFormatException e) {
                MenuUtil.printError("Vui lòng nhập số.");
            }
        } while (true);
    }

    public static void getAllInvoiceDetailsByInvoiceId(){
        Scanner sc = new Scanner(System.in);
        InvoiceDetailsServiceImpl invoiceDetailsService = new InvoiceDetailsServiceImpl();
        System.out.print("Nhập mã hóa đơn cần xem chi tiết: ");
        int invoiceId = Integer.parseInt(sc.nextLine());
        List<InvoiceDetails> invoiceDetailsList = invoiceDetailsService.getAllInvoiceDetailsByInvoiceId(invoiceId);
        if (invoiceDetailsList != null && !invoiceDetailsList.isEmpty()) {
            MenuUtil.printListItems("DANH SÁCH HÓA ĐƠN CHI TIẾT CỦA MÃ HÓA ĐƠN: " + invoiceId, 90);
            InvoiceDetails.printHeader();
            for (InvoiceDetails invoiceDetails : invoiceDetailsList) {
                invoiceDetails.printInvoiceDetailRow();
            }
            InvoiceDetails.printFooter();
            System.out.println("Tổng số: " + invoiceDetailsList.size() + " kết quả");
            StatisticsUI.waitEnter();
        } else {
            MenuUtil.printError("Không tìm thấy kết quả nào!");
        }
    }


    public static void searchByCustomerName() {
        Scanner sc = new Scanner(System.in);
        InvoiceDetailsServiceImpl invoiceDetailsService = new InvoiceDetailsServiceImpl();
        System.out.print("Nhập tên khách hàng cần tìm: ");
        String customerName = sc.nextLine();
        List<InvoiceDetails> invoiceDetailsList = invoiceDetailsService.getAllInvoiceDetailsByCustomerName(customerName);
        if (invoiceDetailsList != null && !invoiceDetailsList.isEmpty()) {
            MenuUtil.printListItems("DANH SÁCH HÓA ĐƠN CHI TIẾT CỦA KHÁCH HÀNG CÓ TÊN: " + customerName, 90);
            InvoiceDetails.printHeader();
            for (InvoiceDetails invoiceDetails : invoiceDetailsList) {
                invoiceDetails.printInvoiceDetailRow();
            }
            InvoiceDetails.printFooter();
            System.out.println("Tổng số: " + invoiceDetailsList.size() + " kết quả");
            StatisticsUI.waitEnter();
        } else {
            MenuUtil.printError("Không tìm thấy kết quả nào!");
        }
    }

    public static void searchByinvoiceDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Scanner sc = new Scanner(System.in);
        InvoiceDetailsServiceImpl invoiceDetailsService = new InvoiceDetailsServiceImpl();
        LocalDate invoiceDate;
        do {
            try {
                System.out.print("Nhập ngày/tháng/năm cần tìm(dd-MM-yyyy): ");
                invoiceDate = LocalDate.parse(sc.nextLine(), dtf);
                break;
            } catch (DateTimeParseException e) {
                MenuUtil.printError("Vui lòng nhập lại đúng định dạng ngày tháng năm");
            }
        } while (true);
        List<InvoiceDetails> invoiceDetailsList = invoiceDetailsService.getAllInvoiceDetailsByInvoiceDate(invoiceDate);
        if (invoiceDetailsList != null && !invoiceDetailsList.isEmpty()) {
            MenuUtil.printListItems("DANH SÁCH HÓA ĐƠN CHI TIẾT NGÀY " + invoiceDate.format(dtf), 90);

            InvoiceDetails.printHeader();
            for (InvoiceDetails invoiceDetails : invoiceDetailsList) {
                invoiceDetails.printInvoiceDetailRow();
            }
            InvoiceDetails.printFooter();
            System.out.println("Tổng số: " + invoiceDetailsList.size() + " kết quả");
            StatisticsUI.waitEnter();
        } else {
            MenuUtil.printError("Không tìm thấy kết quả nào!");
        }
    }
}
