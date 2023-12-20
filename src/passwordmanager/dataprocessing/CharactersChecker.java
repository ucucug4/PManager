package passwordmanager.dataprocessing;

public class CharactersChecker {

    private final String avaiableCharacters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_.";

    public boolean usernameChecker(String username) {
        boolean conditions = true;
        if (username.length() <= 30) {
            for(int i = 0; i < username.length(); i++) {
                if (avaiableCharacters.indexOf(username.charAt(i)) == -1) {
                    conditions = false;
                    break;
                }
            }
            return conditions;
        } else {
            return false;
        }
    }

    public boolean passwordChecker(String password) {
        boolean conditions = true;
        if ((password.length() <= 16) & (password.length() >= 8)) {
            for(int i = 0; i < password.length(); i++) {
                if (avaiableCharacters.indexOf(password.charAt(i)) == -1) {
                    conditions = false;
                    break;
                }
            }
            return conditions;
        } else {
            return false;
        }
    }

    public static boolean isNumeric(String string) {
        int intValue;
        if(string == null || string.equals("")) {
            return false;
        }
        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }

}
