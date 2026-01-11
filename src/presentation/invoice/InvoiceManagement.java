package presentation.invoice;

import business.impl.customer.CustomerServiceImpl;
import business.impl.invoice.InvoiceServiceImpl;
import business.impl.product.ProductServiceImpl;
import business.interfaceService.IInvoiceService;
import dao.impl.customer.CustomerDAOImpl;
import dao.impl.product.ProductDAOImpl;
import entity.Customer;
import entity.Invoice;
import entity.Product;
import presentation.MainMenu;
import presentation.menuUtil.MenuUtil;

import java.awt.*;
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
                        MainMenu.main(args);
                        return;
                    default:
                        MenuUtil.printError("Vui lòng nhập lại lựa chọn phù hợp.");
                }
            } catch (NumberFormatException e) {
                MenuUtil.printError("Vui lòng nhập số.");
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
        MenuUtil.printListItems("DANH SÁCH KHÁCH HÀNG",50);
        CustomerServiceImpl customerService = new CustomerServiceImpl();
        List<Customer> customers = customerService.findAllCustomers();
        if (customers.isEmpty() || customers == null) {
            MenuUtil.printError("Chưa có khách hàng");
            return;
        }
        Customer customer = new Customer();
        customer.printCustomerHeader();
        for (Customer c : customers) {
            c.printCustomerRow(c);
        }
        customer.printCustomerFooter();
        System.out.print("Nhập mã khách hàng: ");
        int customerId = Integer.parseInt(sc.nextLine());

        customer = customerService.findCustomerById(customerId);
        if (customer == null) {
            MenuUtil.printError("Không tìm thấy mã khách hàng.");
            return;
        }

        ProductServiceImpl productService = new ProductServiceImpl();
        MenuUtil.printListItems("DANH SÁCH SẢN PHẨM",50);
        List<Product> productList = productService.getALlProducts();
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

            int availableStock = stockTmp.get(productId);

            if (quantity > availableStock) {
                MenuUtil.printError("Số lượng trong kho không đủ.");
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
            System.out.println("Đã thêm: " + checkProductId.getName() + " x" + quantity + " = " + subTotal);
        } while (true);

        if (productIds.isEmpty()) {
            MenuUtil.printError("Chưa chọn sản phẩm nào.");
            return;
        }

        System.out.println("Tổng tiền: " + totalAmount);
        System.out.print("Xác nhận tạo hóa đơn? (Y/N): ");
        String confirm = sc.nextLine();

        if (!confirm.equalsIgnoreCase("Y")) {
            MenuUtil.printSuccess("Đã hủy tạo hóa đơn.");
            return;
        }

        InvoiceServiceImpl invoiceService = new InvoiceServiceImpl();
        boolean result = invoiceService.createInvoice(customerId, productIds, quantities, prices);

        if (result) {
            MenuUtil.printSuccess("Tạo hóa đơn thành công!.");
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

        MenuUtil.printListItems("DANH SÁCH HÓA ĐƠN",55);
        Invoice.printHeader();
        for (Invoice invoice : invoices) {
            invoice.printInvoiceRow();
        }
        Invoice.printFooter();
        System.out.println("Tổng số hóa đơn: " + invoices.size());
    }
}
