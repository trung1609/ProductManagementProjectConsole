package presentation.invoice;

import dao.impl.InvoiceDAOImpl;
import dao.impl.InvoiceDetailsDAOImpl;
import entity.InvoiceDetails;

import java.util.List;
import java.util.Scanner;

public class MenuSearchInvoice {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        do {
            try {
                System.out.println("1. Tìm kiếm theo tên khách hàng");
                System.out.println("2. Tìm kiếm theo ngày/tháng/năm");
                System.out.println("3. Quay lại menu hóa đơn");
                System.out.print("Nhập lựa chọn của bạn: ");
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        searchByCustomerName();
                        break;
                    case 2:
                        break;
                    case 3:
                        InvoiceManagement.main(args);
                        return;
                    default:
                        System.err.println("Vui lòng nhập từ 1-3");
                }
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập lựa chọn phù hợp.");
            }
        } while (true);
    }

    public static void searchByCustomerName() {
        Scanner sc = new Scanner(System.in);
        InvoiceDetailsDAOImpl invoiceDAO = new InvoiceDetailsDAOImpl();
        System.out.print("Nhập tên khach hàng cần tìm: ");
        String customerName = sc.nextLine();
        List<InvoiceDetails> invoiceDetailsList = invoiceDAO.getAllInvoiceDetailsByCustomerName(customerName);
        if (invoiceDetailsList != null && !invoiceDetailsList.isEmpty()) {
            InvoiceDetails.printHeader();
            invoiceDetailsList.forEach(System.out::println);
            InvoiceDetails.printFooter();
            System.out.println("Tổng số: " + invoiceDetailsList.size() + " kết quả");
        } else {
            System.out.println("Không tìm thấy kết quả nào!");
        }

    }
}
