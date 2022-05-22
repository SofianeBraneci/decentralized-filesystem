import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Broadcasts the current changes to the clients
 */
public class Broadcastor extends Thread {

    ArrayBlockingQueue<PayloadWrapper> queue;
    Map<Integer, Socket> subscribers;

    public Broadcastor(ArrayBlockingQueue<PayloadWrapper> queue) {
        subscribers = new HashMap<>();
        this.queue = queue;
    }

    public void addEntry(int socketId, Socket socket) {
        if (!subscribers.containsKey(socketId)) {
            subscribers.put(socketId, socket);
        }
    }

    @Override
    public void run() {
        while (true) {

            try {

                PayloadWrapper wrapper = queue.take();
                System.out.println("BROADCASTING FILES to " + subscribers.size() + " subs");
                for (Map.Entry<Integer, Socket> entry : subscribers.entrySet()) {
                    System.out.println("--> " + wrapper.getPayload().getPath());

                    try {
                        ObjectOutputStream outputStream = new ObjectOutputStream(entry.getValue().getOutputStream());
                        outputStream.writeObject(wrapper.getPayload());
                        outputStream.reset();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;

            }

        }
    }
}
