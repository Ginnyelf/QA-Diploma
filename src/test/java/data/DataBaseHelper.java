package data;

import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

public class DataBaseHelper {
    private static final String url = System.getProperty("db.url");
    private static final String user = System.getProperty("db.user");
    private static final String password = System.getProperty("db.password");

    private DataBaseHelper() {
    }

    public static String getStatusPaymentWithoutCredit() {
        var statusSql = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";

        try (
                var connection = getConnection(url, user, password);
                var statusStmt = connection.createStatement();
        ) {
            try (var rs = statusStmt.executeQuery(statusSql)) {
                if (rs.next()) {
                    var status = rs.getString(1);

                    return status;
                }
                return null;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static String getStatusPaymentWithCredit() {
        var statusSql = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";

        try (
                var connection = getConnection(url, user, password);
                var statusStmt = connection.createStatement();
        ) {
            try (var rs = statusStmt.executeQuery(statusSql)) {
                if (rs.next()) {
                    var status = rs.getString(1);

                    return status;
                }
                return null;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static void cleanDataBase() {

        var pays = "DELETE FROM payment_entity";
        var credits = "DELETE FROM credit_request_entity";
        var orders = "DELETE FROM order_entity";

        try (
                var connection = getConnection(url, user, password);
                var prepareStatPay = connection.createStatement();
                var prepareStatCredit = connection.createStatement();
                var prepareStatOrder = connection.createStatement();

        ) {
            prepareStatPay.executeUpdate(pays);
            prepareStatCredit.executeUpdate(credits);
            prepareStatOrder.executeUpdate(orders);

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
