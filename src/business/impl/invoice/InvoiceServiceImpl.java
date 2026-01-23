package business.impl.invoice;

import business.interfaceService.IInvoiceService;
import dao.impl.invoice.InvoiceDAOImpl;
import dao.interfaceDao.IInvoiceDAO;
import entity.Invoice;
import exception.ExceptionHandler;
import util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class InvoiceServiceImpl implements IInvoiceService {
    private final IInvoiceDAO invoiceDAO;

    public InvoiceServiceImpl() {
        this.invoiceDAO = new InvoiceDAOImpl();
    }

    @Override
    public boolean createInvoice(int customerId, List<Integer> productIds, List<Integer> quantities, List<Double> prices) {
        Connection conn = null;
        try {
            conn = DBUtil.openConnection();
            conn.setAutoCommit(false);
            InvoiceDetailsServiceImpl invoiceDetailsService = new InvoiceDetailsServiceImpl();

            //Trả ra 1 invoice id
            int invoiceId = invoiceDAO.addInvoice(customerId);
            if (invoiceId == 0) {
                return false;
            }

            for (int i = 0; i < productIds.size(); i++) {
                boolean result = invoiceDetailsService.addInvoiceDetails(
                        invoiceId,
                        productIds.get(i),
                        quantities.get(i),
                        prices.get(i));
                if (!result) {
                    ExceptionHandler.handleDatabaseException(new Exception("Lỗi khi thêm chi tiết hóa đơn"),
                            "Lỗi khi thêm chi tiết hóa đơn cho sản phẩm ID: " + productIds.get(i));
                    conn.rollback();
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ExceptionHandler.handleRollbackException(ex);
            }
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi tạo hóa đơn");
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                ExceptionHandler.handleConnectionCloseException(e);
            }
        }
        return false;
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceDAO.getAllInvoices();
    }


}
