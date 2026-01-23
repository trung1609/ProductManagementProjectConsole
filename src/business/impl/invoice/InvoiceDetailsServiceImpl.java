package business.impl.invoice;

import business.interfaceService.IInvoiceDetailsService;
import dao.impl.invoice.InvoiceDetailsDAOImpl;
import dao.interfaceDao.IInvoiceDetailsDAO;
import entity.InvoiceDetails;

import java.time.LocalDate;
import java.util.List;

public class InvoiceDetailsServiceImpl implements IInvoiceDetailsService {
    @Override
    public boolean addInvoiceDetails(int invoiceId, int productId, int quantity, double price) {
        InvoiceDetailsDAOImpl invoiceDetailsDAOImpl = new InvoiceDetailsDAOImpl();
        return invoiceDetailsDAOImpl.addInvoiceDetails(invoiceId, productId, quantity, price);
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
    public List<InvoiceDetails> getAllInvoiceDetailsByInvoiceId(int invoiceId) {
        IInvoiceDetailsDAO invoiceDetailsDAOImpl = new InvoiceDetailsDAOImpl();
        return invoiceDetailsDAOImpl.getAllInvoiceDetailsByInvoiceId(invoiceId);
    }
}
