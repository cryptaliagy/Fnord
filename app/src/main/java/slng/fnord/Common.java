package slng.fnord;

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
        }
        catch (Exception e) {

        }
        if (digestBytes == null) {
            return null; // something happened
        }

        return makeHex(digestBytes);
    }
}
