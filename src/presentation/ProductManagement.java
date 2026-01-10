package presentation;

import business.impl.product.ProductServiceImpl;
import dao.impl.product.ProductDAOImpl;
import entity.Product;
import presentation.menuUtil.MenuUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ProductManagement {
    static void main(String[] args) {
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
                        updateProduct();
                        break;
                    case 4:
                        deleteProduct(sc);
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
                        MainMenu.main(args);
                        return;
                    default:
                        System.err.println("Vui lòng nhập lựa chọn phù hợp.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số.");
            }
        } while (true);
    }

    public static void addProduct(Scanner sc) {
        Product product = new Product();
        product.input(sc);
        ProductServiceImpl productService = new ProductServiceImpl();
        boolean result = productService.addProduct(product);
        if (result) {
            System.out.println("Thêm sản phẩm thành công");
        } else {
            System.err.println("Có lỗi khi thêm sản phẩm");
        }
    }

    public static void displayAllProducts() {
        ProductServiceImpl productService = new ProductServiceImpl();
        List<Product> productList = productService.getALlProducts();
        if (productList.isEmpty()) {
            System.out.println("Chưa có sản phẩm.");
        } else {
            Product product = new Product();
            product.printProductHeader();
            for (Product p : productList) {
                p.printProductRow(p);
            }
            product.printProductFooter();
            System.out.println("Tổng số sản phẩm: " + productList.size());
        }
    }

    public static void updateProduct() {
        ProductServiceImpl productService = new ProductServiceImpl();
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập id sản phẩm cần cập nhật: ");
        int id = Integer.parseInt(sc.nextLine());
        Product product = productService.findProductById(id);
        if (product == null) {
            System.err.println("Mã sản phẩm không tồn tại");
            return;
        }
        boolean isExist = true;
        do {
            System.out.println("1. Cập nhật tên sản phẩm");
            System.out.println("2. Cập nhật thương hiệu sản phẩm");
            System.out.println("3. Cập nhật giá sản phẩm");
            System.out.println("4. Cập nhật số lượng tồn kho");
            System.out.println("5. Thoát");
            System.out.print("Nhập lựa chọn: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    do {
                        try {
                            System.out.print("Nhập tên sản phẩm mới: ");
                            product.setName(sc.nextLine());
                            break;
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    } while (true);
                    break;
                case 2:
                    do {
                        try {
                            System.out.print("Nhập thương hiệu mới của sản phẩm: ");
                            product.setBrand(sc.nextLine());
                            break;
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    } while (true);
                    break;
                case 3:
                    do {
                        try {
                            System.out.print("Nhập gia bán: ");
                            double newPrice = Double.parseDouble(sc.nextLine());
                            if (newPrice <= 0) {
                                System.err.println("Vui lòng nhập giá bán lớn hơn hoặc bằng 0");
                                continue;
                            }
                            product.setPrice(newPrice);
                            break;
                        } catch (NumberFormatException e) {
                            System.err.println("Vui lòng nhập đúng định dạng giá tiền.");
                        }
                    } while (true);
                    break;
                case 4:
                    do {
                        try {
                            System.out.print("Nhập số lượng tồn kho: ");
                            int newStock = Integer.parseInt(sc.nextLine());
                            if (newStock < 0) {
                                System.err.println("Vui lòng nhập số lượng lớn hơn 0");
                                continue;
                            }
                            product.setStock(newStock);
                            break;
                        } catch (NumberFormatException e) {
                            System.err.println("Vui lòng nhập đúng định dạng số lượng tồn kho.");
                        }
                    } while (true);
                    break;
                case 5:
                    isExist = false;
                    break;
            }
        } while (isExist);

        boolean updateProductResult = productService.updateProduct(product);
        if (updateProductResult) {
            System.out.println("Cập nhật thông tin sản phẩm thành công.");
        } else {
            System.err.println("Có lỗi khi cập nhật sản phẩm");
        }
    }

    public static void deleteProduct(Scanner sc) {
        ProductServiceImpl productService = new ProductServiceImpl();
        System.out.print("Nhập id sản phẩm cần xóa: ");
        int id = Integer.parseInt(sc.nextLine());
        Product checkProductId = productService.findProductById(id);
        if (checkProductId == null) {
            System.err.println("Mã sản phẩm không tồn tại.");
            return;
        }
        System.out.print("Bạn có chắc chắn muốn xóa sản phẩm này không (Y/N): ");
        String choice = sc.nextLine();
        if (choice.equalsIgnoreCase("N")) {
            System.out.println("Đã hủy xóa sản phẩm thành công");
        } else if (choice.equalsIgnoreCase("Y")) {
            productService.deleteProduct(id);
            System.out.println("Xóa sản phẩm thành công");
        }
    }

    public static void searchProductByBrand(Scanner sc) {
        ProductServiceImpl productService = new ProductServiceImpl();
        System.out.print("Nhập sản phẩm có thương hiệu cần tìm: ");
        String brand = sc.nextLine();
        List<Product> productList = productService.searchProductByBrand(brand);
        if (productList.isEmpty()) {
            System.out.println("Không tìm thấy sản phẩm có thương hiệu " + brand);
        } else {
            productList.forEach(System.out::println);
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
                    System.err.println("Vui lòng nhập giá lớn hơn 0.");
                }
                break;
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập đúng định dạng giá bán.");
            }
        } while (true);
        do {
            try {
                System.out.print("Nhập giá kết thúc: ");
                price_to = Double.parseDouble(sc.nextLine());
                if (price_to <= 0) {
                    System.err.println("Vui lòng nhập giá lớn hơn 0.");
                }
                break;
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập đúng định dạng giá bán.");
            }
        } while (true);
        List<Product> productList = productService.searchProductByPrice(price_from, price_to);
        if (productList.isEmpty()) {
            System.out.printf("Không tìm thấy sản phẩm có giá từ %.2f đến %.2f\n", price_from, price_to);
        } else {
            productList.stream().sorted(Comparator.comparing(Product::getPrice)).forEach(System.out::println);
        }
    }

    public static void searchProductByStock(Scanner sc) {
        ProductServiceImpl productService = new ProductServiceImpl();
        System.out.print("Nhập sản phẩm có số lượng tồn kho cần tìm: ");
        int stock = Integer.parseInt(sc.nextLine());
        List<Product> productList = productService.searchProductByStock(stock);
        if (productList.isEmpty()) {
            System.out.println("Không tìm thấy sản phẩm có số lượng tồn kho la: " + stock);
        } else {
            productList.forEach(System.out::println);
        }
    }
}
