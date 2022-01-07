package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.config;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ServerConfig extends kr.enak.plugintemplate.data.config.ServerConfig {
    private @NotNull Pair<BlockVector3, BlockVector3> wildPortalCoordinates = new Pair<>(null, null);
    private @NotNull Pair<BlockVector3, BlockVector3> mildPortalCoordinates = new Pair<>(null, null);
    private @NotNull DiscordConfig discordConfig = new DiscordConfig();

    public ServerConfig(Map<String, Object> map) {
        this.deserialize(map);
    }

    private static @NotNull Map<String, Object> serializeBlockVector3(@NotNull BlockVector3 vec) {
        Map<String, Object> map = new HashMap<>();

        map.put("x", vec.getX());
        map.put("y", vec.getY());
        map.put("z", vec.getZ());

        return map;
    }

    private static @NotNull BlockVector3 deserializeBlockVector3(@NotNull Map<String, Object> map) {
        BlockVector3 vec = BlockVector3.at(
                (Integer) map.get("x"),
                (Integer) map.get("y"),
                (Integer) map.get("z")
        );
        System.out.printf("Deserialized vector x=%d y=%d z=%d%n", vec.getX(), vec.getY(), vec.getZ());
        return vec;
    }

    @Override
    public void deserialize(@NotNull Map<String, Object> map) {
        this.discordConfig = (DiscordConfig) map.getOrDefault("discordConfig", new DiscordConfig());

        this.wildPortalCoordinates = new Pair<>(BlockVector3.at(0, 0, 0), BlockVector3.at(0, 0, 0));
        this.mildPortalCoordinates = new Pair<>(BlockVector3.at(0, 0, 0), BlockVector3.at(0, 0, 0));

        {
            @NotNull List<Map<String, Object>> wildPortalXyz = ((List<Map<String, Object>>) map.getOrDefault("wildPortalXYZ", new ArrayList<>(Arrays.asList(
                    Map.of("x", 0, "y", 0, "z", 0),
                    Map.of("x", 0, "y", 0, "z", 0)
            ))));

            wildPortalCoordinates.setKey(deserializeBlockVector3(wildPortalXyz.get(0)));
            wildPortalCoordinates.setValue(deserializeBlockVector3(wildPortalXyz.get(1)));
        }
        {
            @NotNull List<Map<String, Object>> mildPortalXyz = ((List<Map<String, Object>>) map.getOrDefault("mildPortalXYZ", new ArrayList<>(Arrays.asList(
                    Map.of("x", 0, "y", 0, "z", 0),
                    Map.of("x", 0, "y", 0, "z", 0)
            ))));

            mildPortalCoordinates.setKey(deserializeBlockVector3(mildPortalXyz.get(0)));
            mildPortalCoordinates.setValue(deserializeBlockVector3(mildPortalXyz.get(1)));
        }
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("discordConfig", this.discordConfig);
        map.put("wildPortalXYZ", Arrays.asList(
                serializeBlockVector3(wildPortalCoordinates.getKey()),
                serializeBlockVector3(wildPortalCoordinates.getValue())
        ));
        map.put("mildPortalXYZ", Arrays.asList(
                serializeBlockVector3(mildPortalCoordinates.getKey()),
                serializeBlockVector3(mildPortalCoordinates.getValue())
        ));

        return map;
    }

    public DiscordConfig getDiscordConfig() {
        return discordConfig;
    }

    public Pair<BlockVector3, BlockVector3> getWildPortalCoordinates() {
        return wildPortalCoordinates;
    }

    public Pair<BlockVector3, BlockVector3> getMildPortalCoordinates() {
        return mildPortalCoordinates;
    }

    public CuboidRegion getWildPortalRegion() {
        return new CuboidRegion(wildPortalCoordinates.getKey(), wildPortalCoordinates.getValue());
    }

    public CuboidRegion getMildPortalRegion() {
        return new CuboidRegion(mildPortalCoordinates.getKey(), mildPortalCoordinates.getValue());
    }
}
