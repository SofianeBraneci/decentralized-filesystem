import org.apache.commons.codec.digest.DigestUtils;

import javax.naming.ldap.SortKey;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 5000);
        new ServerWriter(socket.getOutputStream(), "src/main/resources/root").start();
        new InputProcessor(socket.getInputStream(), Path.of("src/main/resources/root")).start();
    }
}
