package server.DOMAIN;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BannedPhrases {
    BufferedReader reader;
    public boolean containsBanPharases(String clientMessage){
        String phrases;
        try {
            reader = new BufferedReader(new FileReader("src/server/DATA/bannedPhrases"));
            phrases = reader.readLine();

            while (phrases != null){
                String[] phrase = phrases.split(" ");

                for (String s : phrase) {
                    if (clientMessage.contains(s)) return true;
                }

                phrases = reader.readLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return false;
    }
}
