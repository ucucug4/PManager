package passwordmanager.dbtools.passwordstable;

import passwordmanager.dataprocessing.Account;

import java.sql.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class PTableChanger {

    private final String connectionURL;
    private final String driverName;
    private final String tableName;


    public PTableChanger(String connectionURL, String driverName, String tableName) {
        this.connectionURL = connectionURL;
        this.driverName = driverName;
        this.tableName = tableName;
    }

    public void PTableUpdating(Account account, String idPasswords, String[] updatingData) throws ClassNotFoundException, SQLException {
        updatingData[0] = account.getUserID();
        if (Objects.equals(updatingData[4], "3")) {
            Random r = new Random();
            updatingData[5] = String.valueOf(r.nextInt(100));
        }
        Class.forName(driverName);
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection(connectionURL, "defaultuser", "1111");
            stmt = conn.createStatement();
            if (Objects.equals(updatingData[4], "1")) {
                stmt.executeUpdate("UPDATE " + tableName + " SET idusers = \'" + updatingData[0] + "\', website = \'" + updatingData[1] + "\', login = \'" + updatingData[2] + "\', savedpassword = \'" + updatingData[3] + "\', encryption = \'" + updatingData[4] + "\' WHERE savedpasswords.idpasswords = \'" + idPasswords + "\'");
            } else if (Objects.equals(updatingData[4], "2")) {
                stmt.executeUpdate("UPDATE " + tableName + " SET idusers = \'" + updatingData[0] + "\', website = \'" + updatingData[1] + "\', login = \'" + updatingData[2] + "\', savedpassword = MD5(\'" + updatingData[3] + "\'), encryption = \'" + updatingData[4] + "\' WHERE savedpasswords.idpasswords = \'" + idPasswords + "\'");
            } else {
                stmt.executeUpdate("UPDATE " + tableName + " SET idusers = \'" + updatingData[0] + "\', website = \'" + updatingData[1] + "\', login = \'" + updatingData[2] + "\', savedpassword = MD5(\'" + updatingData[3] + updatingData[5] + "\'), encryption = \'" + updatingData[4] + "\', salt = \'" + updatingData[5] + "\' WHERE savedpasswords.idpasswords = \'" + idPasswords + "\'");
            }
            System.out.println("Данные успешно обновлены!\n");
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

    public void PTableDeletion(String idPasswords) throws ClassNotFoundException, SQLException {
        Class.forName(driverName);
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection(connectionURL, "defaultuser", "1111");
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM " + tableName + " WHERE idpasswords = \'" +  idPasswords + "\'");
            System.out.println("Данные успешно удалены!\n");
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
