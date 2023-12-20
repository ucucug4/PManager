package passwordmanager.dataprocessing;

import passwordmanager.dbtools.passwordstable.PTableChanger;
import passwordmanager.dbtools.passwordstable.PTableInsertion;
import passwordmanager.dbtools.passwordstable.PTableReader;

import java.sql.*;
import java.util.Objects;
import java.util.Scanner;

public class CustomTableOperations {

    public static void dataCreate(String driverName, String connectionURL, Account account) throws SQLException, ClassNotFoundException {
        System.out.println("Создание новой записи");
        String[] inputData = new String[6];
        Scanner sc = new Scanner(System.in);
        System.out.print("\nВведите название сервиса или адрес: ");
        inputData[1] = sc.nextLine();
        System.out.print("Введите Ваш логин: ");
        inputData[2] = sc.nextLine();
        System.out.print("Введите сохраняемый пароль: ");
        inputData[3] = sc.nextLine();
        System.out.println("Пожалуйста, выберите формат сохранения Вашего пароля: \n1 - Открытый (пароль возможно просматривать в исходном виде; \n2 - 128-битный алгоритм хэширования MD5 (невозвратный); \n3 - Дополнительное хэширования MD5 (\"подсаливание\" для уменьшения угрозы взлома пароля)");
        inputData[4] = "";
        boolean correctNumber = false;
        while (!CharactersChecker.isNumeric(inputData[4]) & !correctNumber) {
            inputData[4] = sc.nextLine();
            if ((!CharactersChecker.isNumeric(inputData[4])) | ((Integer.parseInt(inputData[4]) != 1) & (Integer.parseInt(inputData[4]) != 2) & (Integer.parseInt(inputData[4]) != 3))) {
                System.out.println("Пожалуйста, введите корректное значение!");
            } else if ((Integer.parseInt(inputData[4]) != 1) & (Integer.parseInt(inputData[4]) != 2) & (Integer.parseInt(inputData[4]) != 3)) {
                System.out.println("Пожалуйста, введите корректное значение!");
            } else {
                correctNumber = true;
            }
        }
        PTableInsertion pTableInsertion = new PTableInsertion(connectionURL, driverName, "savedpasswords");
        pTableInsertion.PTableInserting(account, inputData);
    }

    public static void dataUpdate(String driverName, String connectionURL, Account account) throws SQLException, ClassNotFoundException {
        System.out.println("Обновление данных");
        Scanner sc = new Scanner(System.in);
        System.out.println("\nВыберите изменяемую строку: ");
        String dataSelect = String.valueOf(Integer.parseInt(sc.nextLine()) - 1);
        boolean columnExist = false;
        PTableReader pTableReader = new PTableReader(connectionURL, driverName, "savedpasswords");
        String[][] content = pTableReader.PTableReading(account);
        while (!CharactersChecker.isNumeric(dataSelect) & !columnExist) {
            dataSelect = String.valueOf(Integer.parseInt(sc.nextLine()) - 1);
            if (!CharactersChecker.isNumeric(dataSelect)) {
                System.out.println("Пожалуйста, введите корректное значение!");
            }
            if ((Integer.parseInt(dataSelect) >= 0) & (Integer.parseInt(dataSelect) < content.length) & (content[Integer.parseInt(dataSelect)] != null)) {
                columnExist = true;
            }
        }
        System.out.print(String.valueOf(Integer.parseInt(dataSelect) + 1) + ". " + content[Integer.parseInt(dataSelect)][2] + " " + content[Integer.parseInt(dataSelect)][3] + " " + content[Integer.parseInt(dataSelect)][4] + " ");
        if (Objects.equals(content[Integer.parseInt(dataSelect)][5], "1")) {
            System.out.println("Открытый тип");
        } else if (Objects.equals(content[Integer.parseInt(dataSelect)][5], "2")) {
            System.out.println("Хэширование MD5");
        } else {
            System.out.println("Хэширование MD5 с \"подсаливанием\"");
        }
        String idPasswords = content[Integer.parseInt(dataSelect)][0];
        String[] updatingData = new String[6];
        System.out.print("\nВведите название сервиса или адрес: ");
        updatingData[1] = sc.nextLine();
        System.out.print("Введите Ваш логин: ");
        updatingData[2] = sc.nextLine();
        System.out.print("Введите сохраняемый пароль: ");
        updatingData[3] = sc.nextLine();
        System.out.println("Пожалуйста, выберите формат сохранения Вашего пароля: \n1 - Открытый (пароль возможно просматривать в исходном виде; \n2 - 128-битный алгоритм хэширования MD5 (невозвратный); \n3 - дополнительное хэширования MD5 (\"подсаливание\" для уменьшения угрозы взлома пароля)");
        updatingData[4] = "";
        boolean correctNumber = false;
        while (!CharactersChecker.isNumeric(updatingData[4]) & !correctNumber) {
            updatingData[4] = sc.nextLine();
            if (!CharactersChecker.isNumeric(updatingData[4])) {
                System.out.println("Пожалуйста, введите корректное значение!");
            } else if ((Integer.parseInt(updatingData[4]) != 1) & (Integer.parseInt(updatingData[4]) != 2) & (Integer.parseInt(updatingData[4]) != 3)) {
                System.out.println("Пожалуйста, введите корректное значение!");
            } else {
                correctNumber = true;
            }
        }
        PTableChanger pTableChanger = new PTableChanger(connectionURL, driverName, "savedpasswords");
        pTableChanger.PTableUpdating(account, idPasswords, updatingData);
    }

    public static void dataDelete(String driverName, String connectionURL, Account account) throws SQLException, ClassNotFoundException {
        System.out.println("Удаление записи");
        Scanner sc = new Scanner(System.in);
        System.out.println("\nВыберите изменяемую строку: ");
        String dataSelect = String.valueOf(Integer.parseInt(sc.nextLine()) - 1);
        boolean columnExist = false;
        PTableReader pTableReader = new PTableReader(connectionURL, driverName, "savedpasswords");
        String[][] content = pTableReader.PTableReading(account);
        while (!CharactersChecker.isNumeric(dataSelect) & !columnExist) {
            dataSelect = String.valueOf(Integer.parseInt(sc.nextLine()) - 1);
            if (!CharactersChecker.isNumeric(dataSelect)) {
                System.out.println("Пожалуйста, введите корректное значение!");
            }
            if ((Integer.parseInt(dataSelect) >= 0) & (Integer.parseInt(dataSelect) < content.length) & (content[Integer.parseInt(dataSelect)] != null)) {
                columnExist = true;
            }
        }
        System.out.print(String.valueOf(Integer.parseInt(dataSelect) + 1) + ". " + content[Integer.parseInt(dataSelect)][2] + " " + content[Integer.parseInt(dataSelect)][3] + " " + content[Integer.parseInt(dataSelect)][4] + " ");
        if (Objects.equals(content[Integer.parseInt(dataSelect)][5], "1")) {
            System.out.println("Открытый тип");
        } else if (Objects.equals(content[Integer.parseInt(dataSelect)][5], "2")) {
            System.out.println("Хэширование MD5");
        } else {
            System.out.println("Хэширование MD5 с \"подсаливанием\"");
        }
        String idPasswords = content[Integer.parseInt(dataSelect)][0];
        PTableChanger pTableChanger = new PTableChanger(connectionURL, driverName, "savedpasswords");
        pTableChanger.PTableDeletion(idPasswords);
    }

}
