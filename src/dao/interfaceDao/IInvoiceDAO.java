package dao.interfaceDao;

import entity.Invoice;

import java.util.List;

public interface IInvoiceDAO {
    int addInvoice(int customerId);

    List<Invoice> getAllInvoices();

}
