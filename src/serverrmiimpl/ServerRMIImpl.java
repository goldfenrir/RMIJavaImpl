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
    public boolean pause=false;
    ArrayList<Integer> datos= new ArrayList<Integer>();
    public int posX=0;
    public int posY=0;
    public int mapa=0;
    ArrayList<Player> players= new ArrayList<Player>();
    private ArrayList<Boolean> listosFalso = new ArrayList<Boolean>(); 
    private ArrayList<Boolean> listosVerdadero = new ArrayList<Boolean>(); 
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
    public void giveData(Player p) throws RemoteException {
        for(int i=0;i<players.size();i++){
            if(p.name.compareTo(players.get(i).name)==0){//actualizo data del player
                players.get(i).map=p.map;
                players.get(i).posX=p.posX;
                players.get(i).posY=p.posY;
                players.get(i).dir=p.dir;
                players.get(i).s=p.s;
                break;
            }
        }
    }

    @Override
    public ArrayList<Player> receiveData() throws RemoteException {
        return players;
    }
    @Override
    public void conexionPlayer(Player p2){
        String name="player";
        int num=players.size()+1;
        name+=""+num;
        Player p= new Player(p2.name,p2.posX,p2.posY,p2.map,p2.dir,p2.s);
        players.add(p);
        listosFalso.add(false);
    }
    @Override
    public void disconnectPlayer(Player p){
        for(int i=0;i<players.size();i++){
            if(p.name.compareTo(players.get(i).name)==0){
                players.remove(i);
            }
        }
    }
    
    @Override
    public void setpauseGame(boolean pause){
       this.pause=pause;
    }
    @Override
    public boolean getPauseState(){
        return pause;
    }
    
    @Override
    public void agregarListo(){
        listosVerdadero.add(true);
    }
    
    @Override
    public boolean todosListos(){
        if(listosVerdadero.size() == listosFalso.size())
            return true;
        else
            return false;
    }
   
}
