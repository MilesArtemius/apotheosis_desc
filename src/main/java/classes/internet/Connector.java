package classes.internet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Connector {
    public static String primalIpCheck(String host) {
        try {
            URL whatismyip = new URL(host); //fourth method;
            BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
            String ip = in.readLine();
            in.close();
            return ip;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
    }
}
