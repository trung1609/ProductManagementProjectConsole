package dao.interfaceDao;

import dto.CustomerStatistics;
import entity.Invoice;
import dto.ProductStatistics;

import java.time.LocalDate;
import java.util.List;

public interface IStatisticsRevenueDAO {
    double totalRevenueByDate(LocalDate date);

    double totalRevenueAllDate();

    List<Invoice> totalRevenueEachDate();

    double totalRevenueByMonth(int month, int year);

    List<Invoice> totalRevenueEachMonth();

    double totalRevenueByYear(int year);

    List<Invoice> totalRevenueEachYear();

    List<ProductStatistics> getTopSellingProducts(int limit);

    List<CustomerStatistics> getTopCustomers(int limit);
}
