package slng.fnord;

import java.util.regex.Pattern;

public class Common {
    public static String makeHex(byte[] bytes) {
        StringBuffer hexText = new StringBuffer();
        for (byte data : bytes) {
            hexText.append(0xFF & data);
        }

        return hexText.toString();
    }

    public static String makeMD5(String text) {
        byte[] textBytes = text.getBytes();
        byte[] digestBytes = null;

        try {
            digestBytes = java.security.MessageDigest.getInstance("MD5").digest(textBytes);
        } catch (Exception e) {

        }
        if (digestBytes == null) {
            return null; // something happened
        }

        return makeHex(digestBytes);
    }

    public static boolean validateService(String service) {
        return Pattern.matches("^[a-zA-Z][a-zA-Z -]+$", service);
    }

    public static boolean validatePrice(String price) {
        return Pattern.matches("^[0-9]+(\\.[0-9]{0,2})?$", price);
    }

    // Your everyday run-of-the-mill email validation regex
    public static boolean validateEmail(String email) {
        return Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}$", email);
    }

    // Users should only contain alphanumeric characters, periods, underscores and dashes
    public static boolean validateUser(String user) {
        return Pattern.matches("^[a-zA-Z0-9._-]{6,}$", user);
    }

    public static boolean validatePassword(String password) {
        return Pattern.matches("^[a-zA-Z0-9._+=!@#$%^&*:,?-]{5,}$", password);
    }

    public static boolean validatePhoneNumber(String phoneNumber){
        return Pattern.matches("^(?:\\d{3}[\\-\\s]?)?\\d{3}[\\-\\s]?\\d{4}$", phoneNumber);
    }

    public static boolean validateTime(String time){
        return Pattern.matches("^\\d{1,2}[:h]\\d{2}$", time);
    }

    public static boolean validateCompany(String company){
        return Pattern.matches("^[^\\s].+?[^\\s]$", company);
    }

    public static boolean validateAddress(String address){
        return Pattern.matches("^\\d+\\s[^\\d]+$", address);
    }


    public static User makeUser(String email, String username, String password, UserTypes type) {
        switch (type) {
            case HOMEOWNER:
                return new HomeOwner(email, username, password);
            case SERVICEPROVIDER:
                return new ServiceProvider(email, username, password);
        }

        return null; // In theory impossible
    }

}
