package passwordmanager.dbtools.passwordstable;

import passwordmanager.dataprocessing.Account;

import java.sql.*;
import java.util.Arrays;

public class PTableReader {

    private final String connectionURL;
    private final String driverName;
    private final String tableName;


    public PTableReader(String connectionURL, String driverName, String tableName) {
        this.connectionURL = connectionURL;
        this.driverName = driverName;
        this.tableName = tableName;
    }

    public String[][] PTableReading(Account account) throws  ClassNotFoundException {
        String[][] tableContent = new String [100][7];
        String[] columns = new String[] {"idpasswords", "idusers", "website", "login", "savedpassword", "encryption", "salt"};
        Class.forName(driverName);
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection(connectionURL, "defaultuser", "1111");
            stmt = conn.createStatement();
            for (int i = 0; i < columns.length; i++) {
                ResultSet rs = stmt.executeQuery("SELECT " + columns[i] + " FROM " + tableName + " WHERE idusers = \'" + account.getUserID() + "\'");
                int j = 0;
                while (rs.next()) {
                    tableContent[j][i] = rs.getString(columns[i]);
                    j++;
                }
                rs.close();
            }
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return tableContent;
    }
}
