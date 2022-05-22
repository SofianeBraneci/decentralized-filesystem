import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        // String hash = new HashCalculator().computeHash(new File("src/main/resources/root"));
        // System.out.println(hash);

        new DirectoryWatcher("src/main/resources/root").start();

    }
}
