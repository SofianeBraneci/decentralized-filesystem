public class PayloadWrapper {

    private Payload payload;
    private int sourceSocketId;

    public PayloadWrapper(Payload payload, int sourceSocketId) {
        this.payload = payload;
        this.sourceSocketId = sourceSocketId;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public int getSourceSocketId() {
        return sourceSocketId;
    }

    public void setSourceSocketId(int sourceSocketId) {
        this.sourceSocketId = sourceSocketId;
    }
}
