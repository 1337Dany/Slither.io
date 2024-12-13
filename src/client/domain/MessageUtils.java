package client.domain;

import shared.Message;
import shared.MessagePrefixes;

public class MessageUtils {

    public Message buildMessage(String message) {
        String messageContext = message.substring(message.indexOf(':') + 2);
        if (message.startsWith(MessagePrefixes.TOALL.getValue())) {
            return new Message(
                    MessagePrefixes.TOALL,
                    null,
                    null,
                    messageContext
            );
        } else if (message.startsWith(MessagePrefixes.WHISPER.getValue())) {
            String receivers = message.substring(MessagePrefixes.WHISPER.getValue().length() + 1, message.indexOf(':'));
            return new Message(
                    MessagePrefixes.WHISPER,
                    receivers,
                    message.substring(MessagePrefixes.WHISPER.getValue().length(), message.indexOf(':')),
                    messageContext
            );
        } else if (message.startsWith(MessagePrefixes.EXCEPTWHISPER.getValue())) {
            return new Message(
                    MessagePrefixes.EXCEPTWHISPER,
                    null,
                    message.substring(MessagePrefixes.EXCEPTWHISPER.getValue().length(), message.indexOf(':')),
                    messageContext
            );
        } else {
            return new Message(
                    MessagePrefixes.TOALL,
                    null,
                    null,
                    messageContext
            );
        }
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
