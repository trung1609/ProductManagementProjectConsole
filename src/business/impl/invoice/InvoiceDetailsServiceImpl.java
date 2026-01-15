package business.impl.invoice;

import business.interfaceService.IInvoiceDetailsService;
import dao.impl.invoice.InvoiceDetailsDAOImpl;
import dao.interfaceDao.IInvoiceDetailsDAO;
import entity.InvoiceDetails;
import util.DBUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

public class InvoiceDetailsServiceImpl implements IInvoiceDetailsService {
    @Override
    public boolean addInvoiceDetails(int invoiceId, int productId, int quantity, double price) {
        InvoiceDetailsDAOImpl invoiceDetailsDAOImpl = new InvoiceDetailsDAOImpl();
        return  invoiceDetailsDAOImpl.addInvoiceDetails(invoiceId, productId, quantity, price);
    }

    @Override
    public List<InvoiceDetails> getAllInvoiceDetailsByCustomerName(String customerName) {
        IInvoiceDetailsDAO invoiceDetailsDAOImpl = new InvoiceDetailsDAOImpl();
        return invoiceDetailsDAOImpl.getAllInvoiceDetailsByCustomerName(customerName);
    }

    @Override
    public List<InvoiceDetails> getAllInvoiceDetailsByInvoiceDate(LocalDate invoiceDate) {
        IInvoiceDetailsDAO invoiceDetailsDAOImpl = new InvoiceDetailsDAOImpl();
        return invoiceDetailsDAOImpl.getAllInvoiceDetailsByInvoiceDate(invoiceDate);
    }

    @Override
    public InvoiceDetails findInvoiceDetailsByProductId(int productId) {
        Connection conn = null;
        CallableStatement callSt = null;
        InvoiceDetails invoiceDetails = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("{call find_invoice_detail_by_product_id(?)}");
            callSt.setInt(1, productId);
            boolean hasData = callSt.execute();
            if (hasData) {
                ResultSet rs = callSt.getResultSet();
                if (rs.next()) {
                    invoiceDetails = new InvoiceDetails();
                    invoiceDetails.setId(rs.getInt("id"));
                    invoiceDetails.setInvoice_id(rs.getInt("invoice_id"));
                    invoiceDetails.setProduct_id(rs.getInt("product_id"));
                    invoiceDetails.setQuantity(rs.getInt("quantity"));
                    invoiceDetails.setPrice(rs.getDouble("unit_price"));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return invoiceDetails;
    }

    @Override
    public List<InvoiceDetails> getAllInvoiceDetailsByInvoiceId(int invoiceId) {
        IInvoiceDetailsDAO invoiceDetailsDAOImpl = new InvoiceDetailsDAOImpl();
        return invoiceDetailsDAOImpl.getAllInvoiceDetailsByInvoiceId(invoiceId);
    }
}
