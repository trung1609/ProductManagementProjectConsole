package business.interfaceService;

import java.time.LocalDate;

public interface IStatisticsService {
    double totalRevenueByDate(LocalDate date);

    double totalRevenueByMonth(int month, int year);

    double totalRevenueByYear(int year);
}
