package presentation.invoice;

import business.impl.customer.CustomerServiceImpl;
import business.impl.invoice.InvoiceServiceImpl;
import business.impl.product.ProductServiceImpl;
import business.interfaceService.IInvoiceService;
import entity.Customer;
import entity.Invoice;
import entity.Product;
import entity.Role;
import presentation.menu_by_role.AdminMenu;
import presentation.dashboard.MenuDashboard;
import presentation.menu_by_role.StaffMenu;
import presentation.menu_util.MenuUtil;
import presentation.statistics.StatisticsUI;
import util.SessionManager;
import exception.ExceptionHandler;

import java.util.*;
import java.util.List;

public class InvoiceManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] invoiceMenu = {
                "Hiển thị danh sách hóa đơn",
                "Thêm mới hóa đơn",
                "Tìm kiếm hóa đơn chi tiết",
                "Quay ra menu chính"
        };

        do {
            try {
                MenuUtil.printMenu("QUẢN LÝ HÓA ĐƠN", invoiceMenu);

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
                        // Quay về menu tương ứng với role
                        Role currentRole = SessionManager.getCurrentRole();
                        if (currentRole == Role.ADMIN) {
                            AdminMenu.showMenu(args);
                        } else if (currentRole == Role.STAFF) {
                            StaffMenu.showMenu(args);
                        } else {
                            MenuUtil.printError("Phiên đăng nhập đã hết hạn!");
                            MenuDashboard.main(args);
                        }
                        return;
                    default:
                        MenuUtil.printError("Vui lòng nhập lại lựa chọn phù hợp.");
                }
            } catch (NumberFormatException e) {
                ExceptionHandler.handleNumberFormatException();
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
        MenuUtil.printListItems("DANH SÁCH KHÁCH HÀNG", 95);
        CustomerServiceImpl customerService = new CustomerServiceImpl();
        List<Customer> customers = customerService.findAllCustomers();
        if (customers == null || customers.isEmpty()) {
            MenuUtil.printError("Chưa có khách hàng");
            return;
        }
        Customer.printCustomerHeader();
        for (Customer c : customers) {
            c.printCustomerRow(c);
        }
        Customer.printCustomerFooter();

        System.out.print("Nhập mã khách hàng: ");
        int customerId = Integer.parseInt(sc.nextLine());
        Customer customer = customerService.findCustomerById(customerId);
        if (customer == null) {
            MenuUtil.printError("Không tìm thấy mã khách hàng.");
            return;
        }

        ProductServiceImpl productService = new ProductServiceImpl();
        MenuUtil.printListItems("DANH SÁCH SẢN PHẨM", 70);
        List<Product> productList = productService.getProductInStock();
        if (productList == null || productList.isEmpty()) {
            MenuUtil.printError("Không có sản phẩm nào!");
            return;
        }

        Product p_print = new Product();
        p_print.printProductHeader();
        for (Product p : productList) {
            p.printProductRow(p);
        }
        p_print.printProductFooter();

        //Tạo stock tạm -> check stock ngay khi add
        Map<Integer, Integer> stockTmp = new HashMap<>();
        for (Product product : productList) {
            stockTmp.put(product.getId(), product.getStock());
        }

        List<Integer> productIds = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();
        List<Double> prices = new ArrayList<>();
        double totalAmount = 0;

        do {
            System.out.print("Chọn id sản phẩm cần thêm hoặc nhập 0 để dừng chọn: ");
            int productId = Integer.parseInt(sc.nextLine());
            if (productId == 0) {
                break;
            }

            Product checkProductId = productService.findProductById(productId);
            if (checkProductId == null) {
                MenuUtil.printError("Không tìm thấy id sản phẩm");
                continue;
            }

            System.out.print("Nhập số lượng: ");
            int quantity = Integer.parseInt(sc.nextLine());
            if (quantity <= 0) {
                MenuUtil.printError("Số lượng phải lớn hơn 0.");
                continue;
            }

            int availableStock = stockTmp.get(productId);

            if (quantity > availableStock) {
                MenuUtil.printError("Số lượng trong kho không đủ. Còn lại: " + availableStock);
                continue;
            }

            stockTmp.put(productId, availableStock - quantity);

            boolean isExist = false;
            for (int i = 0; i < productIds.size(); i++) {
                if (productIds.get(i) == productId) {
                    quantities.set(i, quantities.get(i) + quantity);
                    isExist = true;
                    break;
                }
            }

            if (!isExist) {
                productIds.add(productId);
                quantities.add(quantity);
                prices.add(checkProductId.getPrice());
            }
            double subTotal = quantity * checkProductId.getPrice();
            totalAmount += subTotal;
            System.out.println("✓ Đã thêm: " + checkProductId.getName() + " x" + quantity + " = " + String.format("%,.0f", subTotal) + " VNĐ");
        } while (true);
        if (productIds.isEmpty()) {
            MenuUtil.printError("Chưa chọn sản phẩm nào.");
            return;
        }

        System.out.println("=".repeat(50));
        System.out.println("TỔNG TIỀN: " + String.format("%,.0f", totalAmount) + " VNĐ");
        System.out.println("=".repeat(50));
        System.out.print("Xác nhận tạo hóa đơn? (Y/N): ");
        String confirm = sc.nextLine();

        if (!confirm.equalsIgnoreCase("Y")) {
            MenuUtil.printSuccess("Đã hủy tạo hóa đơn.");
            return;
        }

        InvoiceServiceImpl invoiceService = new InvoiceServiceImpl();
        boolean result = invoiceService.createInvoice(customerId, productIds, quantities, prices);

        if (result) {
            MenuUtil.printSuccess("Tạo hóa đơn thành công!");
        } else {
            MenuUtil.printError("Tạo hóa đơn thất bại!");
        }
    }

    public static void getAllInvoices() {
        IInvoiceService invoiceService = new InvoiceServiceImpl();
        List<Invoice> invoices = invoiceService.getAllInvoices();
        if (invoices == null || invoices.isEmpty()) {
            MenuUtil.printError("Không có hóa đơn nào trong hệ thống.");
            return;
        }

        MenuUtil.printListItems("DANH SÁCH HÓA ĐƠN", 75);
        Invoice.printHeader();
        for (Invoice invoice : invoices) {
            invoice.printInvoiceRow();
        }
        Invoice.printFooter();
        System.out.println("Tổng số hóa đơn: " + invoices.size());
        StatisticsUI.waitEnter();
    }
}
