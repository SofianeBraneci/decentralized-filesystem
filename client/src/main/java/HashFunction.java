import java.io.IOException;
import java.io.InputStream;

public interface HashFunction {
    String hash(InputStream stream) throws IOException;
}
