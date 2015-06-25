/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverrmiimpl;

import static java.lang.Thread.sleep;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import serverrmi.IServices;
/**
 *
 * @author alulab14
 */
public class ServerRMIImpl extends UnicastRemoteObject implements IServices{



    /**
     * @param args the command line arguments
     */
    public class ThreadTime extends Thread{
  
        public int time=10;
        public boolean corre=true;
        public ThreadTime(){

            corre=true;
        }
        public void run(){
            corre=true;
            while (corre){
                time--;
                try {
                    sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ThreadTime.class.getName()).log(Level.SEVERE, null, ex);
                }
            }		
        }
    }
    public ThreadTime hilo;
    public boolean startedGame = false;
    public boolean pause=false;
    public boolean finGame=false;
    ArrayList<Integer> datos= new ArrayList<Integer>();
    public int posX=258;
    public int posY=185;
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
                players.get(i).numberofFriends=p.numberofFriends;
                players.get(i).fin=p.fin;
                break;
            }
        }
    }

    @Override
    public ArrayList<Player> receiveData() throws RemoteException {
        return players;
    }
    @Override
    public boolean conexionPlayer(Player p2){
        if(!startedGame){
            String namep= new String();
            int n=0;
            for(int i=0;i<players.size();i++){
                if(p2.name.compareTo(players.get(i).name)==0){
                    n++;
                    namep=p2.name+"("+n+")";
                    int j=0;
                    while(j<players.size()){
                        if(players.get(j).name.compareTo(namep)==0){
                            n++;
                            namep=p2.name+"("+n+")";
                            j=0;
                        }
                        j++;
                    }
                }
            }
            if(n!=0) p2.name=namep;
            String name="player";
            int num=players.size()+1;
            name+=""+num;
            Player p= new Player(p2.name,p2.posX,p2.posY,p2.map,p2.dir,p2.s);
            p.posX=posX;
            p.posY=posY;
            players.add(p);
            posX+=60;
            listosFalso.add(false);
            return true;
        }
        return false;
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
        if(listosVerdadero.size() == listosFalso.size()){
            startedGame = true;
            return true;
        }
        else
            return false;
    }
    @Override
    public void setFinGame(boolean b) {
        if(b){
            hilo = new ThreadTime();
            hilo.start();
        }
        else{
            hilo.time=10;
            hilo.corre=false;
        }
         finGame=b;
    }

    @Override
    public boolean getFinGame()  {
       return finGame;
    }    
    @Override
    public int getTime()  {
       return hilo.time;
    }      
}
