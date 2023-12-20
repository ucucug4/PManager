package passwordmanager.dbtools;

import java.sql.*;

public class TableReader {

    private final String connectionURL;
    private final String driverName;

    public TableReader(String connectionURL, String driverName) {
        this.connectionURL = connectionURL;
        this.driverName = driverName;
    }

    public String[] readColumn(String columnName, String tableName) throws SQLException, ClassNotFoundException {
        String[] columnContent = new String [100];
        Class.forName(driverName);
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection(connectionURL, "defaultuser", "1111");
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT " + columnName + " FROM " + tableName);
            int i = 0;
            while (rs.next()) {
                columnContent[i] = rs.getString(columnName);
                i++;
            }
            rs.close();
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
        return columnContent;
    }

    public int checkInTable(String columnName, String tableName, String searchName) throws SQLException, ClassNotFoundException {
        Class.forName(driverName);
        Connection conn = null;
        Statement stmt = null;
        int found = 0;
        try {
            conn = DriverManager.getConnection(connectionURL, "defaultuser", "1111");
            stmt = conn.createStatement();
            ResultSet rs;
            if (columnName == "password") {
                rs = stmt.executeQuery("SELECT EXISTS(SELECT " + columnName + " FROM " + tableName + " WHERE " + columnName + " = MD5(\'" + searchName + "\'))");
            } else {
                rs = stmt.executeQuery("SELECT EXISTS(SELECT " + columnName + " FROM " + tableName + " WHERE " + columnName + " = \'" + searchName + "\')");
            }
            if(rs.next()) {
                found = rs.getInt(1);
            }
            rs.close();
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
        return found;
    }

}
