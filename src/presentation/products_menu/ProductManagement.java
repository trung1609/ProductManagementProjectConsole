package presentation.products_menu;

import business.impl.product.ProductServiceImpl;
import entity.Product;
import entity.Role;
import exception.ExceptionHandler;
import presentation.dashboard.MenuDashboard;
import presentation.menu_by_role.AdminMenu;
import presentation.menu_by_role.StaffMenu;
import presentation.menu_util.MenuUtil;
import presentation.statistics.StatisticsUI;
import util.SessionManager;

import java.util.List;
import java.util.Scanner;

public class ProductManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] productMenu = {
                "Hiển thị danh sách sản phẩm",
                "Thêm sản phẩm mới",
                "Cập nhật thông tin sản phẩm",
                "Xóa sản phẩm theo ID",
                "Tìm kiếm theo brand",
                "Tìm kiếm theo khoảng giá",
                "Tìm kiếm theo tồn kho",
                "Quay lại menu chính"
        };

        do {
            try {
                MenuUtil.printMenu("QUẢN LÝ SẢN PHẨM", productMenu);

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        displayAllProducts();
                        break;
                    case 2:
                        addProduct(sc);
                        break;
                    case 3:
                        Role currentRoleUpdate = SessionManager.getCurrentRole();
                        if (currentRoleUpdate == Role.ADMIN) {
                            updateProduct();
                        } else {
                            MenuUtil.printError("Chức năng này chỉ dành cho ADMIN.");
                        }
                        break;
                    case 4:
                        Role currentRoleDelete = SessionManager.getCurrentRole();
                        if (currentRoleDelete == Role.ADMIN) {
                            deleteProduct(sc);
                        } else {
                            MenuUtil.printError("Chức năng này chỉ dành cho ADMIN.");
                        }
                        break;
                    case 5:
                        searchProductByBrand(sc);
                        break;
                    case 6:
                        searchProductByPrice(sc);
                        break;
                    case 7:
                        searchProductByStock(sc);
                        break;
                    case 8:
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
                        MenuUtil.printError("Vui lòng nhập lựa chọn phù hợp.");
                }
            } catch (NumberFormatException e) {
                ExceptionHandler.handleNumberFormatException();
            }
        } while (true);
    }

    public static void addProduct(Scanner sc) {
        Product product = new Product();
        product.input(sc);
        ProductServiceImpl productService = new ProductServiceImpl();
        boolean result = productService.addProduct(product);
        if (result) {
            MenuUtil.printSuccess("Thêm sản phẩm thành công");
        } else {
            MenuUtil.printError("Có lỗi khi thêm sản phẩm");
        }
    }

    public static void displayAllProducts() {
        ProductServiceImpl productService = new ProductServiceImpl();
        List<Product> productList = productService.getALlProducts();
        if (productList.isEmpty()) {
            MenuUtil.printError("Chưa có sản phẩm.");
        } else {
            Product product = new Product();
            MenuUtil.printListItems("DANH SÁCH SẢN PHẨM", 70);
            product.printProductHeader();
            for (Product p : productList) {
                p.printProductRow(p);
            }
            product.printProductFooter();
            System.out.println("Tổng số sản phẩm: " + productList.size());
            StatisticsUI.waitEnter();
        }
    }

    public static void updateProduct() {
        String[] productUpdateMenu = {
                "Cập nhật tên sản phẩm",
                "Cập nhật thương hiệu sản phẩm",
                "Cập nhật giá sản phẩm",
                "Cập nhật số lượng tồn kho",
                "Thoát"
        };
        List<Product> productList = new ProductServiceImpl().getALlProducts();
        MenuUtil.printListItems("DANH SÁCH SẢN PHẨM", 70);
        Product productDisplay = new Product();
        productDisplay.printProductHeader();
        for (Product p : productList) {
            p.printProductRow(p);
        }
        productDisplay.printProductFooter();
        ProductServiceImpl productService = new ProductServiceImpl();
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập id sản phẩm cần cập nhật: ");
        int id = Integer.parseInt(sc.nextLine());
        Product product = productService.findProductById(id);
        if (product == null) {
            MenuUtil.printError("Mã sản phẩm không tồn tại");
            return;
        }

        MenuUtil.printListItems("THÔNG TIN SẢN PHẨM CẦN CẬP NHẬT", 70);
        product.printProductHeader();
        for (int i = 0; i < 1; i++) {
            product.printProductRow(product);
        }
        product.printProductFooter();
        boolean isExist = true;
        do {

            try {
                MenuUtil.printMenu("CẬP NHẬT THÔNG TIN SẢN PHẨM", productUpdateMenu);
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        do {
                            try {
                                System.out.print("Nhập tên sản phẩm: ");
                                String newName = sc.nextLine();
                                if (newName.isEmpty()) {
                                    MenuUtil.printError("Vui lòng không bỏ trống tên sản phẩm.");
                                    continue;
                                }
                                if (ProductServiceImpl.checkProductName(newName)) {
                                    MenuUtil.printError("Tên sản phẩm đã tồn tại. Vui lòng nhập tên khác.");
                                    continue;
                                }
                                break;
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        } while (true);
                        break;
                    case 2:
                        do {
                            try {
                                System.out.print("Nhập thương hiệu sản phẩm: ");
                                String newBrand = sc.nextLine();
                                if (newBrand.isEmpty()) {
                                    MenuUtil.printError("Vui lòng không bỏ trống thương hiệu sản phẩm.");
                                    continue;
                                }
                                break;
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        } while (true);
                        break;
                    case 3:
                        do {
                            try {
                                System.out.print("Nhập giá bán: ");
                                double newPrice = Double.parseDouble(sc.nextLine());
                                if (newPrice <= 0) {
                                    MenuUtil.printError("Vui lòng nhập giá bán lớn hơn hoặc bằng 0");
                                    continue;
                                }
                                product.setPrice(newPrice);
                                break;
                            } catch (NumberFormatException e) {
                                MenuUtil.printError("Vui lòng nhập đúng định dạng giá tiền.");
                            }
                        } while (true);
                        break;
                    case 4:
                        do {
                            try {
                                System.out.print("Nhập số lượng tồn kho: ");
                                int newStock = Integer.parseInt(sc.nextLine());
                                if (newStock < 0) {
                                    MenuUtil.printError("Vui lòng nhập số lượng lớn hơn 0");
                                    continue;
                                }
                                product.setStock(newStock);
                                break;
                            } catch (NumberFormatException e) {
                                MenuUtil.printError("Vui lòng nhập đúng định dạng số lượng tồn kho.");
                            }
                        } while (true);
                        break;
                    case 5:
                        isExist = false;
                        break;
                    default:
                        MenuUtil.printError("Vui lòng nhập lựa chọn phù hợp.");
                }
            } catch (NumberFormatException e) {
                ExceptionHandler.handleNumberFormatException();
            }
        } while (isExist);

        boolean updateProductResult = productService.updateProduct(product);
        if (updateProductResult) {
            MenuUtil.printSuccess("Cập nhật thông tin sản phẩm thành công.");
        } else {
            MenuUtil.printError("Có lỗi khi cập nhật sản phẩm");
        }
    }

    public static void deleteProduct(Scanner sc) {
        ProductServiceImpl productService = new ProductServiceImpl();
        Product product = new Product();
        List<Product> productList = productService.getALlProducts();
        MenuUtil.printListItems("DANH SÁCH SẢN PHẨM", 70);
        product.printProductHeader();
        for (Product p : productList) {
            p.printProductRow(p);
        }
        product.printProductFooter();
        System.out.print("Nhập id sản phẩm cần xóa: ");
        int id = Integer.parseInt(sc.nextLine());
        Product checkProductId = productService.findProductById(id);
        if (checkProductId == null) {
            MenuUtil.printError("Mã sản phẩm không tồn tại.");
            return;
        }
        System.out.print("Bạn có chắc chắn muốn xóa sản phẩm này không (Y/N): ");
        String choice = sc.nextLine();
        if (choice.equalsIgnoreCase("N")) {
            MenuUtil.printSuccess("Đã hủy xóa sản phẩm thành công");
        } else if (choice.equalsIgnoreCase("Y")) {
            boolean deleteProductResult = productService.deleteProduct(id);
            if (deleteProductResult) {
                MenuUtil.printSuccess("Xóa sản phẩm thành công.");
            } else {
                MenuUtil.printError("Có lỗi khi xóa sản phẩm.");
            }
        }
    }

    public static void searchProductByBrand(Scanner sc) {
        ProductServiceImpl productService = new ProductServiceImpl();
        System.out.print("Nhập sản phẩm có thương hiệu cần tìm: ");
        String brand = sc.nextLine();
        List<Product> productList = productService.searchProductByBrand(brand);
        if (productList.isEmpty()) {
            MenuUtil.printError("Không tìm thấy sản phẩm có thương hiệu " + brand);
        } else {
            Product product = new Product();
            MenuUtil.printListItems("DANH SÁCH SẢN PHẨM CÓ TÊN THƯƠNG HIỆU LÀ: " + brand, 70);
            product.printProductHeader();
            for (Product p : productList) {
                p.printProductRow(p);
            }
            product.printProductFooter();
            System.out.println("Tổng số sản phẩm: " + productList.size());
            StatisticsUI.waitEnter();
        }
    }

    public static void searchProductByPrice(Scanner sc) {
        ProductServiceImpl productService = new ProductServiceImpl();
        double price_from;
        double price_to;
        System.out.println("Nhập sản phẩm có khoảng giá sản phẩm cần tìm: ");
        do {
            try {
                System.out.print("Nhập giá khởi đầu: ");
                price_from = Double.parseDouble(sc.nextLine());
                if (price_from <= 0) {
                    MenuUtil.printError("Vui lòng nhập giá lớn hơn 0.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                ExceptionHandler.handleNumberFormatException("Vui lòng nhập đúng định dạng giá bán.");
            }
        } while (true);
        do {
            try {
                System.out.print("Nhập giá kết thúc: ");
                price_to = Double.parseDouble(sc.nextLine());
                if (price_to <= 0) {
                    MenuUtil.printError("Vui lòng nhập giá lớn hơn 0.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                ExceptionHandler.handleNumberFormatException("Vui lòng nhập đúng định dạng giá bán.");
            }
        } while (true);
        List<Product> productList = productService.searchProductByPrice(price_from, price_to);
        if (productList.isEmpty()) {
            String message = String.format("Không tìm thấy sản phẩm có giá từ %,.0f đến %,.0f", price_from, price_to);
            MenuUtil.printError(message);
        } else {
            Product product = new Product();
            String message = String.format("DANH SÁCH SẢN PHẨM CÓ GIÁ TỪ %,.0f ĐẾN %,.0f LÀ: ", price_from, price_to);
            MenuUtil.printListItems(message, 73);
            product.printProductHeader();
            for (Product p : productList) {
                p.printProductRow(p);
            }
            product.printProductFooter();
            System.out.println("Tổng số sản phẩm: " + productList.size());
            StatisticsUI.waitEnter();
        }
    }

    public static void searchProductByStock(Scanner sc) {
        ProductServiceImpl productService = new ProductServiceImpl();
        System.out.print("Nhập tên sản phẩm cần tìm: ");
        String product_name = sc.nextLine();
        List<Product> productList = productService.searchProductByStock(product_name);
        if (productList.isEmpty()) {
            MenuUtil.printError("Không tìm thấy sản phẩm có tên là: " + product_name);
        } else {
            Product product = new Product();
            MenuUtil.printListItems("DANH SÁCH SẢN PHẨM TÊN LÀ: " + product_name, 70);
            product.printProductHeader();
            for (Product p : productList) {
                p.printProductRow(p);
            }
            product.printProductFooter();
            System.out.println("Tổng số sản phẩm: " + productList.size());
            StatisticsUI.waitEnter();
        }
    }
}
