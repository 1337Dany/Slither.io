package server.DOMAIN;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BannedPhrases {
    BufferedReader reader;
    public BannedPhrases(){
        try {
            reader = new BufferedReader(new FileReader("server/DATA/bannedPhrases"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public boolean containsBanPharases(String clientMessage){
        String phrases;
        try {
            phrases = reader.readLine();

            while (phrases != null){
                String[] phrase = phrases.split(" ");

                for (String s : phrase) {
                    if (clientMessage.contains(s)) return true;
                }

                phrases = reader.readLine();
            }
        }catch (IOException ignored){}
        return false;
    }
}
