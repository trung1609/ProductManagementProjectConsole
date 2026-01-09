package business;

import util.DBUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

public class AdminBusiness {
    public static boolean loginAdmin(String username,String password) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("call check_login(?,?,?)");
            callSt.setString(1, username);
            callSt.setString(2, password);
            callSt.registerOutParameter(3, Types.BOOLEAN);
            callSt.execute();
            return  callSt.getBoolean(3);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtil.closeConnection(conn,callSt);
        }
        return false;
    }
}
