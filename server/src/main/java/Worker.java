import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Worker extends Thread{

    private Socket socket;

    public Worker(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        boolean run = true;
            // request the client hash, compare it to the server hash
            // if it's the same nothing to do
            // if not

            InputStream stream = null;
            try {
                stream = socket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(stream);
                Payload payload = (Payload) objectInputStream.readObject();
                System.out.println("New payload received");
                System.out.println(payload);
                run = !socket.isClosed();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }



        System.out.println("Worker done !");
    }
}
