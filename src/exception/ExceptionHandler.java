package exception;

import presentation.menu_util.MenuUtil;

import java.sql.SQLException;


public class ExceptionHandler {


    public static void handleDatabaseException(Exception e, String message) {
        MenuUtil.printError("Lỗi cơ sở dữ liệu: " + message);
        e.printStackTrace();
    }

    public static void handleDatabaseException(Exception e) {
        handleDatabaseException(e, "Có lỗi xảy ra khi thao tác với cơ sở dữ liệu");
    }


    public static void handleNumberFormatException(String message) {
        MenuUtil.printError(message != null ? message : "Vui lòng nhập đúng định dạng số.");
    }

    public static void handleNumberFormatException() {
        handleNumberFormatException("Vui lòng nhập số.");
    }

    public static void handleDateTimeParseException(String message) {
        MenuUtil.printError(message != null ? message : "Định dạng ngày tháng không hợp lệ.");
    }


    public static void handleDateTimeParseException() {
        handleDateTimeParseException("Định dạng ngày tháng không hợp lệ. Vui lòng nhập theo định dạng dd-MM-yyyy.");
    }

    public static void handleConnectionCloseException(Exception e) {
        MenuUtil.printError("Lỗi khi đóng kết nối cơ sở dữ liệu");
        e.printStackTrace();
    }

    public static void handleRollbackException(SQLException e) {
        MenuUtil.printError("Lỗi khi rollback transaction");
        e.printStackTrace();
    }

    public static void handleGeneralException(Exception e, String context) {
        MenuUtil.printError("Lỗi xảy ra tại: " + context);
        e.printStackTrace();
    }
}
