import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

public class ServerWriter extends Thread{

    private OutputStream stream;
    private Path directory;
    private WatchService watcher;
    private Hasher hasher;

    public ServerWriter(OutputStream socketOutputStream, String dir) throws IOException {
        stream = socketOutputStream;
        directory = Path.of(dir);
        watcher = FileSystems.getDefault().newWatchService();
        hasher = new HashCalculator();
    }

    @Override
    public void run() {
        System.out.println("DirectoryWatcher is monitoring dir = " + directory);

        try {
            directory.register(watcher, ENTRY_CREATE, ENTRY_DELETE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(stream);
            WatchKey key;
            while (true) {
                // waits for an event to be triggered
                key = watcher.take();
                // loop through the events
                for (WatchEvent<?> event : key.pollEvents()) {
                    System.out.println("Event kind = " + event.kind());
                    if (event.kind() == OVERFLOW) {
                        continue;
                    }
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path filename = ev.context();
                    System.out.println("A new File was added to the watched directory " + filename);
                    String hash = hasher.computeHash(directory.toFile());
                    System.out.println("Current hash of the directory = "+ hash);
                    // writing to the socket
                    System.out.println("Writing to the socket");
                    Payload payload = new Payload(hash, event.kind().toString(), filename.toString());
                    objectOutputStream.writeObject(payload);
                    objectOutputStream.flush();
                    objectOutputStream.reset();

                }
                boolean isValid = key.reset();
                if (!isValid) {
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
