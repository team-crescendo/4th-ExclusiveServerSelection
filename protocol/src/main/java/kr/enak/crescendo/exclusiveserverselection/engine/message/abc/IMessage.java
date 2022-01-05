package kr.enak.crescendo.exclusiveserverselection.engine.message.abc;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import kr.enak.crescendo.exclusiveserverselection.engine.message.EnumExSSMessage;

public interface IMessage {
    EnumExSSMessage getMessageId();

    ByteArrayDataOutput serialize();

    IMessage deserialize(ByteArrayDataInput input);
}
