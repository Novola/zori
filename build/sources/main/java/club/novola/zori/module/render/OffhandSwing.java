package club.novola.zori.module.render;

import club.novola.zori.module.Module;
import club.novola.zori.util.Wrapper;
import net.minecraft.util.EnumHand;

import static club.novola.zori.util.Wrapper.mc;

public class OffhandSwing extends Module {
    public OffhandSwing() {
        super("OffhandSwing", Category.RENDER);
    }

    public void onUpdate() {

        if (mc.world == null)
            return;
        Wrapper.getPlayer().swingingHand = EnumHand.OFF_HAND;
    }
}