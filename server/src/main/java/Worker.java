import java.io.*;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

public class Worker extends Thread{

    private Socket socket;
    private static  final String DIR = "src/main/resources/root/";
    private ArrayBlockingQueue<PayloadWrapper> payloads;
    private Broadcastor broadcastor;

    public Worker(Socket socket, ArrayBlockingQueue<PayloadWrapper> queue, Broadcastor broadcastor) throws IOException {
        this.socket = socket;
        this.payloads = queue;
        this.broadcastor = broadcastor;
        this.broadcastor.addEntry(socket.hashCode(), socket);
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
                Object o;
                while((o = objectInputStream.readObject()) != null){
                    Payload payload = (Payload) o;
                    System.out.println("New payload received");
                    System.out.println(payload);
                    payloads.put(new PayloadWrapper(payload, socket.hashCode()));
                }



            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }

        System.out.println("Worker done !");
    }
}
