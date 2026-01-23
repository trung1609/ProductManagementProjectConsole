package presentation.invoice;

import business.impl.invoice.InvoiceDetailsServiceImpl;
import entity.InvoiceDetails;
import presentation.menu_util.MenuColor;
import presentation.menu_util.MenuUtil;
import presentation.statistics.StatisticsUI;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

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

    public static void getAllInvoiceDetailsByInvoiceId() {
        Scanner sc = new Scanner(System.in);
        InvoiceDetailsServiceImpl invoiceDetailsService = new InvoiceDetailsServiceImpl();
        System.out.print("Nhập mã hóa đơn cần xem chi tiết: ");
        int invoiceId = Integer.parseInt(sc.nextLine());
        List<InvoiceDetails> invoiceDetailsList = invoiceDetailsService.getAllInvoiceDetailsByInvoiceId(invoiceId);
        if (invoiceDetailsList != null && !invoiceDetailsList.isEmpty()) {
            MenuUtil.printListItems("DANH SÁCH HÓA ĐƠN CHI TIẾT CỦA MÃ HÓA ĐƠN: " + invoiceId, 120);
            InvoiceDetails.printHeader();
            double totalAmount = 0;
            for (InvoiceDetails invoiceDetails : invoiceDetailsList) {
                invoiceDetails.printInvoiceDetailRow();
                totalAmount += invoiceDetails.getQuantity() * invoiceDetails.getPrice();
            }
            InvoiceDetails.printFooter();
            InvoiceDetails.printTotalAmount(totalAmount);
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
            MenuUtil.printListItems("KẾT QUẢ TÌM KIẾM HÓA ĐƠN CỦA KHÁCH HÀNG: " + customerName, 120);

            Map<Integer, List<InvoiceDetails>> groupedByInvoice = new LinkedHashMap<>(); // sử dụng linkedhashmap để giữ thứ tự insert
            for (InvoiceDetails invoiceDetails : invoiceDetailsList) {
                groupedByInvoice.computeIfAbsent(invoiceDetails.getInvoice_id(), k -> new ArrayList<>()).add(invoiceDetails); //kiem tra xem key đã tồn tại chưa, nếu chưa thì tạo mới
            }

            double totalAmount = 0;
            int invoiceCount = 0;
            // Hiển thị từng hóa đơn riêng biệt
            for (Map.Entry<Integer, List<InvoiceDetails>> entry : groupedByInvoice.entrySet()) {
                invoiceCount++;
                int invoiceId = entry.getKey();
                List<InvoiceDetails> invoiceDetails = entry.getValue();

                InvoiceDetails firstDetail = invoiceDetails.getFirst(); // Lấy 1 dòng đại diện vì mọi dòng trong cùng hóa đơn đều thuộc cùng 1 khách hàng

                System.out.println("\n" + MenuColor.CYAN + "═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════" + MenuColor.RESET);
                System.out.println(MenuColor.YELLOW + "  HÓA ĐƠN #" + invoiceId + " - Khách hàng: " + firstDetail.getCustomerName() + " (ID: " + firstDetail.getCustomerId() + ")" + MenuColor.RESET);
                System.out.println(MenuColor.CYAN + "═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════" + MenuColor.RESET);

                InvoiceDetails.printHeader();
                double invoiceTotal = 0;
                for (InvoiceDetails detail : invoiceDetails) {
                    detail.printInvoiceDetailRow();
                    invoiceTotal += detail.getQuantity() * detail.getPrice();
                }
                InvoiceDetails.printFooter();
                InvoiceDetails.printTotalAmount(invoiceTotal);

                totalAmount += invoiceTotal;
            }

            // Hiển thị tổng kết
            System.out.println("\n" + MenuColor.GREEN + "═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════" + MenuColor.RESET);
            System.out.println(MenuColor.YELLOW + "  TỔNG KẾT: Tìm thấy " + invoiceCount + " hóa đơn với tổng cộng " + invoiceDetailsList.size() + " sản phẩm" + MenuColor.RESET);
            System.out.println(MenuColor.GREEN + "  TỔNG TIỀN TẤT CẢ HÓA ĐƠN: " + MenuColor.RESET + MenuColor.YELLOW + String.format("%,.0f VNĐ", totalAmount) + MenuColor.RESET);
            System.out.println(MenuColor.GREEN + "═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════" + MenuColor.RESET);

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
            MenuUtil.printListItems("KẾT QUẢ TÌM KIẾM HÓA ĐƠN NGÀY: " + invoiceDate.format(dtf), 120);

            Map<Integer, List<InvoiceDetails>> groupedByInvoice = new LinkedHashMap<>(); // sử dụng linkedhashmap để giữ thứ tự insert
            for (InvoiceDetails invoiceDetails : invoiceDetailsList) {
                groupedByInvoice.computeIfAbsent(invoiceDetails.getInvoice_id(), k -> new ArrayList<>()).add(invoiceDetails); //kiem tra xem key đã tồn tại chưa, nếu chưa thì tạo mới
            }

            double totalAmount = 0;
            int invoiceCount = 0;
            // Hiển thị từng hóa đơn riêng biệt
            for (Map.Entry<Integer, List<InvoiceDetails>> entry : groupedByInvoice.entrySet()) {
                invoiceCount++;
                int invoiceId = entry.getKey();
                List<InvoiceDetails> invoiceDetails = entry.getValue();

                InvoiceDetails firstDetail = invoiceDetails.getFirst(); // Lấy 1 dòng đại diện vì mọi dòng trong cùng hóa đơn đều thuộc cùng 1 khách hàng

                System.out.println("\n" + MenuColor.CYAN + "═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════" + MenuColor.RESET);
                System.out.println(MenuColor.YELLOW + "  HÓA ĐƠN #" + invoiceId + " - Khách hàng: " + firstDetail.getCustomerName() + " (ID: " + firstDetail.getCustomerId() + ")" + MenuColor.RESET);
                System.out.println(MenuColor.CYAN + "═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════" + MenuColor.RESET);

                InvoiceDetails.printHeader();
                double invoiceTotal = 0;
                for (InvoiceDetails detail : invoiceDetails) {
                    detail.printInvoiceDetailRow();
                    invoiceTotal += detail.getQuantity() * detail.getPrice();
                }
                InvoiceDetails.printFooter();
                InvoiceDetails.printTotalAmount(invoiceTotal);

                totalAmount += invoiceTotal;
            }

            // Hiển thị tổng kết
            System.out.println("\n" + MenuColor.GREEN + "═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════" + MenuColor.RESET);
            System.out.println(MenuColor.YELLOW + "  TỔNG KẾT: Tìm thấy " + invoiceCount + " hóa đơn với tổng cộng " + invoiceDetailsList.size() + " sản phẩm" + MenuColor.RESET);
            System.out.println(MenuColor.GREEN + "  TỔNG TIỀN TẤT CẢ HÓA ĐƠN: " + MenuColor.RESET + MenuColor.YELLOW + String.format("%,.0f VNĐ", totalAmount) + MenuColor.RESET);
            System.out.println(MenuColor.GREEN + "═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════" + MenuColor.RESET);

            StatisticsUI.waitEnter();
        } else {
            MenuUtil.printError("Không tìm thấy kết quả nào!");
        }
    }
}
