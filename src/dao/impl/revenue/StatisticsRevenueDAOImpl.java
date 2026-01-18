package dao.impl.revenue;

import dao.interfaceDao.IStatisticsRevenue;
import util.DBUtil;
import util.ExceptionHandler;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Types;
import java.time.LocalDate;

public class StatisticsRevenueDAOImpl implements IStatisticsRevenue {
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
}
