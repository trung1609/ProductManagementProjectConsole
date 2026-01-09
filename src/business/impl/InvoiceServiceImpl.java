package business.impl;

import business.IInvoiceService;
import dao.impl.InvoiceDAOImpl;
import dao.impl.InvoiceDetailsDAOImpl;
import entity.Invoice;
import util.DBUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

public class InvoiceServiceImpl implements IInvoiceService {
    @Override
    public boolean createInvoice(int customerId, List<Integer> productIds, List<Integer> quantities, List<Double> prices) {
        InvoiceDAOImpl invoiceDAO = new InvoiceDAOImpl();
        InvoiceDetailsDAOImpl invoiceDetailsDAO = new InvoiceDetailsDAOImpl();
        try {
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
                    return false;
                }
            }
            System.out.println("Tạo hóa đơn thành công! ID: " + invoiceId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Invoice> getAllInvoices() {
        InvoiceDAOImpl invoiceDAO = new InvoiceDAOImpl();
        return invoiceDAO.getAllInvoices();
    }

    @Override
    public Invoice findInvoiceById(int id) {
        Connection conn = null;
        CallableStatement callSt = null;
        Invoice invoice = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("{call find_invoice_by_id(?)}");
            callSt.setInt(1, id);
            boolean hasData = callSt.execute();
            if (hasData){
                ResultSet rs = callSt.getResultSet();
                if(rs.next()){
                    invoice = new Invoice();
                    invoice.setId(rs.getInt("id"));
                    invoice.setCustomerId(rs.getInt("customer_id"));
                    invoice.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime().toLocalDate());
                    invoice.setTotalAmount(rs.getDouble("total_amount"));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtil.closeConnection(conn,callSt);
        }
        return invoice;
    }
}
