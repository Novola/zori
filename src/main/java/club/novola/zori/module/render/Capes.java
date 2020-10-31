package club.novola.zori.module.render;

import club.novola.zori.module.Module;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class Capes extends Module {
    public static Map<String, String[]> UUIDs;
    public static final ResourceLocation ZORI_CAPE;
    private static Capes instance;

    public Capes() {
        super("Capes (WIP)", Category.RENDER);
        Capes.UUIDs.put("BrownZombie", new String[] { "8bb16b12-cc6d-46bf-ab4c-c183853c68c8" });
        Capes.UUIDs.put("Novola", new String[] { "70a2ddc6-e073-44a4-b11b-c2298031ed06" });
        Capes.instance = this;
    }

    public static Capes getInstance() {
        if (Capes.instance == null) {
            Capes.instance = new Capes();
        }
        return Capes.instance;
    }

    public static ResourceLocation getCapeResource(final AbstractClientPlayer player) {
        for (final String name : Capes.UUIDs.keySet()) {
            for (final String uuid : Capes.UUIDs.get(name)) {
                if (name.equalsIgnoreCase("BrownZombie") && player.getUniqueID().equals(UUID.fromString(formatUUID(uuid)))) {
                    return Capes.ZORI_CAPE;
                }
                if (name.equalsIgnoreCase("Novola") && formatUUID(player.getUniqueID().toString()).equals(formatUUID(uuid))) {
                    return Capes.ZORI_CAPE;
                }
            }
        }
        return Capes.ZORI_CAPE;
    }

    public static boolean hasCape(final UUID uuid) {
        final Iterator<String> iterator = Capes.UUIDs.keySet().iterator();
        if (iterator.hasNext()) {
            final String name = iterator.next();
            return Arrays.asList((String[])Capes.UUIDs.get(name)).contains(uuid.toString());
        }
        return false;
    }

    public static String formatUUID(final String input) {
        return input.replace("-", "").toLowerCase();
    }

    static {
        Capes.UUIDs = new HashMap<String, String[]>();
        ZORI_CAPE = new ResourceLocation("textures/zb0b.png");
    }
}
