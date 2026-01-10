package business.impl.statistics;

import business.interfaceService.IStatisticsService;
import dao.impl.revenue.StatisticsRevenueDAOImpl;

import java.time.LocalDate;

public class StatisticsServiceImpl implements IStatisticsService {
    @Override
    public double totalRevenueByDate(LocalDate date) {
        StatisticsRevenueDAOImpl statisticsRevenueDAO = new StatisticsRevenueDAOImpl();
        return statisticsRevenueDAO.totalRevenueByDate(date);
    }

    @Override
    public double totalRevenueByMonth(int month, int year) {
        StatisticsRevenueDAOImpl statisticsRevenueDAO = new StatisticsRevenueDAOImpl();
        return statisticsRevenueDAO.totalRevenueByMonth(month, year);
    }

    @Override
    public double totalRevenueByYear(int year) {
        StatisticsRevenueDAOImpl statisticsRevenueDAO = new StatisticsRevenueDAOImpl();
        return statisticsRevenueDAO.totalRevenueByYear(year);
    }
}
