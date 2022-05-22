import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        ArrayBlockingQueue<PayloadWrapper> payloads = new ArrayBlockingQueue<>(30);
        Broadcastor broadcastor = new Broadcastor(payloads);
        broadcastor.start();

        while(true){
            System.out.println("Waiting for connections");
            Socket socket = serverSocket.accept();
            System.out.println("A new connection was received " + socket.getInetAddress() );
            new Worker(socket, payloads, broadcastor).start();
        }
    }
}
