import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class RpsServer {

    public static int calculateResult(int p1_choice, int p2_choice){
        if(p1_choice==p2_choice){
            return 0;
        }
        else if(p1_choice==1){
            if(p2_choice==3){
                return 1;
            }
            else{
                return 2;
            }
        }
        else if(p1_choice==2){
            if(p2_choice==1){
                return 1;
            }
            else{
                return 2;
            }
        }
        else{
            if(p2_choice==2){
                return 1;
            }
            else{
                return 2;
            }
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException{
        ServerSocket ss = new ServerSocket(7777);
        Socket socket1 = new Socket();
        Socket socket2 = new Socket();
        AcceptConnection p1 = new AcceptConnection(ss, socket1);
        Thread t1 = new Thread(p1);
        AcceptConnection p2 =new AcceptConnection(ss, socket2);
        Thread t2 = new Thread(p2);

        t1.start();
        t2.start();

        socket1 = p1.getSocket();
        socket2 = p2.getSocket();

        int p1_choice,p2_choice;
        p1_choice = p1.getChoice();
        p2_choice = p2.getChoice();
        System.out.println("P1 Choice : "+p1_choice);
        System.out.println("P2 Choice : "+p2_choice);

        BufferedWriter b1 = new BufferedWriter(new OutputStreamWriter(socket1.getOutputStream()));
        BufferedWriter b2 = new BufferedWriter(new OutputStreamWriter(socket2.getOutputStream()));
        
        int winner = calculateResult(p1_choice, p2_choice);
        if(winner==0){
            System.out.println("Draw!!");
            b1.write('d');
            b2.write('d');
        }
        else if(winner==1){
            System.out.println("Player 1 Won!!");
            b1.write('w');
            b2.write('l');
        }
        else{
            System.out.println("Player 2 Won!!");
            b1.write('l');
            b2.write('w');
        }
        b1.flush();
        b2.flush();
    }
}
