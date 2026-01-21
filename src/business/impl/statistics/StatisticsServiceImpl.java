package business.impl.statistics;

import business.interfaceService.IStatisticsService;
import dao.impl.revenue.StatisticsRevenueDAOImpl;
import entity.CustomerStatistics;
import entity.Invoice;
import entity.ProductStatistics;

import java.time.LocalDate;
import java.util.List;

public class StatisticsServiceImpl implements IStatisticsService {
    @Override
    public double totalRevenueByDate(LocalDate date) {
        StatisticsRevenueDAOImpl statisticsRevenueDAO = new StatisticsRevenueDAOImpl();
        return statisticsRevenueDAO.totalRevenueByDate(date);
    }

    @Override
    public double totalRevenueAllDate() {
        StatisticsRevenueDAOImpl statisticsRevenueDAO = new StatisticsRevenueDAOImpl();
        return statisticsRevenueDAO.totalRevenueAllDate();
    }

    @Override
    public List<Invoice> totalRevenueEachDate() {
        StatisticsRevenueDAOImpl statisticsRevenueDAO = new StatisticsRevenueDAOImpl();
        return statisticsRevenueDAO.totalRevenueEachDate();
    }

    @Override
    public double totalRevenueByMonth(int month, int year) {
        StatisticsRevenueDAOImpl statisticsRevenueDAO = new StatisticsRevenueDAOImpl();
        return statisticsRevenueDAO.totalRevenueByMonth(month, year);
    }

    @Override
    public List<Invoice> totalRevenueEachMonth() {
        StatisticsRevenueDAOImpl statisticsRevenueDAO = new StatisticsRevenueDAOImpl();
        return statisticsRevenueDAO.totalRevenueEachMonth();
    }

    @Override
    public double totalRevenueByYear(int year) {
        StatisticsRevenueDAOImpl statisticsRevenueDAO = new StatisticsRevenueDAOImpl();
        return statisticsRevenueDAO.totalRevenueByYear(year);
    }

    @Override
    public List<Invoice> totalRevenueEachYear() {
        StatisticsRevenueDAOImpl statisticsRevenueDAO = new StatisticsRevenueDAOImpl();
        return statisticsRevenueDAO.totalRevenueEachYear();
    }

    @Override
    public List<ProductStatistics> getTopSellingProducts(int limit) {
        StatisticsRevenueDAOImpl statisticsRevenueDAO = new StatisticsRevenueDAOImpl();
        return statisticsRevenueDAO.getTopSellingProducts(limit);
    }

    @Override
    public List<CustomerStatistics> getTopCustomers(int limit) {
        StatisticsRevenueDAOImpl statisticsRevenueDAO = new StatisticsRevenueDAOImpl();
        return statisticsRevenueDAO.getTopCustomers(limit);
    }
}
