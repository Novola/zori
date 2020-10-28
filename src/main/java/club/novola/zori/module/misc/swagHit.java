package club.novola.zori.module.misc;

import club.novola.zori.module.Module;
import club.novola.zori.util.Wrapper;

public class swagHit extends Module {
    public swagHit() {
        super("swagHit", Category.MISC);
    }

    @Override
    public void onUpdate() {
        if(Wrapper.getPlayer() == null) return;

        if(Wrapper.mc.entityRenderer.itemRenderer.equippedProgressMainHand < 1)
            Wrapper.mc.entityRenderer.itemRenderer.equippedProgressMainHand = 1;

        if(Wrapper.mc.entityRenderer.itemRenderer.itemStackMainHand != Wrapper.getPlayer().getHeldItemMainhand())
            Wrapper.mc.entityRenderer.itemRenderer.itemStackMainHand = Wrapper.getPlayer().getHeldItemMainhand();
    }
}
