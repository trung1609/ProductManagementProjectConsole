package dao.interfaceDao;

import entity.Invoice;

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
}
