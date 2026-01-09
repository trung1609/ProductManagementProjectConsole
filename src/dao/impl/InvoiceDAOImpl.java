package dao.impl;

import dao.IInvoiceDAO;
import entity.Invoice;
import util.DBUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

public class InvoiceDAOImpl implements IInvoiceDAO {

    @Override
    public int addInvoice(int customerId) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("call add_invoice(?,?)");
            callSt.setInt(1, customerId);
            callSt.registerOutParameter(2, Types.INTEGER);
            callSt.execute();
            return callSt.getInt(2);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtil.closeConnection(conn,callSt);
        }
        return 0;
    }
}
