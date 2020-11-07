package club.novola.zori.module.render;

import club.novola.zori.event.EventReceivePacket;
import club.novola.zori.module.Module;
import club.novola.zori.setting.Setting;
import club.novola.zori.util.Wrapper;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CustomTime extends Module {
    private long time = 0;

    private Setting<Long> gametime = register("Time", 18000L, 0L, 23992L);

    public CustomTime() {
        super("CustomTime", Category.RENDER);
    }

    @SubscribeEvent
    public void onTime(final EventReceivePacket event) {
        if (event.getPacket() instanceof SPacketTimeUpdate) {
            this.time = ((SPacketTimeUpdate)event.getPacket()).getWorldTime();
        }
    }

    @Override
    public void onUpdate() {
        if (Wrapper.mc.world == null) {
            return;
        }
        Wrapper.mc.world.setWorldTime(this.gametime.getValue());
    }

    @Override
    public void onEnable() {
        this.time = Wrapper.mc.world.getWorldTime();
    }

    @Override
    public void onDisable() {
        Wrapper.mc.world.setWorldTime(this.time);
    }
}
