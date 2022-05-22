import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

public class DirectoryWatcher extends Thread {

    private Hasher hasher;
    private Path directory;
    private WatchService watcher;
    private Socket socket;

    public DirectoryWatcher(Hasher hasher, String directoryPath) throws IOException {
        this.hasher = hasher;
        this.directory = Path.of(directoryPath);
        this.watcher = FileSystems.getDefault().newWatchService();

    }

    public DirectoryWatcher(String directoryPath) throws IOException {
        this.hasher = new HashCalculator();
        this.directory = Path.of(directoryPath);
        this.watcher = FileSystems.getDefault().newWatchService();
    }

    @Override
    public void run() {
        System.out.println("DirectoryWatcher is monitoring dir = " + directory);
        try {
            directory.register(watcher, ENTRY_CREATE, ENTRY_DELETE);
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
                    socket = new Socket("localhost", 5000);
                    OutputStream outputStream = socket.getOutputStream();
                    Payload payload = new Payload(hash, event.kind().toString(), filename.toString());
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                    objectOutputStream.writeObject(payload);
                    objectOutputStream.close();
                    socket.close();
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
