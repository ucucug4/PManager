package passwordmanager.dataprocessing;

import passwordmanager.dbtools.TableInsertion;
import passwordmanager.dbtools.TableReader;
import java.sql.*;

public class Account {

    private final String username;
    private final String password;
    private final String connectionURL;
    private final String driverName;

    public Account(String username, String password, String connectionURL, String driverName) {
        this.username = username;
        this.password = password;
        this.connectionURL = connectionURL;
        this.driverName = driverName;
    }

    public Boolean login() throws SQLException, ClassNotFoundException{
        TableReader tableReader = new TableReader(connectionURL, driverName);
        int usernameFound = tableReader.checkInTable("username", "users", username);
        int passwordFound = tableReader.checkInTable("password", "users", password);
        return (usernameFound & passwordFound) != 0;
    }

    public Boolean registration(Account account) throws SQLException, ClassNotFoundException{
        if (!account.login()) {
            TableInsertion tableInsertion = new TableInsertion(connectionURL, driverName);
            tableInsertion.userInsertion("users", username, password);
            return true;
        } else {
            return false;
        }
    }

    public String getUserID() throws SQLException, ClassNotFoundException{
        Class.forName(driverName);
        Connection conn = null;
        Statement stmt = null;
        String userid = "";
        try {
            conn = DriverManager.getConnection(connectionURL, "defaultuser", "1111");
            stmt = conn.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT idusers FROM users WHERE username = \'" + username + "\'");
            if (rs.next()) {
                userid = rs.getString("idusers");
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
        return userid;
    }

}
