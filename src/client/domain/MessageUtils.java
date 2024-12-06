package client.domain;

import shared.Message;
import shared.MessagePrefixes;

public class MessageUtils {

    public Message buildMessage(String message) {
        if (message.startsWith(MessagePrefixes.TOALL.getValue())) {
            return new Message(MessagePrefixes.TOALL, message.substring(MessagePrefixes.TOALL.getValue().length(), message.indexOf(':')), message.substring(message.indexOf(':')));
        }
//        else {
//            //other
//            //return new ToAllMessage();
//        }
        return null;
    }

//    public Message parseMessageFromServer(Message message) {
//        if (message.getPrefix() == MessagePrefixes.TOALL) {
//            return new ToAllMessage(message);
//        } else {
//            //other
//            return new ToAllMessage(message);
//        }
//    }
}
