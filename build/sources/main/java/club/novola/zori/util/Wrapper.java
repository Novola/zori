package club.novola.zori.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.Packet;

import javax.annotation.Nullable;

public class Wrapper {
    public static final Minecraft mc = Minecraft.getMinecraft();

    public static volatile Wrapper INSTANCE = new Wrapper();

    @Nullable
    public static EntityPlayerSP getPlayer(){
        return mc.player;
    }

    @Nullable
    public static WorldClient getWorld(){
        return mc.world;
    }

    public static FontRenderer getFontRenderer(){
        return mc.fontRenderer;
    }

    public void sendPacket(Packet packet) {
        this.getPlayer().connection.sendPacket(packet);
    }
}
