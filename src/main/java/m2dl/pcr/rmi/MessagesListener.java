package m2dl.pcr.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessagesListener extends Remote {
    void newMessageReceived(Message message) throws RemoteException;
}
