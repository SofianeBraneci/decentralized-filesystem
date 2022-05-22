import java.io.Serializable;
import java.nio.file.WatchEvent;

public class Payload implements Serializable {
    private String hash;
    private String action;
    private String path;

    public Payload(String hash, String action, String path) {
        this.hash = hash;
        this.action = action;
        this.path = path;
    }

    public Payload() {
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "hash='" + hash + '\'' +
                ", action='" + action + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
