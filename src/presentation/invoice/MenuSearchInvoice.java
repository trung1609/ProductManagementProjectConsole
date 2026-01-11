package presentation.invoice;

import business.impl.invoice.InvoiceDetailsServiceImpl;
import dao.impl.invoice.InvoiceDetailsDAOImpl;
import entity.InvoiceDetails;
import presentation.menuUtil.MenuUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class MenuSearchInvoice {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] searchInvoiceMenu = {
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
                        searchByCustomerName();
                        break;
                    case 2:
                        searchByinvoiceDate();
                        break;
                    case 3:
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


    public static void searchByCustomerName() {
        Scanner sc = new Scanner(System.in);
        InvoiceDetailsServiceImpl invoiceDetailsService = new InvoiceDetailsServiceImpl();
        System.out.print("Nhập tên khach hàng cần tìm: ");
        String customerName = sc.nextLine();
        List<InvoiceDetails> invoiceDetailsList = invoiceDetailsService.getAllInvoiceDetailsByCustomerName(customerName);
        if (invoiceDetailsList != null && !invoiceDetailsList.isEmpty()) {
            InvoiceDetails.printHeader();
            for (InvoiceDetails invoiceDetails : invoiceDetailsList) {
                invoiceDetails.printInvoiceDetailRow();
            }
            InvoiceDetails.printFooter();
            System.out.println("Tổng số: " + invoiceDetailsList.size() + " kết quả");
        } else {
            MenuUtil.printError("Không tìm thấy kết quả nào!");
        }
    }

    public static void searchByinvoiceDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Scanner sc = new Scanner(System.in);
        InvoiceDetailsServiceImpl invoiceDetailsService = new InvoiceDetailsServiceImpl();
        LocalDate invoiceDate;
        do {
            try {
                System.out.print("Nhập ngày/tháng/năm cần tìm(dd/MM/yyyy): ");
                invoiceDate = LocalDate.parse(sc.nextLine(), dtf);
                break;
            } catch (DateTimeParseException e) {
                MenuUtil.printError("Vui lòng nhập lại đúng định dạng ngày tháng năm");
            }
        } while (true);
        List<InvoiceDetails> invoiceDetailsList = invoiceDetailsService.getAllInvoiceDetailsByInvoiceDate(invoiceDate);
        if (invoiceDetailsList != null && !invoiceDetailsList.isEmpty()) {
            InvoiceDetails.printHeader();
            for (InvoiceDetails invoiceDetails : invoiceDetailsList) {
                invoiceDetails.printInvoiceDetailRow();
            }
            InvoiceDetails.printFooter();
            System.out.println("Tổng số: " + invoiceDetailsList.size() + " kết quả");
        } else {
            MenuUtil.printError("Không tìm thấy kết quả nào!");
        }
    }
}
