package kr.enak.crescendo.exclusiveserverselection.data;

public class ServerConfig {
    private boolean isServerExclusive;

    public ServerConfig() {
        this(false);
    }

    public ServerConfig(
            boolean isServerExclusive
    ) {
        this.isServerExclusive = isServerExclusive;
    }

    public boolean isServerExclusive() {
        return isServerExclusive;
    }

    public void setServerExclusive(boolean serverExclusive) {
        isServerExclusive = serverExclusive;
    }
}
