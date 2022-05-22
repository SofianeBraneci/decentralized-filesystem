import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Path;

public class InputProcessor extends Thread {
    private final InputStream stream;
    private final Path directory;
    private final ObjectInputStream objectInputStream;

    public InputProcessor(InputStream stream, Path directory) throws IOException {
        this.stream = stream;
        this.directory = directory;
        this.objectInputStream = new ObjectInputStream(stream);
    }

    @Override
    public void run() {

        System.out.println("READING");
        Object o;

        try {

            while ((o = objectInputStream.readObject()) != null) {

                System.out.println("RECEIVED");
                Payload payload = (Payload) o;
                System.out.println(payload);

            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();

        }


    }
}
