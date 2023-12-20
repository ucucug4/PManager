package passwordmanager.dbtools;

import java.sql.*;

public class TableInsertion {

    private final String connectionURL;
    private final String driverName;

    public TableInsertion(String connectionURL, String driverName) {
        this.connectionURL = connectionURL;
        this.driverName = driverName;
    }

    public void userInsertion(String tableName, String usernameInsert, String passwordInsert) throws SQLException, ClassNotFoundException {
        Class.forName(driverName);
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection(connectionURL, "defaultuser", "1111");
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO " + tableName + " (username, password) VALUES (\'" + usernameInsert + "\' , MD5(\'" + passwordInsert + "\'))");
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
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }

}
