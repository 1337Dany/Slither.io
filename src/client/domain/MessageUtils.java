package client.domain;

import client.data.message.Message;
import client.data.message.ToAllMessage;

public class MessageUtils {

    public Message buildMessageFromReceiver(String receiver, String message) {
        if(receiver.equals("To all: ")){
            return new ToAllMessage(message);
        } else {
            //other
            return new ToAllMessage(message);
        }
    }

    public Message parseMessageFromServer(String message){
        String[] split = message.split(":");
        String receiver = split[0];
        String messageText = split[1];
        if(receiver.contains("To all")){
            return new ToAllMessage(messageText);
        } else {
            //other
            return new ToAllMessage(messageText);
        }
    }
}
