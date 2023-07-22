import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class RpsClient {
    public static int readChoice(){
        System.out.print("1.Rock\n2.Paper\n3.Scissors\nEnter Choice : ");
        Scanner sc = new Scanner(System.in);
        return (sc.nextInt());
    }
    public static void main(String[] args) throws Exception{
        int choice;
        Socket socket = new Socket("192.168.1.103", 7777);
        PlayerChoice my_choice = new PlayerChoice();
        do{
            choice = readChoice();
            if(choice<1 || choice>3){
                System.out.println("Wrong Choice!! Please Enter Valid Input!");
            }
        }while(choice<1 || choice>3);
        my_choice.choice=choice;
        ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        out.writeObject(my_choice);
        out.flush();

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        char result = (char)in.read();
        if(result == 'd'){
            System.out.println("Draw!!");
        }
        else if(result == 'w'){
            System.out.println("You Won!!");
        }
        else{
            System.out.println("You Lost!!");
        }
    }
}
