package business;

import util.DBUtil;
import util.ExceptionHandler;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.concurrent.Callable;


public class AdminBusiness {
    public static boolean loginAdmin(String username, String password) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("call check_login(?,?,?)");
            callSt.setString(1, username);
            callSt.setString(2, password);
            callSt.registerOutParameter(3, Types.BOOLEAN);
            callSt.execute();
            return callSt.getBoolean(3);
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi đăng nhập");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return false;
    }

    public static boolean check_exist_admin(String username) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("call  check_ex_username(?,?)");
            callSt.setString(1, username);
            callSt.registerOutParameter(2, Types.BOOLEAN);
            callSt.execute();
            return callSt.getBoolean(2);
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi kiểm tra tên đăng nhập");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return false;
    }


    public static String get_password_by_username(String username) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("{call get_pass_by_username(?,?)}");
            callSt.setString(1, username);
            callSt.registerOutParameter(2, Types.VARCHAR);
            callSt.execute();
            return callSt.getString(2);
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi lấy mật khẩu");
        }
        return null;
    }
}
