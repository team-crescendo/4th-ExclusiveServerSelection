package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.config;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.*;

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
        {
            @NotNull List<Map<String, Object>> wildPortalXyz = ((List<Map<String, Object>>) map.getOrDefault("wildPortalXYZ", new ArrayList<>(Arrays.asList(
                    Map.of("x", 0, "y", 0, "z", 0),
                    Map.of("x", 0, "y", 0, "z", 0)
            ))));

            wildPortalCoordinates.setKey(deserializeBlockVector3(wildPortalXyz.get(0)));
            wildPortalCoordinates.setKey(deserializeBlockVector3(wildPortalXyz.get(1)));
        }
        {
            @NotNull List<Map<String, Object>> mildPortalXyz = ((List<Map<String, Object>>) map.getOrDefault("wildPortalXYZ", new ArrayList<>(Arrays.asList(
                    Map.of("x", 0, "y", 0, "z", 0),
                    Map.of("x", 0, "y", 0, "z", 0)
            ))));

            mildPortalCoordinates.setKey(deserializeBlockVector3(mildPortalXyz.get(0)));
            mildPortalCoordinates.setKey(deserializeBlockVector3(mildPortalXyz.get(1)));
        }
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
