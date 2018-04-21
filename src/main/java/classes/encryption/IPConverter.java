package classes.encryption;

public class IPConverter {
    public static String encode(String ip) {
        long decimal = 0;
        String [] bytes = ip.split("\\.");
        for (int i = 0; i < bytes.length; i++) {
            int num = Integer.parseInt(bytes[i]);
            decimal += num * Math.pow(256, i);
        }
        StringBuilder res = new StringBuilder();
        do {
            long letter = decimal % 32;
            if (letter < 10) {
                res.append(letter);
            } else {
                res.append((char) ((letter - 10) + 'a'));
            }
            decimal /= 32;
        } while (decimal > 0);
        return res.toString();
    }
}