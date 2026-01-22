package dao.impl.invoice;

import dao.interfaceDao.IInvoiceDAO;
import entity.Invoice;
import exception.ExceptionHandler;
import util.DBUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

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
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi thêm hóa đơn");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return 0;
    }

    @Override
    public List<Invoice> getAllInvoices() {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Invoice> invoices = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("{call get_all_invoices()}");
            boolean hasData = callSt.execute();
            if (hasData) {
                ResultSet rs = callSt.getResultSet();
                invoices = new ArrayList<>();
                while (rs.next()) {
                    Invoice invoice = new Invoice();
                    invoice.setId(rs.getInt("id"));
                    invoice.setCustomerId(rs.getInt("customer_id"));
                    invoice.setCustomerName(rs.getString("customer_name"));
                    invoice.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime().toLocalDate());
                    invoice.setTotalAmount(rs.getDouble("total_amount"));
                    invoices.add(invoice);
                }
            }
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi lấy danh sách hóa đơn");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return invoices;
    }

    @Override
    public boolean deleteInvoicesByCustomerId(int customerId) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("call delete_customer_invoices(?)");
            callSt.setInt(1, customerId);
            callSt.execute();
            return true;
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi xóa hóa đơn của khách hàng");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return false;
    }

    @Override
    public Invoice findInvoiceByCustomerId(int customerId) {
        Connection conn = null;
        CallableStatement callSt = null;
        Invoice invoice = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("{call find_invoice_by_customer_id(?)}");
            callSt.setInt(1, customerId);
            boolean hasData = callSt.execute();
            if (hasData) {
                ResultSet rs = callSt.getResultSet();
                if (rs.next()) {
                    invoice = new Invoice();
                    invoice.setId(rs.getInt("id"));
                    invoice.setCustomerId(rs.getInt("customer_id"));
                    invoice.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime().toLocalDate());
                    invoice.setTotalAmount(rs.getDouble("total_amount"));
                }
            }
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi tìm hóa đơn theo ID khách hàng");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return invoice;
    }
}
