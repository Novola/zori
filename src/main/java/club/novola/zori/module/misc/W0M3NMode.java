package club.novola.zori.module.misc;

import club.novola.zori.module.Module;
import club.novola.zori.util.Wrapper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.awt.*;
import java.net.URI;

public class W0M3NMode extends Module {

    public W0M3NMode(){
        super("W0M3N Mode", Category.MISC);
    }

    public void onUpdate(){
        if(Wrapper.mc.world == null || Wrapper.mc.player == null){
            this.disable();
        }
    }

    public void onEnable(){
        if(Wrapper.mc.world != null && Wrapper.getPlayer() != null) {
            try {
                Desktop.getDesktop().browse(URI.create("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
            } catch (Exception ignored) {
            }
        }
    }

    @SubscribeEvent
    public void onClientDisconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        this.disable();
    }
}
