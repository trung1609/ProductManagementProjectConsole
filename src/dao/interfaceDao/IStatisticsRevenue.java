package dao.interfaceDao;

import java.time.LocalDate;

public interface IStatisticsRevenue {
    double totalRevenueByDate(LocalDate date);

    double totalRevenueByMonth(int month, int year);

    double totalRevenueByYear(int year);
}
