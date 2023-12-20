package passwordmanager.dbtools.passwordstable;

import passwordmanager.dataprocessing.Account;

import java.sql.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class PTableInsertion {

    private final String connectionURL;
    private final String driverName;
    private final String tableName;

    public PTableInsertion(String connectionURL, String driverName, String tableName) {
        this.connectionURL = connectionURL;
        this.driverName = driverName;
        this.tableName = tableName;
    }

    public void PTableInserting(Account account, String[] inputData) throws ClassNotFoundException, SQLException {
        inputData[0] = account.getUserID();
        if (Objects.equals(inputData[4], "3")) {
            Random r = new Random();
            inputData[5] = String.valueOf(r.nextInt(100));
        }
        Class.forName(driverName);
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection(connectionURL, "defaultuser", "1111");
            stmt = conn.createStatement();
            if (Objects.equals(inputData[4], "1")) {
                stmt.executeUpdate("INSERT INTO " + tableName + " (idusers, website, login, savedpassword, encryption) VALUES (\'" + inputData[0] + "\', \'" + inputData[1] + "\', \'" + inputData[2] + "\', \'" + inputData[3] + "\', \'" + inputData[4] + "\')");
            } else if (Objects.equals(inputData[4], "2")) {
                stmt.executeUpdate("INSERT INTO " + tableName + " (idusers, website, login, savedpassword, encryption) VALUES (\'" + inputData[0] + "\', \'" + inputData[1] + "\', \'" + inputData[2] + "\', MD5(\'" + inputData[3] + "\'), \'" + inputData[4] + "\')");
            } else {
                stmt.executeUpdate("INSERT INTO " + tableName + " (idusers, website, login, savedpassword, encryption, salt) VALUES (\'" + inputData[0] + "\', \'" + inputData[1] + "\', \'" + inputData[2] + "\', MD5(\'" + inputData[3] + inputData[5] + "\'), \'" + inputData[4] + "\', \'" + inputData[5] + "\')");
            }
            System.out.println("Данные успешно записаны!\n");
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
