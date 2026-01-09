package presentation;

import java.util.Scanner;

public class InvoiceManagement {
    static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        do {
            try {
                System.out.println("============ QUẢN LÝ HÓA ĐƠN ============");
                System.out.println("1. Hiển thị danh sách hóa đơn");
                System.out.println("2. Thêm mới hóa đơn");
                System.out.println("3. Tìm kiếm hóa đơn");
                System.out.println("4. Quay ra menu chính");
                System.out.println("=========================================");
                System.out.print("Nhập lựa chọn: ");
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        MainMenu.main(args);
                        return;
                    default:
                        System.err.println("Vui lòng nhập lại lựa chọn phù hợp.");
                }
            }catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập lại lựa chọn.");
            }
        } while (true);
    }

    public static void addInvoice(Scanner sc){
        do {

        }while (true);
    }
}
