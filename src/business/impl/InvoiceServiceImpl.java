package business.impl;

import business.IInvoiceService;
import dao.impl.InvoiceDAOImpl;
import dao.impl.InvoiceDetailsDAOImpl;

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
}
