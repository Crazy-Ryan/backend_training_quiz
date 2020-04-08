package repo;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Properties;

public class DatabaseUtil {

    private static String URL;
    private static String USER;
    private static String PASSWORD;
    private static Connection connection;

    static {
        try {
            Properties pro = new Properties();
            ClassLoader classLoader = DatabaseUtil.class.getClassLoader();
            java.net.URL pathURL = classLoader.getResource("jdbc.properties");
            String path = Objects.requireNonNull(pathURL).getPath();
            pro.load(new FileReader(path));
            URL = pro.getProperty("url");
            USER = pro.getProperty("user");
            PASSWORD = pro.getProperty("password");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection connectToDB() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void releaseSource(Connection conn, Statement pre, ResultSet res) {
        if (null != res) {
            try {
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        releaseSource(conn, pre);
    }

    public static void releaseSource(Connection conn, Statement pre) {
        if (null != pre) {
            try {
                pre.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (null != conn) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}