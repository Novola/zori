package club.novola.zori.module.render;

import club.novola.zori.module.Module;
import club.novola.zori.util.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerGlow extends Module {

    public PlayerGlow() {
        super("PlayerGlow", Category.RENDER);
    }

    public void onUpdate() {
        if(Wrapper.mc.world != null){
            for (Entity entity : Wrapper.mc.world.loadedEntityList) {
                if (!entity.isGlowing() && entity instanceof EntityPlayer) {
                    entity.setGlowing(true);

                }
            }
        }
    }

    public void onDisable() {
        for (Entity entity : Wrapper.mc.world.loadedEntityList) {
            if (entity.isGlowing() && entity instanceof EntityPlayer) {
                entity.setGlowing(false);
            }
        }
        super.onDisable();
    }
}
