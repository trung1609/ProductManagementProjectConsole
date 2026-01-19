package dao.impl.revenue;

import dao.interfaceDao.IStatisticsRevenueDAO;
import entity.Invoice;
import util.DBUtil;
import util.ExceptionHandler;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StatisticsRevenueDAOImpl implements IStatisticsRevenueDAO {
    @Override
    public double totalRevenueByDate(LocalDate date) {
        Connection conn = null;
        CallableStatement callSt = null;
        double totalRevenue = 0;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("call revenue_by_date(?,?)");
            callSt.setDate(1, Date.valueOf(date));
            callSt.registerOutParameter(2, Types.NUMERIC);
            callSt.execute();
            return totalRevenue = callSt.getBigDecimal(2).doubleValue();
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi tính doanh thu theo ngày");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return totalRevenue;
    }

    @Override
    public double totalRevenueAllDate() {
        Connection conn = null;
        CallableStatement callSt = null;
        double totalRevenue = 0;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("call revenue_all_date(?)");
            callSt.registerOutParameter(1, Types.NUMERIC);
            callSt.execute();
            return totalRevenue = callSt.getBigDecimal(1).doubleValue();
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi tính tổng doanh thu");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return totalRevenue;
    }

    @Override
    public List<Invoice> totalRevenueEachDate() {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Invoice> totalRevenueList = null;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("{call revenue_each_date()}");
            boolean hasData = callSt.execute();
            if (hasData) {
                ResultSet rs = callSt.getResultSet();
                totalRevenueList = new ArrayList<>();
                while (rs.next()) {
                    Invoice invoice = new Invoice();
                    invoice.setCreatedAt(rs.getTimestamp("revenue_date").toLocalDateTime().toLocalDate());
                    invoice.setTotalAmount(rs.getDouble("total_revenue"));
                    totalRevenueList.add(invoice);
                }
            }
        }catch (Exception e){
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi tính doanh thu theo ngày");
        }finally {
            DBUtil.closeConnection(conn, callSt);
        }

        return totalRevenueList;
    }

    @Override
    public double totalRevenueByMonth(int month, int year) {
        Connection conn = null;
        CallableStatement callSt = null;
        double totalRevenue = 0;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("call revenue_by_month(?,?,?)");
            callSt.setInt(1, month);
            callSt.setInt(2, year);
            callSt.registerOutParameter(3, Types.NUMERIC);
            callSt.execute();
            return totalRevenue = callSt.getBigDecimal(3).doubleValue();
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi tính doanh thu theo tháng");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return totalRevenue;
    }

    @Override
    public List<Invoice> totalRevenueEachMonth() {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Invoice> totalRevenueList = new ArrayList<>();
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("{call revenue_each_month()}");
            boolean hasData = callSt.execute();
            if (hasData) {
                ResultSet rs = callSt.getResultSet();
                while (rs.next()) {
                    Invoice invoice = new Invoice();
                    int month = rs.getInt("f_month");
                    int year = rs.getInt("f_year");
                    //Gán ngày đại diện cho tháng và năm là ngày đầu tiên của tháng đó
                    invoice.setCreatedAt(LocalDate.of(year, month, 1));
                    invoice.setTotalAmount(rs.getDouble("total_revenue"));
                    totalRevenueList.add(invoice);
                }
            }
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi tính doanh thu theo tháng");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }

        return totalRevenueList;
    }

    @Override
    public double totalRevenueByYear(int year) {
        Connection conn = null;
        CallableStatement callSt = null;
        double totalRevenue = 0;
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("call revenue_by_year(?,?)");
            callSt.setInt(1, year);
            callSt.registerOutParameter(2, Types.NUMERIC);
            callSt.execute();
            return totalRevenue = callSt.getBigDecimal(2).doubleValue();
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi tính doanh thu theo năm");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }
        return totalRevenue;
    }

    @Override
    public List<Invoice> totalRevenueEachYear() {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Invoice> totalRevenueList = new ArrayList<>();
        try {
            conn = DBUtil.openConnection();
            callSt = conn.prepareCall("{call revenue_each_year()}");
            boolean hasData = callSt.execute();
            if (hasData) {
                ResultSet rs = callSt.getResultSet();
                while (rs.next()) {
                    Invoice invoice = new Invoice();
                    int year = rs.getInt("f_year");
                    // Gán ngày và tháng đại diện cho năm là tháng và ngày đầu tiên của năm đó
                    invoice.setCreatedAt(LocalDate.of(year, 1, 1));
                    invoice.setTotalAmount(rs.getDouble("total_revenue"));
                    totalRevenueList.add(invoice);
                }
            }
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseException(e, "Lỗi khi tính doanh thu theo năm");
        } finally {
            DBUtil.closeConnection(conn, callSt);
        }

        return totalRevenueList;
    }
}
