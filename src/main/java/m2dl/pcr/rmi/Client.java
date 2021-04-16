package m2dl.pcr.rmi;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UID;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Scanner;

public class Client extends UnicastRemoteObject implements MessagesListener {
    List<Message> localeListMessage;

    private Client(List<Message> allMessages) throws RemoteException {
        super();
        localeListMessage = allMessages;
        for(Message msg : localeListMessage){
            System.out.println(msg.senderUsername +" : "+msg.content);
        }
    }

    public static void main(String[] args) {
        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry();
            Chat stub = (Chat) registry.lookup("Chat");
            Client client = new Client(stub.getAllMessages());

            String registryId = new UID().toString();
            registry.bind(registryId,client);
            stub.addMessagesListener(registryId);

            Scanner in = new Scanner(System.in);
            System.out.print("Who are you :");
            String username = in.nextLine();
            System.out.print("Votre message :");
            String s = in.nextLine();
            while(!s.equals("stop")){
                stub.sendMessage(new Message(s,username));
                System.out.print("Votre message :");
                s = in.nextLine();
            }

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void newMessageReceived(Message message) throws RemoteException {
        System.out.println(message.senderUsername +" : "+message.content);
        localeListMessage.add(message);
    }
}