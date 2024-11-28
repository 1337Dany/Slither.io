package server.domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Configurations {
    private BufferedReader reader;
    private static final String configPath = "src/server/resources/configs";

    public boolean containsBanPharases(String clientMessage) {
        String phrases;
        try {
            reader = new BufferedReader(new FileReader(configPath));
            phrases = reader.readLine();

            while (phrases != null) {
                if (!phrases.startsWith("Server")) {
                    String[] phrase = phrases.split(",");

                    for (String s : phrase) {
                        if (clientMessage.contains(s)) return true;
                    }
                }
                phrases = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public String getAllBanPhrases() {
        String banPharasesList = "";
//        try {
//            reader = new BufferedReader(new FileReader(configPath));
//
//            String tmp = reader.readLine();
//            while (tmp != null) {
//                if (!tmp.startsWith("Server")) {
//                    banPharasesList += tmp;
//                }
//                tmp = reader.readLine();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return banPharasesList;
    }
    public String giveServerName(){
        String name = "loh";
//        try {
//            reader = new BufferedReader(new FileReader(configPath));
//            reader.readLine();
//            name = reader.readLine().substring(13);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return name;
    }

    public int giveServerPort(){
        return 12345;
//        String port = "";
//        try {
//            reader = new BufferedReader(new FileReader(configPath));
//            port = reader.readLine().substring(13);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return Integer.parseInt(port);
    }
}