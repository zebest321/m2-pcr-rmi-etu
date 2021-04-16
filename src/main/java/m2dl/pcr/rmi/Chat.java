package m2dl.pcr.rmi;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Chat extends Remote {
    void sendMessage(Message msg) throws RemoteException;
    List<Message> getAllMessages() throws RemoteException;
    void addMessagesListener(String regsistryId) throws RemoteException, NotBoundException;
    void removeMessagesListener(MessagesListener addTemperatureListener) throws RemoteException;
}
