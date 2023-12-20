import passwordmanager.dataprocessing.Account;
import passwordmanager.dataprocessing.CharactersChecker;
import passwordmanager.dataprocessing.CustomTableOperations;
import passwordmanager.dbtools.passwordstable.PTableReader;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    static String connectionURL = "jdbc:mysql://localhost:3306/pmanager";
    static String driverName = "com.mysql.cj.jdbc.Driver";
    static Account account;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        System.out.println("\nПриложение PManager: Менеджер паролей. Пожалуйста, для просмотра данных войдите в учетную запись или создайте новую.\n");
        initialActions(connectionURL, driverName);

    }

    public static void initialActions(String connectionURL, String driverName) throws SQLException, ClassNotFoundException {
        System.out.println("\n1 - Войте в существующую учетную запись; \n2 - Создать новую учетную запись");
        String choice = "";
        Scanner sc = new Scanner(System.in);
        choice = sc.nextLine();
        if (Objects.equals(choice, "1")) {
            loginFunction(connectionURL, driverName);
        } else if (Objects.equals(choice, "2")) {
            registrationFunction(connectionURL, driverName);
        } else {
            System.out.println("Пожалуста, введите корректное значение!");
            initialActions(connectionURL, driverName);
        }
    }

    public static void loginFunction(String connectionURL, String driverName) throws SQLException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nВход в учетную запись");
        System.out.print("Введите логин: ");
        String username = sc.nextLine();
        System.out.print("Введите пароль: ");
        String password = sc.nextLine();
        account = new Account(username, password, connectionURL, driverName);
        if (!account.login()) {
            System.out.println("Учетная запись не существует или введенные данные неверны");
            initialActions(connectionURL, driverName);
        } else {
            System.out.println("Вход успешно осуществлен. Добро пожаловать, " + username + "!\n");
            dataOperationSelection(connectionURL, driverName);
        }
    }

    private static void registrationFunction(String connectionURL, String driverName) throws SQLException, ClassNotFoundException {
        System.out.println("\nРегистрация учетной записи");
        Scanner sc = new Scanner(System.in);
        String username = ":";
        CharactersChecker chkr = new CharactersChecker();
        while (!chkr.usernameChecker(username)) {
            System.out.print("Введите логин: ");
            username = sc.nextLine();
            if (chkr.usernameChecker(username)) {
                System.out.println("Имя соответствует требованиям");
            } else {
                System.out.println("Имя не соответствует требованиям");
            }
        }
        String password = ":";
        while (!chkr.usernameChecker(password)) {
            System.out.print("Введите пароль: ");
            password = sc.nextLine();
            if (chkr.passwordChecker(password)) {
                System.out.println("Пароль соответствует требованиям");
            } else {
                System.out.println("Пароль не соответствует требованиям");
            }
        }
        account = new Account(username, password, connectionURL, driverName);
        if (chkr.usernameChecker(password) & chkr.usernameChecker(username)) {
            if (account.registration(account)) {
                System.out.println("Регистрация прошла успешно. Добро пожаловать, " + username + "!\n");
                CustomTableOperations.dataCreate(driverName, connectionURL, account);
                dataOperationSelection(connectionURL, driverName);
            }
        } else {
            initialActions(connectionURL, driverName);
        }
    }

    private static void dataOperationSelection(String connectionURL, String driverName) throws SQLException, ClassNotFoundException {
        tableShow();
        System.out.println("\n1 - Создать новую запись; \n2 - Обновить существующую запись; \n3 - Удалить существующую запись");
        Scanner sc = new Scanner(System.in);
        String choice = sc.nextLine();
        while (!CharactersChecker.isNumeric(choice) & ((Integer.parseInt(choice) != 1) | (Integer.parseInt(choice) != 2) | (Integer.parseInt(choice) != 3))) {
            choice = sc.nextLine();
            if ((!CharactersChecker.isNumeric(choice)) | ((Integer.parseInt(choice) != 1) | (Integer.parseInt(choice) != 2) | (Integer.parseInt(choice) != 3))) {
                System.out.println("Пожалуйста, введите корректное значение!");
            }
        }
        if (Objects.equals(choice, "1")) {
            CustomTableOperations.dataCreate(driverName, connectionURL, account);
            dataOperationSelection(connectionURL, driverName);
        } else if (Objects.equals(choice, "2")) {
            CustomTableOperations.dataUpdate(driverName, connectionURL, account);
            dataOperationSelection(connectionURL, driverName);
        } else {
            CustomTableOperations.dataDelete(driverName, connectionURL, account);
            dataOperationSelection(connectionURL, driverName);
        }
    }

    private static void tableShow() throws ClassNotFoundException {
        PTableReader pTableReader = new PTableReader(connectionURL, driverName, "savedpasswords");
        String[][] content = pTableReader.PTableReading(account);
        int lineCount = 0;
        for (String[] strings : content) {
            if (strings[0] != null) {
                lineCount++;
                System.out.print(lineCount + ". " + strings[2] + " " + strings[3] + " " + strings[4] + " ");
                if (Objects.equals(strings[5], "1")) {
                    System.out.println("Открытый тип");
                } else if (Objects.equals(strings[5], "2")) {
                    System.out.println("Хэширование MD5");
                } else {
                    System.out.println("Хэширование MD5 с \"подсаливанием\"");
                }
            }
        }
    }

}