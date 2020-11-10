package club.novola.zori.module.player;

import club.novola.zori.module.Module;
import club.novola.zori.util.Wrapper;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemExpBottle;

public class FastXP extends Module {
    public FastXP() {
        super("FastXP", Category.PLAYER);
    }

    @Override
    public void onUpdate() {
        if(Wrapper.getPlayer() == null) return;
        if (Wrapper.getPlayer().getHeldItemMainhand().getItem() instanceof ItemExpBottle || offhand()) Wrapper.mc.rightClickDelayTimer = 0;
    }

    private boolean offhand() {
        boolean item = Wrapper.getPlayer().getHeldItemOffhand().getItem() instanceof ItemExpBottle;
        boolean block = Wrapper.getPlayer().getHeldItemMainhand().getItem() instanceof ItemBlock;
        return item && !block;
    }
}
