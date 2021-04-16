package m2dl.pcr.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Server<string> extends UnicastRemoteObject implements Chat {
    public List<Message> messages;
    public List<MessagesListener> listeners = new ArrayList<>();
    private Registry registry;

    public Server(Registry reg) throws RemoteException {
        super();
        registry = reg;
        messages = new ArrayList<>();
    }

    public void sendMessage(Message msg) {
        messages.add(msg);
        notifyMessageListeners(msg);
    }

    public void notifyMessageListeners(Message msg){
        for(MessagesListener lst : listeners){
            try{
                lst.newMessageReceived(msg);
            } catch(RemoteException e){
                listeners.remove(lst);
            }
        }
    }

    public List<Message> getAllMessages() throws RemoteException {
        return messages;
    }

    @Override
    public void addMessagesListener(String registryId) throws RemoteException, NotBoundException {
        MessagesListener stub = (MessagesListener) registry.lookup(registryId);
        listeners.add(stub);
    }

    @Override
    public void removeMessagesListener(MessagesListener listener) throws RemoteException {
        listeners.remove(listener);
    }

    public static void main(String args[]) {

        try {

            // Bind the remote object's stub in the registry
            LocateRegistry.getRegistry(1099);
            LocateRegistry.createRegistry(1099);
            Registry registry = LocateRegistry.getRegistry(1099);
            Server obj = new Server(registry);

            registry.bind("Chat", obj);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
