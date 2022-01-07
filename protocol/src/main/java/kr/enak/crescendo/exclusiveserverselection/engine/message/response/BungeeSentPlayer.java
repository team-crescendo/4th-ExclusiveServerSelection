package kr.enak.crescendo.exclusiveserverselection.engine.message.response;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import kr.enak.crescendo.exclusiveserverselection.engine.message.EnumExSSMessage;
import kr.enak.crescendo.exclusiveserverselection.engine.message.abc.IMessage;
import kr.enak.crescendo.exclusiveserverselection.engine.models.ServerType;

public class BungeeSentPlayer implements IMessage {
    private String username;
    private ServerType serverType;

    public BungeeSentPlayer() {
    }

    public BungeeSentPlayer(String username, ServerType serverType) {
        this.username = username;
        this.serverType = serverType;
    }

    @Override
    public EnumExSSMessage getMessageId() {
        return EnumExSSMessage.BUNGEE_SENT_PLAYER;
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
        return new BungeeSentPlayer(
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
