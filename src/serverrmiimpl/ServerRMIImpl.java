/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverrmiimpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import serverrmi.IServices;
/**
 *
 * @author alulab14
 */
public class ServerRMIImpl extends UnicastRemoteObject implements IServices{

    /**
     * @param args the command line arguments
     */
    ArrayList<Integer> datos= new ArrayList<Integer>();
    public int posX=0;
    public int posY=0;
    public int mapa=0;
    public ServerRMIImpl() throws RemoteException{		
    }    
    public static void main(String[] args) {
        // TODO code application logic here
        try {
                Registry reg = LocateRegistry.createRegistry(1099);
                reg.rebind("MyRMIServer", new ServerRMIImpl());
                System.out.println("Servidor iniciado :)");
        } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }		        
    }

    @Override
    public void giveData(int posX2, int posY2, int mapa2) throws RemoteException {
        posX=posX2;
        posY=posY2;
        mapa=mapa2;
                        System.out.println(posX);
                System.out.println(posY);
                System.out.println(mapa);
    }

    @Override
    public ArrayList<Integer> receiveData() throws RemoteException {
        datos.clear();
        datos.add(posX);
        datos.add(posY);
        datos.add(mapa);
        return datos;
    }
    
}
