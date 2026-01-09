package dao.impl;

import dao.IInvoiceDetailsDAO;
import util.DBUtil;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;

public class InvoiceDetailsDAOImpl implements IInvoiceDetailsDAO {

    @Override
    public boolean addInvoiceDetails(int invoiceId, int productId, int quantity, double price) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("call add_invoice_details(?,?,?,?)");
            callSt.setInt(1, invoiceId);
            callSt.setInt(2, productId);
            callSt.setInt(3, quantity);
            callSt.setBigDecimal(4, BigDecimal.valueOf(price));
            callSt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return false;
    }
}
