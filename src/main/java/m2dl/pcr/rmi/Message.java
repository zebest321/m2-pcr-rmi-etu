package m2dl.pcr.rmi;

import java.io.Serializable;

public class Message implements Serializable {
    String content;
    String senderUsername;

    public Message(String content, String senderUsername) {
        this.content = content;
        this.senderUsername = senderUsername;
    }

    @Override
    public String toString() {
        return senderUsername + ":" +content;
    }
}
