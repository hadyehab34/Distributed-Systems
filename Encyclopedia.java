

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Encyclopedia extends Remote {
    int count() throws RemoteException;
    String[] repeatedWords() throws RemoteException;
    String longest() throws RemoteException;
    String shortest() throws RemoteException;
    Object[] repeat() throws RemoteException;
}
