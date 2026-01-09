package util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
    private static String URL = "jdbc:postgresql://localhost:5432/product_management";
    private static String USER = "postgres";
    private static String PASSWORD = "123456";

    public static Connection openConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL,USER,PASSWORD);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        return conn;
    }

    public static void closeConnection(Connection conn, CallableStatement callSt){
        if(conn != null){
            try {
                conn.close();
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        if(callSt!=null){
            try {
                callSt.close();
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }
}
