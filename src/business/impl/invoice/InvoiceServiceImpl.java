package business.impl.invoice;

import business.interfaceService.IInvoiceService;
import dao.impl.invoice.InvoiceDAOImpl;
import dao.impl.invoice.InvoiceDetailsDAOImpl;
import entity.Invoice;
import util.DBUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class InvoiceServiceImpl implements IInvoiceService {
    @Override
    public boolean createInvoice(int customerId, List<Integer> productIds, List<Integer> quantities, List<Double> prices) {
        Connection conn = null;
        try {
            conn = DBUtil.openConnection();
            conn.setAutoCommit(false);
            InvoiceDAOImpl invoiceDAO = new InvoiceDAOImpl();
            InvoiceDetailsDAOImpl invoiceDetailsDAO = new InvoiceDetailsDAOImpl();
            int invoiceId = invoiceDAO.addInvoice(customerId);
            if (invoiceId == 0) {
                return false;
            }
            for (int i = 0; i < productIds.size(); i++) {
                boolean result = invoiceDetailsDAO.addInvoiceDetails(
                        invoiceId,
                        productIds.get(i),
                        quantities.get(i),
                        prices.get(i));
                if (!result) {
                    System.err.println("Lỗi khi thêm sản phẩm ID: " + productIds.get(i));
                    conn.rollback();
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (conn == null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public List<Invoice> getAllInvoices() {
        InvoiceDAOImpl invoiceDAO = new InvoiceDAOImpl();
        return invoiceDAO.getAllInvoices();
    }


}
