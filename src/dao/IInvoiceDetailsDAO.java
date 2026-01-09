package dao;

import entity.InvoiceDetails;

public interface IInvoiceDetailsDAO {
    boolean addInvoiceDetails(int invoiceId, int productId, int quantity, double price);
}
