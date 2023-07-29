package data;

import lombok.Data;
import lombok.SneakyThrows;

import java.sql.Connection;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.DriverManager;


public class DataBaseHelper {
    private static final String url = System.getProperty("db.url");
    private static final String user = System.getProperty("db.user");
    private static final String password = System.getProperty("db.password");

    private DataBaseHelper() {
    }


    @SneakyThrows
    public static Payment getStatusPaymentWithoutCredit() {
        String statusSql = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        QueryRunner runner = new QueryRunner();
        Connection conn = getConnection();
        return runner.query(conn, statusSql, new BeanHandler<>(Payment.class));

    }

    @SneakyThrows
    public static Credit getStatusPaymentWithCredit() {
        var statusSql = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        QueryRunner runner = new QueryRunner();
        Connection conn = getConnection();
        return runner.query(conn, statusSql, new BeanHandler<>(Credit.class));

    }

    @SneakyThrows
    private static Connection getConnection() {
        return DriverManager.getConnection(
                url, user, password
        );
    }

    @SneakyThrows
    public static void cleanDataBase() {

        var pays = "DELETE FROM payment_entity";
        var credits = "DELETE FROM credit_request_entity";
        var orders = "DELETE FROM order_entity";
        QueryRunner runner = new QueryRunner();
        Connection conn = getConnection();
        runner.execute(conn, pays);
        runner.execute(conn, credits);
        runner.execute(conn, orders);


    }
}
