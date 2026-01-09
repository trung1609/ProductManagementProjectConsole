package presentation.invoice;

import business.IInvoiceService;
import business.impl.CustomerServiceImpl;
import business.impl.InvoiceServiceImpl;
import business.impl.ProductServiceImpl;
import dao.impl.ProductDAOImpl;
import entity.Customer;
import entity.Invoice;
import entity.Product;
import presentation.MainMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InvoiceManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        do {
            try {
                System.out.println("============ QUẢN LÝ HÓA ĐƠN ============");
                System.out.println("1. Hiển thị danh sách hóa đơn");
                System.out.println("2. Thêm mới hóa đơn");
                System.out.println("3. Tìm kiếm hóa đơn chi tiết");
                System.out.println("4. Quay ra menu chính");
                System.out.println("=========================================");
                System.out.print("Nhập lựa chọn: ");
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        getAllInvoices();
                        break;
                    case 2:
                        addInvoice(sc);
                        break;
                    case 3:
                        MenuSearchInvoice.main(args);
                        break;
                    case 4:
                        MainMenu.main(args);
                        return;
                    default:
                        System.err.println("Vui lòng nhập lại lựa chọn phù hợp.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập lại lựa chọn.");
            }
        } while (true);
    }

    /*
     * Nhập mã khách hàng
     * Kiểm tra xem mã khách hàng có tồn tại hay ko
     * Hiển thị danh sách sản phẩm
     * Cho người dùng nhập vào id sản phẩm đến khi naò nhập 0 thì dừng chọn
     * Lưu sản phẩm người dùng chọn vào 1 list product
     * Tạo các list quantity và price để lấy ra vị trí của mỗi sản phẩm có số lượng và giá ra sao
     * Tính tổng tiền
     * Thêm vào hóa đơn
     * */

    public static void addInvoice(Scanner sc) {
        System.out.print("Nhập mã khách hàng: ");
        int customerId = Integer.parseInt(sc.nextLine());

        CustomerServiceImpl customerService = new CustomerServiceImpl();
        Customer customer = customerService.findCustomerById(customerId);
        if (customer == null) {
            System.err.println("Không tìm thấy mã khách hàng.");
            return;
        }

        ProductDAOImpl productDAO = new ProductDAOImpl();
        List<Product> productList = productDAO.displayAllProducts();
        if (productList == null || productList.isEmpty()) {
            System.err.println("Không có sản phẩm nào!");
            return;
        }
        productList.forEach(System.out::println);
        List<Integer> productIds = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();
        List<Double> prices = new ArrayList<>();
        double totalAmount = 0;
        ProductServiceImpl productService = new ProductServiceImpl();
        do {
            System.out.print("Chọn id sản phẩm cần thêm hoặc nhập 0 để dừng chọn: ");
            int productId = Integer.parseInt(sc.nextLine());
            if (productId == 0) {
                break;
            }
            Product checkProductId = productService.findProductById(productId);
            if(checkProductId == null) {
                System.err.println("Không tìm thấy id sản phẩm");
                continue;
            }
            System.out.print("Nhập số lượng: ");
            int quantity = Integer.parseInt(sc.nextLine());

            if (quantity > checkProductId.getStock()) {
                System.out.println("Số lượng trong kho không đủ.");
                continue;
            }

            productIds.add(productId);
            quantities.add(quantity);
            prices.add(checkProductId.getPrice());
            double subTotal = quantity * checkProductId.getPrice();
            totalAmount += subTotal;
            System.out.println("Đã thêm: " + checkProductId.getName() + " x" + quantity + " = " + subTotal);
        } while (true);

        if (productIds.isEmpty()) {
            System.err.println("Chưa chọn sản phẩm nào.");
            return;
        }

        System.out.println("Tổng tiền: " + totalAmount);
        System.out.print("Xác nhận tạo hóa đơn? (Y/N): ");
        String confirm = sc.nextLine();

        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("Đã hủy tạo hóa đơn.");
            return;
        }

        InvoiceServiceImpl invoiceService = new InvoiceServiceImpl();
        boolean result = invoiceService.createInvoice(customerId, productIds, quantities, prices);

        if (result) {
            System.out.println("Tạo hóa đơn thành công!");
        } else {
            System.err.println("Tạo hóa đơn thất bại!");
        }
    }

    public static void getAllInvoices() {
        IInvoiceService invoiceService = new InvoiceServiceImpl();
        List<Invoice> invoices = invoiceService.getAllInvoices();

        if (invoices == null || invoices.isEmpty()) {
            System.out.println("Không có hóa đơn nào trong hệ thống.");
            return;
        }

        System.out.println("\n========== DANH SÁCH HÓA ĐƠN ==========");
        Invoice.printHeader();
        invoices.forEach(System.out::println);
        Invoice.printFooter();
        System.out.println("Tổng số hóa đơn: " + invoices.size());
    }
}
