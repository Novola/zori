package club.novola.zori.module.misc;

import org.lwjgl.input.Keyboard;

import club.novola.zori.module.Module;
import club.novola.zori.util.Wrapper;

import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class ReverseStep extends Module {
    public ReverseStep() {
        super("ReverseStep", Category.MISC);
    }

    public void onUpdate() {
        if (Wrapper.getPlayer() == null || Wrapper.getWorld() == null || Wrapper.getPlayer().isInWater() || Wrapper.getPlayer().isInLava()) {
            return;
        }
        if (Wrapper.getPlayer().onGround) {
            --Wrapper.getPlayer().motionY;
        }
    }
}
