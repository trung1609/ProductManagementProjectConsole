package business.interfaceService;

import entity.Invoice;

import java.util.List;

public interface IInvoiceService {
    boolean createInvoice(int customerId, List<Integer> productIds, List<Integer> quantities, List<Double> prices);

    List<Invoice> getAllInvoices();
}
