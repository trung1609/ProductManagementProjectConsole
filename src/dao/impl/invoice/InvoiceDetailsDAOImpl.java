package dao.impl.invoice;

import dao.interfaceDao.IInvoiceDetailsDAO;
import entity.InvoiceDetails;
import util.DBUtil;
import util.ExceptionHandler;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi thêm chi tiết hóa đơn");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return false;
    }

    @Override
    public List<InvoiceDetails> getAllInvoiceDetailsByCustomerName(String customerName) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<InvoiceDetails> invoiceDetailsList = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("{call get_invoice_details_by_customer_name(?)}");
            callSt.setString(1, customerName);
            boolean hasData = callSt.execute();
            if (hasData) {
                ResultSet rs = callSt.getResultSet();
                invoiceDetailsList = new ArrayList<>();
                while (rs.next()) {
                    InvoiceDetails invoiceDetails = new InvoiceDetails();
                    invoiceDetails.setId(rs.getInt("id"));
                    invoiceDetails.setInvoice_id(rs.getInt("invoice_id"));
                    invoiceDetails.setCustomerId(rs.getInt("customer_id"));
                    invoiceDetails.setCustomerName(rs.getString("customer_name"));
                    invoiceDetails.setProduct_name(rs.getString("product_name"));
                    invoiceDetails.setQuantity(rs.getInt("quantity"));
                    invoiceDetails.setPrice(rs.getDouble("unit_price"));
                    invoiceDetailsList.add(invoiceDetails);
                }
            }
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi lấy chi tiết hóa đơn theo tên khách hàng");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return invoiceDetailsList;
    }

    @Override
    public List<InvoiceDetails> getAllInvoiceDetailsByInvoiceDate(LocalDate invoiceDate) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<InvoiceDetails> invoiceDetailsList = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("{call get_invoice_details_by_invoice_date(?)}");
            callSt.setDate(1, Date.valueOf(invoiceDate));
            boolean hasData = callSt.execute();
            if (hasData) {
                ResultSet rs = callSt.getResultSet();
                invoiceDetailsList = new ArrayList<>();
                while (rs.next()) {
                    InvoiceDetails invoiceDetails = new InvoiceDetails();
                    invoiceDetails.setId(rs.getInt("id"));
                    invoiceDetails.setInvoice_id(rs.getInt("invoice_id"));
                    invoiceDetails.setCustomerId(rs.getInt("customer_id"));
                    invoiceDetails.setCustomerName(rs.getString("customer_name"));
                    invoiceDetails.setProduct_name(rs.getString("product_name"));
                    invoiceDetails.setQuantity(rs.getInt("quantity"));
                    invoiceDetails.setPrice(rs.getDouble("unit_price"));
                    invoiceDetailsList.add(invoiceDetails);
                }
            }
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi lấy chi tiết hóa đơn theo ngày");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return invoiceDetailsList;
    }

    @Override
    public List<InvoiceDetails> getAllInvoiceDetailsByInvoiceId(int invoiceId) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<InvoiceDetails> invoiceDetailsList = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("{call get_invoice_details_by_id(?)}");
            callSt.setInt(1, invoiceId);
            boolean hasData = callSt.execute();
            if (hasData) {
                ResultSet rs = callSt.getResultSet();
                invoiceDetailsList = new ArrayList<>();
                while (rs.next()) {
                    InvoiceDetails invoiceDetails = new InvoiceDetails();
                    invoiceDetails.setId(rs.getInt("id"));
                    invoiceDetails.setInvoice_id(rs.getInt("invoice_id"));
                    invoiceDetails.setCustomerId(rs.getInt("customer_id"));
                    invoiceDetails.setCustomerName(rs.getString("customer_name"));
                    invoiceDetails.setProduct_name(rs.getString("product_name"));
                    invoiceDetails.setQuantity(rs.getInt("quantity"));
                    invoiceDetails.setPrice(rs.getDouble("unit_price"));
                    invoiceDetailsList.add(invoiceDetails);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return invoiceDetailsList;
    }

}
