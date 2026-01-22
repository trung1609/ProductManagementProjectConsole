package dao.impl.admin;

import dao.interfaceDao.IAdminDAO;
import exception.ExceptionHandler;
import util.DBUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

public class AdminDAOImpl implements IAdminDAO {

    @Override
    public boolean loginAdmin(String username, String password) {
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

    @Override
    public boolean checkExistAdmin(String username) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("call check_ex_username(?,?)");
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

    @Override
    public String getPasswordByUsername(String username) {
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
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return null;
    }

    @Override
    public String getRoleByUsername(String username) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("{call get_role_by_username(?,?)}");
            callSt.setString(1, username);
            callSt.registerOutParameter(2, Types.VARCHAR);
            callSt.execute();
            return callSt.getString(2);
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi lấy quyền người dùng");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return null;
    }
}
