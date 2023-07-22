import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class AcceptConnection implements Runnable {
    Socket socket;
    ServerSocket ss;
    int choice;
    Boolean finish1=false,finish2=false;
    public AcceptConnection(ServerSocket ss, Socket socket){
        this.socket = socket; 
        this.ss = ss;
    }
    @Override
    public void run() {
        try {
            socket = ss.accept();
            finish2=true;
            System.out.println("Client Connected!");
            ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            PlayerChoice my_choice = (PlayerChoice)in.readObject();
            choice = my_choice.choice;
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }

        finish1 = true;
        synchronized(this){
            this.notifyAll();
        }
    }

    public Socket getSocket() throws InterruptedException{
        synchronized(this){
            if(!finish2){
                this.wait();
            }
        }
        return socket;
    }

    public int getChoice() throws InterruptedException{

        synchronized(this){
            if(!finish1){
                this.wait();
            }
        }
        return choice;
    }
}
