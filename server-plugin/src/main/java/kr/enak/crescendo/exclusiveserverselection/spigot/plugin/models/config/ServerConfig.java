package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.config;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerConfig extends kr.enak.plugintemplate.data.config.ServerConfig {
    private final Pair<BlockVector3, BlockVector3> wildPortalCoordinates;
    private final Pair<BlockVector3, BlockVector3> mildPortalCoordinates;

    public ServerConfig() {
        this(new HashMap<>());
    }

    public ServerConfig(Map<String, Object> map) {
        wildPortalCoordinates = new Pair<>(null, null);
        mildPortalCoordinates = new Pair<>(null, null);

        deserialize(map);
    }

    private static @NotNull Map<String, Object> serializeBlockVector3(@NotNull BlockVector3 vec) {
        Map<String, Object> map = new HashMap<>();

        map.put("x", vec.getX());
        map.put("y", vec.getY());
        map.put("z", vec.getZ());

        return map;
    }

    private static @NotNull BlockVector3 deserializeBlockVector3(@NotNull Map<String, Object> map) {
        return BlockVector3.at(
                (Integer) map.get("x"),
                (Integer) map.get("y"),
                (Integer) map.get("z")
        );
    }

    @Override
    public void deserialize(@NotNull Map<String, Object> map) {
        wildPortalCoordinates.setKey(deserializeBlockVector3(((List<Map<String, Object>>) map.get("wildPortalXYZ")).get(0)));
        wildPortalCoordinates.setKey(deserializeBlockVector3(((List<Map<String, Object>>) map.get("wildPortalXYZ")).get(1)));
        mildPortalCoordinates.setKey(deserializeBlockVector3(((List<Map<String, Object>>) map.get("mildPortalXYZ")).get(0)));
        mildPortalCoordinates.setKey(deserializeBlockVector3(((List<Map<String, Object>>) map.get("mildPortalXYZ")).get(1)));
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

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
