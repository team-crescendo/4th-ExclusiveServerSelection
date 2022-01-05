package kr.enak.crescendo.exclusiveserverselection.engine.message.request;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import kr.enak.crescendo.exclusiveserverselection.engine.message.EnumExSSMessage;
import kr.enak.crescendo.exclusiveserverselection.engine.message.abc.IMessage;
import kr.enak.crescendo.exclusiveserverselection.engine.models.ServerType;

public class SpigotRequestSelectServer implements IMessage {
    private String username;
    private ServerType serverType;

    public SpigotRequestSelectServer() {
    }

    public SpigotRequestSelectServer(String username, ServerType serverType) {
        this.username = username;
        this.serverType = serverType;
    }

    @Override
    public EnumExSSMessage getMessageId() {
        return EnumExSSMessage.SPIGOT_REQUEST_SELECT_SERVER;
    }

    @Override
    public ByteArrayDataOutput serialize() {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF(this.username);
        output.writeUTF(this.serverType.name());

        return output;
    }

    @Override
    public IMessage deserialize(ByteArrayDataInput input) {
        return new SpigotRequestSelectServer(
                input.readUTF(),
                ServerType.valueOf(input.readUTF())
        );
    }

    public String getUsername() {
        return username;
    }

    public ServerType getServerType() {
        return serverType;
    }
}
