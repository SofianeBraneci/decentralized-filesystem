import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);

        while(true){
            System.out.println("Waiting for connections");
            Socket socket = serverSocket.accept();
            System.out.println("A new connection was received " );
            new Worker(socket).start();
        }
    }
}
