package kr.enak.crescendo.exclusiveserverselection.data;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class ServerConfigDeserializer extends StdDeserializer<ServerConfig> {
    public ServerConfigDeserializer() {
        this(null);
    }

    public ServerConfigDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ServerConfig deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);

        boolean isServerExclusive = node.get("isServerExclusive").booleanValue();

        return null;
    }
}
