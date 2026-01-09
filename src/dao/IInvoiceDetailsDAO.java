package dao;

import entity.InvoiceDetails;

import java.util.List;

public interface IInvoiceDetailsDAO {
    boolean addInvoiceDetails(int invoiceId, int productId, int quantity, double price);
    List<InvoiceDetails> getAllInvoiceDetailsByCustomerName(String customerName);
}
